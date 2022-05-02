package web.Dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import web.Entry.Classes;
import web.Entry.User;

import java.net.UnknownServiceException;
import java.util.List;

@Repository
public interface UserDao {
    public List<User> getAllUser();

    public int registerUser(User user);

    public int addTeacherByAdmin(User user);

    public int updateUserInfoByAdmin(User user);

    public int deleteUser(@Param("user_id") String user_id);

    public User getUserByName(@Param("username") String username);

    public int updateUserClassName(@Param("user_id") String user_id, @Param("class_name") String class_name);

    public Classes ifExistClass(@Param("class_name") String class_name);

    public List<User> getUserByClassName(@Param("class_name") String class_name);

}

