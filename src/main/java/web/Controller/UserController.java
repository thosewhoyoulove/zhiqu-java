package web.Controller;


import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import web.Dao.UserDao;
import web.Entry.User;
import web.Exception.BaseException;
import web.Service.UserLoginService;
import web.Utils.GreateImageCoke;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController

public class UserController {
    @Autowired
    UserDao userDao;
    @Value("${absoluteImgPath}")
    String absoluteImgPath;
    @Value("${sonImgPath}")
    String sonImgPath;


    @Autowired
    private UserLoginService userLoginService;

    //进入登录界面
    @RequestMapping("/login")
    public String login(){ return  "login";}

    //退出
    @RequestMapping("/logout")
    public String logout(){ return  "login";}

    //生成图片验证
    @RequestMapping(value = "/checkCode")
    public void getVerify(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
            response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expire", 0);
            GreateImageCoke coke = new GreateImageCoke();
            coke.getRandcode(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //登录
    @RequestMapping(value = "/tologin", method = RequestMethod.POST)
    public String toLogin(String username, String checkcode1, String password, HttpSession session, Model model) {
        //。System.out.println(username+password);
        String code = (String) session.getAttribute("RANDOMVALIDATECODEKEY");
        if (!checkcode1.equals(code)) {
            model.addAttribute("msg", "验证码错误");
            return "login";
        } else {
            try {
                User user = userLoginService.getAUser(username, password);
                int status = user.getStatus();
                session.setAttribute("user", user);
                model.addAttribute("user", user);
                if (status == 1) {
                    return "redirect:/showUser/1";
                } else {
                    return "index";
                }

            } catch (BaseException ex) {
                model.addAttribute("msg", ex.getMessage());
            }
            return "login";
        }
    }

    //  用户信息显示
    @RequestMapping("/showUser/{page}")
    public String showUser(Model model, @PathVariable("page") int page) {
        //System.out.println("dddddddd");
        PageInfo<User> user = userLoginService.pageUser(page, 5);
        //System.out.println(userInfo.getList());
        model.addAttribute("userList", user.getList());
        model.addAttribute("currPage", user.getPageNum());
        model.addAttribute("totalPage", user.getPages());
        return "showUser";
    }




    //前往用户注册界面
    @RequestMapping("/register")
    public String toRegister() {
        return "register";
    }

    //用户注册验证处理
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(String username,  String password1, String password2, Model model) {
        if (!password2.equals(password1)) {
            model.addAttribute("msg", "请输入相同密码");
            return "register";
        } else {
            try {
                userLoginService.addByName(username, password1);
                model.addAttribute("msg", "用户注册成功");
                return "login";
            } catch (BaseException ex) {
                model.addAttribute("msg", ex.getMessage());
            }
            return "register";
        }
    }



    //管理员前往添加用户界面
    @RequestMapping(value = "/addUser",method = RequestMethod.GET)
    public String toAddUser(){
        return  "addUser";

    }


    //管理员增加用户处理
    @RequestMapping(value = "/addUser",method = RequestMethod.POST)
    public String addUser(String username,String password1, String password2, String status,Model model) {
        if (!password2.equals(password1)) {
            model.addAttribute("msg", "请输入相同密码");
            return "addUser";
        } else {
            if(!status.equals("用户")) {
                try {
                    userLoginService.addByName(username, password1);
                    model.addAttribute("msg", "用户添加成功");
                } catch (BaseException ex) {
                    model.addAttribute("msg", ex.getMessage());
                }
                return "addUser";
            } else{
                try {
                    userLoginService.addByName(username, password1);
                    model.addAttribute("msg", "管理员添加成功");
                    return "addUser";
                } catch (BaseException ex) {
                    model.addAttribute("msg", ex.getMessage());
                }
                return "addUser";
            }
        }
    }
//        try{
//            userLoginService.addByName(username,password1);
//            model.addAttribute("msg", "用户注册成功");
//            return "login";
//        } catch (BaseException ex) {
//            model.addAttribute("msg", ex.getMessage());
//        }
//        return "register";
//       }

    // 修改用户信息页面显示
    @RequestMapping(value = "/editUser/{username}/{currPage}", method = {RequestMethod.GET})
    public String toUpdate(@PathVariable("username") String username,
                           @PathVariable("currPage") int currPage, Model model) {
        User user= userLoginService.getUserByName1(username);
        model.addAttribute("userInfo", user);
        model.addAttribute("currPage", currPage);
        return "editUser";
    }

    // 修改用户信息处理
    @RequestMapping(value = "/editUser", method = {RequestMethod.POST})
    public String updateUser(String username, String password, String phone) {
        //System.out.println(username+password+phone);
        userLoginService.update(password, username, phone);
        return "redirect:/showUser/1";
    }

    // 删除用户
    @RequestMapping(value = "/removeUser/{username}/{currPage}", method = {RequestMethod.GET})
    public String deleteUser(@PathVariable("username") String username, @PathVariable("currPage") int currPage,
                             Model model) {
        userLoginService.removeByName(username);
        return "redirect:/showUser/{currPage}";
    }

    // 搜索用户
    @RequestMapping(value = "selectUser", method = {RequestMethod.POST})
    private String selectUser(String findByName, Model model) throws Exception {
        List<User> user = userLoginService.getUserByName2(findByName);
        model.addAttribute("userList", user);
        return "showUser";
    }
}

