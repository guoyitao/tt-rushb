package test;

import com.tt.sqlhelper.session.SqlSession;
import com.tt.sqlhelper.session.SqlSessionFactory;
import com.tt.sqlhelper.session.SqlSessionFactoryBuilder;
import test.bean.User;
import test.dao.UserMapper;

import java.util.List;

/**
 * @author: guoyitao
 * @date: 2020/11/23 22:34
 * @version: 1.0
 */
public class TestMain {
    public static void main(String[] args) {
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build("conf.properties");

        SqlSession sqlSession = factory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
//        getAllUser(mapper);
//        getOneUser(mapper);

//        User user = new User();
//        user.setId("4");
//        user.setName("fuckABC");
//        updateUser(mapper,user);

//        User user = new User();
//        user.setId("11213123");
//        user.setName("fuckABC");
//        int i = mapper.addUser(user);
//        System.out.println(i);

        int i = mapper.deleteUser("2");
        System.out.println(i);
    }

    private static void updateUser(UserMapper mapper,User user) {

        int i = mapper.updateUser(user);
        System.out.println(i);
    }

    private static void getOneUser(UserMapper mapper) {
        User user = mapper.getUser("4");
        System.out.println(user);
    }

    private static void getAllUser(UserMapper mapper) {
        List<User> all = mapper.getAll();
        for (User user : all) {
            System.out.println(user);
        }
    }
}
