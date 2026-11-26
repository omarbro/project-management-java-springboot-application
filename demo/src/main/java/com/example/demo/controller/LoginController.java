package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.model.Project;

import com.example.demo.service.UserService;
import com.example.demo.service.ProjectService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
public class LoginController {

    @Autowired
    UserService userService;

    @Autowired
    ProjectService projService;

    @GetMapping("/")
     public String index(Model model){
        Date startDate;
        Date endDate;

        Calendar calendar = Calendar.getInstance();
        int curMonth = calendar.get(Calendar.MONTH);
        int curYear = calendar.get(Calendar.YEAR);
        calendar.set(Calendar.MONTH, curMonth);
        calendar.set(Calendar.YEAR, curYear);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        startDate = calendar.getTime();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        endDate = calendar.getTime();

        List<Project> projects = projService.allProjects(startDate, endDate, startDate, endDate);
        model.addAttribute("projects", projects);
        return "projects";
    }

//        }
//        else{
//            SimpleDateFormat dateFormat=new SimpleDateFormat("MM/dd/yyyy");
//            try{
//                startDate=dateFormat.parse(start);
//                endDate=dateFormat.parse(end);
//            }catch (ParseException e){
//                String err="Your Date input is not valid";
//                return err;
//            }
//            }
        /*if(session.getAttribute("userName") != null) {
            List<Project> projects = projService.allProjects(startDate, endDate, startDate, endDate);
            System.out.println(projects);
            model.addAttribute("projects", projects);
            return "projects";
        }else {
            return "redirect:/login";
        } */
    @GetMapping("/login")
    public String showLoginForm (Model model){
        User user=new User();
        model.addAttribute("user",user);
        return "login";
    }

  /**   @PostMapping("/login")
    public String login(@ModelAttribute User user, Model model, HttpSession session){
        System.out.println(user.getEmail());
//        if(user.getEmail().equals("email") && user.getPassword().equals("password")){
//            System.out.println("heel");
//            model.addAttribute("user", user.getUserName());
//            return "welcome";
//        }
        User regUser= userService.findByEmailAndPassword(user.getEmail(),user.getPassword());
        if(regUser != null){
            session.setAttribute("userName",regUser.getUserName());
            model.addAttribute("user",regUser.getUserName());
            return "welcome";
        }
        return "login";
    } */
    /*  @GetMapping("/logout")
    public String logout(HttpSession session){
        if(session.getAttribute("userName")!=null){
            session.invalidate();
            return "redirect:/login";
        }
        else{
            return "redirect:/login";
        }
    }*/

}
