package cn.kmbeast.mapper;

import cn.kmbeast.pojo.entity.User;
import cn.kmbeast.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;

@SpringBootTest
public class UserMapperTest {

    // 自动注入 Mapper 代理对象
    @Autowired
    private UserMapper userMapper;

    // 测试查询所有用户
    @Test
    public void testSelectAll() {
        List<User> userList = userMapper.selectList(null); // null 表示无查询条件
        userList.forEach(user -> System.out.println("用户：" + user));
    }

    // 测试插入用户
    @Test
    public void testInsert() {
        User user = new User();
        user.setUserName("张三");
        int rows = userMapper.insert(user); // 插入成功返回 1
        System.out.println("插入行数：" + rows + "，新增用户ID：" + user.getId()); // 自增ID会自动回显
    }
    // 测试
    @Test
    public void test() {
        int a = Integer.MAX_VALUE;
        int b = Integer.MAX_VALUE;
        int sum = a + b;
        System.out.println("a="+a+",b="+b+",sum="+sum);
    }
}