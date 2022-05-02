package web.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import web.Dao.UserDao;
import web.Entry.Result;
import web.Entry.User;
import web.Entry.UserToken;
import web.Utils.ResultUtils;
import web.Utils.UserTokenUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@RestController

public class UserController {
    @Autowired
    UserDao userDao;

    //用户登录
    @RequestMapping("/loginUser")
    public Result loginUser(@RequestParam("username") String username, @RequestParam("password") String password){
        User user = userDao.getUserByName(username);
        UserToken userToken;
        String token= null;
        //查找用户是否存在
        if (user == null){
            return ResultUtils.error(300, "用户不存在");
        }
        if (!user.getPassword().equals(password)){
            return ResultUtils.error(400, "密码错误");
        }
        user.setPassword("null");
        //获取用户token
        userToken = new UserToken(user.getUser_id(), username, user.getStatus(), user.getClass_name());
        try {
            token = UserTokenUtils.generateToken(userToken, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //登录成功返回user对象
        return ResultUtils.data(user, token,"登录成功");


    }

    //展示所有用户
    @RequestMapping("/getAllUser")
    public Result getAllUser(){
        List<User> userList = userDao.getAllUser();
        if (userList.size() == 0){
            return ResultUtils.error(300, "列表为空");
        }
        for (User user : userList) {
            user.setPassword("null");
        }
        return ResultUtils.data(userList, "获取成功");
    }

    //注册用户
    @RequestMapping("/registerUser")
    public Result registerUser(@RequestParam("username") String username, @RequestParam("password1") String password1, @RequestParam("password2") String password2){
        //判断是否存在该用户名
        if (userDao.getUserByName(username) != null){
            return ResultUtils.error(300, "该用户名已存在");
        }
        if (!password1.equals(password2)){
            return ResultUtils.error(400, "两次密码不一致");
        }
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        User user = new User("student_" + timestamp, username, password1, 0, "null");
        if (userDao.registerUser(user) > 0){
            return ResultUtils.success("注册成功");
        }
        return ResultUtils.error(500, "注册失败");
    }

    //管理员增加用户处理
    @RequestMapping("/addTeacherByAdmin")
    public Result addTeacherByAdmin(@RequestParam("username") String username, @RequestParam("password") String password){
        if (userDao.getUserByName(username) != null){
            return ResultUtils.error(300, "该用户名已存在");
        }
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        User user = new User("teacher_" + timestamp, username, password, 1, "null");
        if (userDao.addTeacherByAdmin(user) > 0){
            return ResultUtils.success("注册成功");
        }
        return ResultUtils.error(500, "注册失败");
    }

    //管理员修改用户信息
    @RequestMapping("/updateUserInfoByAdmin")
    public Result updateUserInfoByAdmin(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("class_name") String class_name){
        //判断班级是否存在
        if (!ifExistClass(class_name)){
            return ResultUtils.error(300, "班级不存在");
        }
        //获取用户对象
        User user = userDao.getUserByName(username);
        user.setUsername(username);
        user.setPassword(password);
        user.setClass_name(class_name);
        if (userDao.updateUserInfoByAdmin(user) > 0){
            return ResultUtils.success("修改成功");
        }
        return ResultUtils.error(400, "修改失败");
    }

    //删除用户
    @RequestMapping("/deleteUser")
    public Result deleteUser(@RequestParam("user_id") String user_id){
        if (userDao.deleteUser(user_id) > 0){
            return ResultUtils.success("删除成功");
        }
        return ResultUtils.error(300, "删除失败");
    }

    //通过用户名搜索用户
    @RequestMapping("/getUserByName")
    public Result getUserByName(@RequestParam("username") String username){
        User user = userDao.getUserByName(username);
        user.setPassword("null");
        if (user == null){
            return ResultUtils.error(300, "不存在改用户");
        }
        return ResultUtils.data(user, "搜索成功");
    }

    //通过用户名添加班级
    @RequestMapping("/updateUserClassName")
    public Result updateUserClassName(@RequestParam("user_id") String user_id, @RequestParam("class_name") String class_name){
        //判断班级是否存在
        if (!ifExistClass(class_name)){
            return ResultUtils.error(300, "班级不存在");
        }
        if (userDao.updateUserClassName(user_id,class_name) > 0){
            return ResultUtils.success("" + "添加成功");
        }
        return ResultUtils.error(300, "添加失败");
    }

    //班级是否存在
    @RequestMapping("ifExistClass")
    public boolean ifExistClass(String class_name){
        if (userDao.ifExistClass(class_name) != null){
            return true;
        }
        return false;
    }

    //通过班级名搜索用户
    @RequestMapping("/getUserByClassName")
    public Result getUserByClassName(@RequestParam("class_name") String class_name){
        //判断班级是否存在
        if (!ifExistClass(class_name)){
            return ResultUtils.error(300, "班级不存在");
        }
        List<User> userList = userDao.getUserByClassName(class_name);
        if (userList.size() == 0){
            return ResultUtils.error(400, "班级人数为空");
        }
        for (User user : userList) {
            user.setPassword("null");
        }
        return ResultUtils.data(userList, "搜索成功");
    }




}
