package com.example.demo;

import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.spi.CalendarNameProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@Controller
public class ProjectController {
    @Autowired
    private ProjectRepository projRepo;

    @Autowired
    private ProjectService projService;

    @Autowired
    private UserService userService;

    @Autowired
    private ReportService reportService;

    @GetMapping("/projects")
    public String showProjects(Model model,Project project,HttpSession session) throws ParseException {
        Date startDate;
        Date endDate;

//        if(start == null || end == null){
            Calendar calendar= Calendar.getInstance();
            int curMonth=calendar.get(calendar.MONTH);
            int curYear=calendar.get(calendar.YEAR);
            calendar.set(Calendar.MONTH,curMonth);
            calendar.set(Calendar.YEAR,curYear);
            calendar.set(Calendar.DAY_OF_MONTH,1);
            startDate=calendar.getTime();
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            endDate=calendar.getTime();
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
        if(session.getAttribute("userName") != null) {
            List<Project> projects = projService.allProjects(startDate, endDate, startDate, endDate);
            System.out.println(projects);
            model.addAttribute("projects", projects);
            return "projects";
        }else {
            return "redirect:/login";
        }
    }

//    @GetMapping("/reportDate")
//    public String projectsDateRange(HttpSession session){
//        if(session.getAttribute("userName") != null){
//            return "reportByDate";
//        }else{
//            return "redirect:/login";
//        }
//
//    }

    @GetMapping("/projectsFilter")
    public String projectsFilter(Model model, Project project, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
                                 @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date end, HttpSession session) throws ParseException{

        if(session.getAttribute("userName") != null){
            List<Project> projects=projService.allProjects(start,end,start,end);
            System.out.println(projects);
            model.addAttribute("projects",projects);
            return "projects";
        }else{
            return "redirect:/login";
        }

    }


        @GetMapping("/addProject")
        public String showProjectAdd(HttpSession session){
//            Project project=new Project();
//            model.addAttribute("project", project);

            if(session.getAttribute("userName") != null){
                return "projectForm";
            }else{
                return "redirect:/login";
            }

        }

        @PostMapping("/addProject")
        public String addProject(@ModelAttribute Project project, HttpSession session, @RequestParam("member") List<String> memberList){

            String userName=(String) session.getAttribute("userName");
            System.out.println("asasasa----------");
            project.setProjectOwner(userName);
            System.out.println(project.getProjectOwner()+"this is get method");
            List<User> userList=new ArrayList<User>();
            for (String uname: memberList) {
                User userMem=userService.findByUserName(uname);
                userList.add(userMem);
            }

            if(session.getAttribute("userName") != null){
                project.setProjectMembers(userList);
                projRepo.save(project);
                return "welcome project";
            }else{
                return "redirect:/login";
            }

        }

        @GetMapping("/editProject/{id}")
        public String editUserPage(Model model, @PathVariable Long id, HttpSession session){
            if(session.getAttribute("userName") != null){
                Project project= projService.getProjectById(id);
                model.addAttribute("project",project);
                return "edit_project";
            }else{
                return "redirect:/login";
            }

        }

        @PostMapping("/updateProject")
        public String updateProject(@ModelAttribute Project project, @RequestParam("member") List<String> memberList , HttpSession session){
            if(session.getAttribute("userName") != null){
                System.out.println("bcsjhkdgc");
                Project existProject= projService.getProjectById(project.getId());
                System.out.println(existProject);

                List<User> mList=new ArrayList<>();
                for(String ul: memberList){
                    User userL=userService.findByUserName(ul);
                    mList.add(userL);
                }
                project.setProjectMembers(mList);

                if(project.getProjectName() != null){
                    existProject.setProjectName(project.getProjectName());
                }
                if(project.getProjectIntro() != null){
                    existProject.setProjectIntro(project.getProjectIntro());
                }
                if(project.getStatus() != 0){
                    existProject.setStatus(project.getStatus());
                }
                if(project.getStartDateTime() != null){
                    existProject.setStartDateTime(project.getStartDateTime());
                }
                if(project.getEndDateTime() != null){
                    existProject.setEndDateTime(project.getEndDateTime());
                }
                if(project.getProjectMembers() != null){
                    existProject.setProjectMembers(project.getProjectMembers());
                }
                projRepo.save(existProject);

                return "redirect:/projects";
            }else{
                return "redirect:/login";
            }

        }
         @GetMapping("deleteProject/{id}")
         public String deleteProject(@PathVariable Long id,HttpSession session){
             if(session.getAttribute("userName") != null){
                 Project project= projService.getProjectById(id);
                 projRepo.delete(project);

                 return "redirect:/projects";
             }else{
                 return "redirect:/login";
             }


         }
         @GetMapping("/reportDate")
         public String reportDate(){
            return "reportByDate";
         }
     /*    @GetMapping("/report/{format}")
         public String generateReport( @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
                                      @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date end, @PathVariable("format") String format, HttpSession session) throws FileNotFoundException, JRException {
             System.out.println(format);
            List<Project> projects=projService.allProjects(start,end,start,end);
             List<Report> reportProjects = new ArrayList<Report>();

             for(Project project: projects){
                 Report report=new Report();
                 report.setId(project.getId());
                 report.setProjectName(project.getProjectName());
                 report.setProjectIntro(project.getProjectIntro());
                 report.setStatus(project.getStatus());
                 report.setProjectOwner(project.getProjectOwner());
                 report.setStartDateTime(project.getStartDateTime());
                 report.setEndDateTime(project.getEndDateTime());


                 if (project.getProjectMembers() != null){
                     List<String> memberList= new ArrayList<String>();
                     for (User member: project.getProjectMembers()){
                         memberList.add(member.getUserName());
                     }
                     report.setProjectMembers(memberList);
                 }

                 reportProjects.add(report);
             }
            if(session.getAttribute("userName") != null){
                return reportService.exportReport(reportProjects, format);
            }else{
                return "redirect:/login";
             }

        } */

@GetMapping("/report/{format}")
        public ResponseEntity<byte[]> generateReport(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
                                     @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date end, @PathVariable("format") String format, HttpSession session) throws JRException,FileNotFoundException {

            if (session.getAttribute("userName") == null) {
                return ResponseEntity.status(HttpStatus.FOUND)
                        .header(HttpHeaders.LOCATION, "/login")
                        .build();
            }

            List<Project> projects = projService.allProjects(start, end, start, end);
            List<Report> reportProjects = new ArrayList<Report>();

            for (Project project : projects) {
                Report report = new Report();
                report.setId(project.getId());
                report.setProjectName(project.getProjectName());
                report.setProjectIntro(project.getProjectIntro());
                report.setStatus(project.getStatus());
                report.setProjectOwner(project.getProjectOwner());
                report.setStartDateTime(project.getStartDateTime());
                report.setEndDateTime(project.getEndDateTime());

                if (project.getProjectMembers() != null) {
                    List<String> memberList = new ArrayList<String>();
                    for (User member : project.getProjectMembers()) {
                        memberList.add(member.getUserName());
                    }
                    report.setProjectMembers(memberList);
                }

                reportProjects.add(report);
            }

            byte[] pdfBytes = reportService.exportReport(reportProjects, format);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=projects.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfBytes);
        }


}

