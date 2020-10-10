package com.cxw.web.admin;


import com.cxw.po.User;
import com.cxw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private UserService userService;

    //跳转到登录页面
    @GetMapping
    public String loginPage() {
        return "admin/login";
    }

    //判断登录的账号密码
    @PostMapping("/login")
    //@RequestParam传入参数用户名密码
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes) {
        //调用userService的checkUser方法，将username和password参数传入该方法
        User user = userService.checkUser(username,password);
        //判断用户名密码是否为空
        if(user != null){
            //账号密码验证通过
            //需要将User参数传递到前端，所以要将密码设为空，不把密码传到前面
            user.setPassword(null);
            session.setAttribute("user",user);
            return "admin/index";
        }else {
            //账号密码验证不通过
            //传递信息给前端
            attributes.addFlashAttribute("message","用户名和密码错误");

            //使用重定向方式，跳转到@RequestMapping("/admin")下的方法loginPage
            return "redirect:/admin";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        //使用session里面的方法把session去掉
        session.removeAttribute("user");
        return "redirect:/admin";
    }

}
