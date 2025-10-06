package cn.kmbeast.utils;

import java.util.*;
import java.util.stream.Collectors;

public class ItemBasedCFUtil {

    // 数据结构：物品-用户-评分
    public static class Score {
        private Integer itemId;   // 食谱ID
        private Integer userId;   // 用户ID
        private Double score;     // 评分

        public Score(Integer itemId, Integer userId, Double score) {
            this.itemId = itemId;
            this.userId = userId;
            this.score = score;
        }

        public Integer getItemId() { return itemId; }
        public Integer getUserId() { return userId; }
        public Double getScore() { return score; }
    }

    /**
     * 构建物品-用户评分矩阵
     */
    public static Map<Integer, Map<Integer, Double>> buildItemScoreMatrix(List<Score> scoreList) {
        Map<Integer, Map<Integer, Double>> matrix = new HashMap<>();
        for (Score score : scoreList) {
            matrix.putIfAbsent(score.getItemId(), new HashMap<>());
            matrix.get(score.getItemId()).put(score.getUserId(), score.getScore());
        }
        return matrix;
    }

    /**
     * 计算物品间余弦相似度
     */
    public static Map<Integer, Map<Integer, Double>> calculateItemSimilarity(Map<Integer, Map<Integer, Double>> itemScoreMatrix) {
        Map<Integer, Map<Integer, Double>> similarityMatrix = new HashMap<>();
        Set<Integer> allItems = itemScoreMatrix.keySet();

        for (Integer item1 : allItems) {
            similarityMatrix.putIfAbsent(item1, new HashMap<>());
            for (Integer item2 : allItems) {
                if (!item1.equals(item2)) {
                    Map<Integer, Double> scores1 = itemScoreMatrix.get(item1);
                    Map<Integer, Double> scores2 = itemScoreMatrix.get(item2);

                    Set<Integer> commonUsers = new HashSet<>(scores1.keySet());
                    commonUsers.retainAll(scores2.keySet());

                    double numerator = 0.0, denom1 = 0.0, denom2 = 0.0;
                    for (Integer user : commonUsers) {
                        double s1 = scores1.get(user);
                        double s2 = scores2.get(user);
                        numerator += s1 * s2;
                        denom1 += Math.pow(s1, 2);
                        denom2 += Math.pow(s2, 2);
                    }

                    double similarity = 0.0;
                    if (denom1 > 0 && denom2 > 0) {
                        similarity = numerator / (Math.sqrt(denom1) * Math.sqrt(denom2));
                    }
                    similarityMatrix.get(item1).put(item2, similarity);
                }
            }
        }
        return similarityMatrix;
    }

    /**
     * 为目标用户推荐食谱
     */
    public static List<Integer> recommendItems(
        Map<Integer, Map<Integer, Double>> itemScoreMatrix,
        Map<Integer, Map<Integer, Double>> similarityMatrix,
        Integer targetUserId,
        int topN) {

        // 1. 找到目标用户评分过的物品
        Map<Integer, Double> userRatings = new HashMap<>();
        for (Map.Entry<Integer, Map<Integer, Double>> entry : itemScoreMatrix.entrySet()) {
            Integer itemId = entry.getKey();
            for (Map.Entry<Integer, Double> userScore : entry.getValue().entrySet()) {
                if (userScore.getKey().equals(targetUserId)) {
                    userRatings.put(itemId, userScore.getValue());
                }
            }
        }

        if (userRatings.isEmpty()) {
            return Collections.emptyList();
        }

        // 2. 预测未评分物品的分数
        Map<Integer, Double> itemScores = new HashMap<>();
        for (Map.Entry<Integer, Double> entry : userRatings.entrySet()) {
            Integer itemId = entry.getKey();
            Double rating = entry.getValue();

            // 相似物品Top10
            List<Integer> similarItems = similarityMatrix.getOrDefault(itemId, Collections.emptyMap()).entrySet().stream()
                    .sorted(Map.Entry.<Integer, Double>comparingByValue().reversed())
                    .limit(10)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());

            for (Integer simItem : similarItems) {
//                 if (!userRatings.containsKey(simItem)) {
                     // 改为：即使用户已评分，也允许推荐（仅作测试，生产需谨慎）
                     double sim = similarityMatrix.get(itemId).get(simItem);
                     itemScores.put(simItem, itemScores.getOrDefault(simItem, 0.0) + sim * rating);
//                 }
            }
        }


        // 3. 返回TopN推荐
        return itemScores.entrySet().stream()
                .sorted(Map.Entry.<Integer, Double>comparingByValue().reversed())
                .limit(topN)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    /**
     * 外部直接调用的推荐方法
     */
    public static List<Integer> recommend(List<Score> scoreList, Integer targetUserId, int topN) {
        Map<Integer, Map<Integer, Double>> itemScoreMatrix = buildItemScoreMatrix(scoreList);
        Map<Integer, Map<Integer, Double>> similarityMatrix = calculateItemSimilarity(itemScoreMatrix);
        return recommendItems(itemScoreMatrix, similarityMatrix, targetUserId, topN);
    }
}