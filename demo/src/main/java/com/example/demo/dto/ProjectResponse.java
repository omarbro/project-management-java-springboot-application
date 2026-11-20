package com.example.demo.dto;

import javax.print.DocFlavor.STRING;

import com.example.demo.model.Project;
import com.example.demo.model.User;
import java.sql.Date;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectResponse {
    private final long id;
    private final String projectName;
    private final String projectIntro;
    private final String projectOwner;
    private final int status;
    private final Date startDateTime;
    private final Date endDateTime;
    private final List<String> members;

    private ProjectResponse(Project p, List<String> members){
        this.id =p.getId();
        this.projectName = p.getProjectName();
        this.projectIntro = p.getProjectIntro();
        this.projectOwner = p.getProjectOwner();
        this.status = p.getStatus();
        this.startDateTime = p.getStartDateTime();
        this.endDateTime = p.getEndDateTime();
        this.members = members;
    }

    public static ProjectResponse toDto(Project p){
        List<String> names = p.getProjectMembers() == null ? 
            Collections.emptyList() : 
            p.getProjectMembers().stream().map(User:: getUserName).collect(Collectors.toList());
        
        return new ProjectResponse(p, names);
    }

    public long getId(){return id;}
    public String getProjectName(){return projectName;}
    public String getProjectIntro() { return projectIntro; }
    public String getProjectOwner() { return projectOwner; }
    public int getStatus() { return status; }
    public Date getStartDateTime() { return startDateTime; }
    public Date getEndDateTime() { return endDateTime; }
    public List<String> getMembers(){return members;}




}
