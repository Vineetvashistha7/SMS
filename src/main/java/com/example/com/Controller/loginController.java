package com.example.com.Controller;

import com.example.com.Entity.admin;
import com.example.com.Repository.adminRepo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class loginController {

    @Autowired
    adminRepo aR;

    @GetMapping({"/", "/login"})
    public String home(){

        return "login";
    }
    @PostMapping("/login")
    public String login(HttpServletRequest request, @RequestParam String email, @RequestParam String password){

        admin a=aR.findByEmailAndPasswordAndStatus(email,password,1);
        if(a == null){
            return "login";
        }
        else{
            HttpSession session = request.getSession();
            session.setAttribute("user", a);
            return "index";
        }
    }

    @GetMapping("/signup")
    public String registerPage()
    {
        return "register";
    }


    @PostMapping("/register")
    public String register(@ModelAttribute admin a){
        aR.save(a);
        return "redirect:/login";
    }

    @GetMapping({"/logout"})
    public String logout(HttpServletRequest req){
        HttpSession session = req.getSession();  // Get the current session, if it exists
        if (session != null) {
            session.invalidate();  // Invalidate the session to log the user out
        }
        return "redirect:/login";  // Redirect to the login page after logout
    }

}
