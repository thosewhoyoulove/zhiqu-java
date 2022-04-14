package web.Dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import web.Entry.User;
import java.util.List;



@Mapper
@Repository
public interface UserDao {
    //查询所有用户
    //@Select("select * from user")
    List<User> selectAll();

    //通过用户名和密码查询用户
    //@Select("select * from user where username = #{username} and password = #{password}")
    User selectByUsernamePass(@Param("username") String username, @Param("password") String password);

    //通过用户名查找用户
    //@Select("select * from user where username = #{username}")
    User selectByUsername1(@Param("username") String username);

    //@Select("select * from user where username = #{username}")
    List<User> selectByUsername2(@Param("username") String username);

    //增加用户
    //@Insert("insert into user(username,password,phone,status) values(#{username},#{password},#{phone},0)")
    void addUser(@Param("username") String username, @Param("password") String password);

    //增加管理员
    void addUser1(@Param("username") String username, @Param("password") String password);

    //通过用户名删除用户
    //@Delete(" delete from user where username = #{username}")
    void deleteUser(@Param("username") String username);

    //修改用户密码
    //@Update(" update user set password = #{password},phone = #{phone} where username = #{username}")
    void updateUser(@Param("username") String username, @Param("password") String password, @Param("phone") String phone);
}


