package com.example.demo.controller;
import com.example.demo.dto.ProjectResponse;
import com.example.demo.model.Project;
import com.example.demo.repository.ProjectRepository;

import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ProjectRestController {
    @Autowired
    private ProjectRepository projRepo;

    public ProjectRestController(ProjectRepository projRepo) {
        this.projRepo = projRepo;
    }
    @GetMapping("api/v1/projects")
    public List<ProjectResponse> getAllProjects(){
        List<ProjectResponse> result = new ArrayList<>();
        for (Project p : projRepo.findAll()){
            result.add(ProjectResponse.toDto(p));
        }
        return result;
    }
}
