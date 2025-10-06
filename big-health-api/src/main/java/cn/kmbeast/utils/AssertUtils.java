package cn.kmbeast.utils;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * 断言工具类：封装常用参数校验、业务规则断言逻辑，避免重复 if-else 判断
 * 特点：1. 空指针安全 2. 支持自定义异常信息 3. 覆盖常见校验场景
 */
public class AssertUtils {

    // -------------------------- 空值校验 --------------------------
    /**
     * 断言对象不为 null，否则抛出 IllegalArgumentException
     * @param obj 待校验对象（如参数、Bean 属性）
     * @param message 异常提示信息
     */
    public static void notNull(Object obj, String message) {
        if (obj == null) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 断言字符串不为 null 且非空白（排除纯空格、制表符等），否则抛出 IllegalArgumentException
     * @param str 待校验字符串（如用户名、手机号）
     * @param message 异常提示信息
     */
    public static void notBlank(String str, String message) {
        if (str == null || str.trim().isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 断言集合不为 null 且非空（有元素），否则抛出 IllegalArgumentException
     * @param collection 待校验集合（如列表、Set）
     * @param message 异常提示信息
     */
    public static void notEmpty(Collection<?> collection, String message) {
        notNull(collection, message); // 先确保集合本身不为 null
        if (collection.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 断言数组不为 null 且非空（有元素），否则抛出 IllegalArgumentException
     * @param array 待校验数组（如基本类型数组、对象数组）
     * @param message 异常提示信息
     */
    public static void notEmpty(Object[] array, String message) {
        notNull(array, message); // 先确保数组本身不为 null
        if (array.length == 0) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 断言 Map 不为 null 且非空（有键值对），否则抛出 IllegalArgumentException
     * @param map 待校验 Map
     * @param message 异常提示信息
     */
    public static void notEmpty(Map<?, ?> map, String message) {
        notNull(map, message); // 先确保 Map 本身不为 null
        if (map.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }


    // -------------------------- 数值范围校验 --------------------------
    /**
     * 断言整数大于 0，否则抛出 IllegalArgumentException
     * @param num 待校验整数（如 ID、数量）
     * @param message 异常提示信息
     */
    public static void greaterThanZero(int num, String message) {
        if (num <= 0) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 断言长整数大于 0，否则抛出 IllegalArgumentException
     * @param num 待校验长整数（如分布式 ID、时间戳）
     * @param message 异常提示信息
     */
    public static void greaterThanZero(long num, String message) {
        if (num <= 0) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 断言数值在指定范围内（包含边界），否则抛出 IllegalArgumentException
     * @param num 待校验数值
     * @param min 最小值（包含）
     * @param max 最大值（包含）
     * @param message 异常提示信息
     */
    public static void between(int num, int min, int max, String message) {
        if (num < min || num > max) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 断言数值在指定范围内（包含边界），否则抛出 IllegalArgumentException
     * @param num 待校验数值
     * @param min 最小值（包含）
     * @param max 最大值（包含）
     * @param message 异常提示信息
     */
    public static void between(long num, long min, long max, String message) {
        if (num < min || num > max) {
            throw new IllegalArgumentException(message);
        }
    }


    // -------------------------- 业务规则校验 --------------------------
    /**
     * 断言布尔表达式为 true（满足业务规则），否则抛出 IllegalArgumentException
     * @param expression 布尔表达式（如 "user.getStatus() == 1"）
     * @param message 异常提示信息（规则不满足时的说明）
     */
    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 断言两个对象相等（equals 语义），否则抛出 IllegalArgumentException
     * 注意：支持 null 安全比较（两个都为 null 时判定为相等）
     * @param obj1 第一个对象
     * @param obj2 第二个对象
     * @param message 异常提示信息
     */
    public static void equals(Object obj1, Object obj2, String message) {
        if (!Objects.equals(obj1, obj2)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 断言字符串长度在指定范围内（包含边界），否则抛出 IllegalArgumentException
     * @param str 待校验字符串
     * @param minLen 最小长度（包含）
     * @param maxLen 最大长度（包含）
     * @param message 异常提示信息
     */
    public static void lengthBetween(String str, int minLen, int maxLen, String message) {
        notBlank(str, message); // 先确保字符串非空
        int length = str.length();
        if (length < minLen || length > maxLen) {
            throw new IllegalArgumentException(message);
        }
    }


    // -------------------------- 自定义异常支持 --------------------------
    /**
     * 断言对象不为 null，否则抛出指定类型的运行时异常
     * 场景：需要抛业务自定义异常（如 BusinessException）而非默认 IllegalArgumentException 时
     * @param obj 待校验对象
     * @param exceptionSupplier 异常构造器（如 BusinessException::new）
     */
    public static <T extends RuntimeException> void notNull(Object obj, Supplier<T> exceptionSupplier) {
        if (obj == null) {
            throw exceptionSupplier.get();
        }
    }

    /**
     * 断言布尔表达式为 true，否则抛出指定类型的运行时异常
     * @param expression 布尔表达式
     * @param exceptionSupplier 异常构造器
     */
    public static <T extends RuntimeException> void isTrue(boolean expression, Supplier<T> exceptionSupplier) {
        if (!expression) {
            throw exceptionSupplier.get();
        }
    }


    // 私有构造：避免工具类被实例化
    private AssertUtils() {
        throw new AssertionError("工具类不允许实例化");
    }
}
