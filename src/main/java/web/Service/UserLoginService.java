package web.Service;

import com.github.pagehelper.PageInfo;
import web.Entry.User;
import java.util.List;
public interface UserLoginService {
    //通过用户名和密码查询用户
    User getAUser(String username,String password);

    //通过用户名查找用户
    User getUserByName1(String username);

    List<User> getUserByName2(String username);

    //根据姓名删除
    void removeByName(String username);

    //添加用户
    void addByName(String username, String password);

    //增加管理员
    void addByName1(String username, String password);

    //修改用户密码
    void update(String password, String username, String phone);

    //查询所有用户
    List<User> findUser();

//分页显示

    PageInfo<User> pageUser(int currPage, int pageSize);


}
