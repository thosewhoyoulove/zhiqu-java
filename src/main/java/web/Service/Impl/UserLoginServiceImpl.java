package web.Service.Impl;

import com.github.pagehelper.PageHelper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.Dao.UserDao;
import web.Entry.User;
import web.Exception.BaseException;
import web.Service.UserLoginService;
import com.github.pagehelper.PageInfo;
import java.util.List;

@Service
public class UserLoginServiceImpl implements UserLoginService {

    @Autowired
    private UserDao userDao;

    //验证用户登录是否正确
    @Override
    public User getAUser(String username, String password) {
        User user = userDao.selectByUsername1(username);
        if (user == null) {
            throw new BaseException("您查询的账户不存在");
        }
        if (!(user.getPassword().equals(password))) {
            throw new BaseException("您输入的密码有误");
        }
        return user;
    }
    //通过用户名查找用户
    @Override
    public User getUserByName1(String username) {
        User user = userDao.selectByUsername1(username);
        return user;
    }

    @Override
    public List<User> getUserByName2(String username) {
        List<User> user =userDao.selectByUsername2(username);
        return user;
    }

    //删除用户
    @Override
    public void removeByName(String username) {
       userDao.deleteUser(username);
    }

    //增加用户，检查注册信息
    @Override
    public void addByName(String username,String password) {
        User user = null;
        user=userDao.selectByUsername1(username);
        if ((username==null||username.equals("")||password==null||password.equals(""))){
            throw new BaseException("请填写注册信息");

        }
        if(user==null){
            userDao.addUser(username,password);
        }else{
            throw new BaseException("用户名已存在，请重新输入");
        }
    }

    //增加管理员，检查注册信息
    @Override
    public void addByName1(String username,String password) {
        User user = null;
        user=userDao.selectByUsername1(username);
        if ((username==null||username.equals("")||password==null||password.equals(""))){
            throw new BaseException("请填写注册信息");

        }
        if(user==null){
            userDao.addUser1(username,password);
        }else{
            throw new BaseException("用户名已存在，请重新输入");
        }
    }



    //修改密码
    @Override
    public void update(String password, String username, String phone) {
        userDao.updateUser(username,password,phone);

    }
    //查询所有用户
    @Override
    public List<User> findUser() {
        return null;
    }

    @Override
    public PageInfo<User> pageUser(int currPage, int pageSize) {
        PageHelper.startPage(currPage, pageSize);
        System.out.println("before user");
        List<User> user= userDao.selectAll();
        System.out.println(userDao.selectAll());
        PageInfo<User> pageInfo = new PageInfo<User>(user);
        return pageInfo;
    }
}
