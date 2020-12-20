package test.dao;

import com.tt.sqlhelper.annotations.Param;
import test.bean.User;

import java.util.List;

/**
 * @author: guoyitao
 * @date: 2020/11/23 20:00
 * @version: 1.0
 */
public interface UserMapper {
    /**
     * 获取单个user TODO
     *
     * @param id
     * @return
     * @see
     */
    User getUser(@Param("id")String id);

    /**
     * 获取所有用户 TODO
     *
     * @return
     * @see
     */
    List<User> getAll();

    /**
     * 更新用户 TODO
     *
     * @param user
     */
    int updateUser(@Param("user")User user);

    /**
     * 删除用户 TODO
     * @author guo
     * @date 2020/11/23 20:03
     * @param id
     * @return void
     */
    int deleteUser(@Param("id")String id);


    int addUser(@Param("user")User user);
}
