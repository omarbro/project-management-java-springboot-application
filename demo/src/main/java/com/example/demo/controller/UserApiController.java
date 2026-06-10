package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;

@Controller
public class UserApiController {

    @GetMapping("/userdata/{id}")
    public String getUserPage(@PathVariable("id") Integer userId, HttpSession session) {

        if(session.getAttribute("userName") !=null ){

        session.setAttribute("dataId", userId );
        return "consumeApi";
        }
        else
            return "redirect:/login";
    }

}
