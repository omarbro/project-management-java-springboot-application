package com.example.demo.service;
import com.example.demo.model.Project;
import com.example.demo.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projRepo;

    public List<Project> allProjects(Date startDate, Date endDate, Date sDate, Date eDate){
        List<Project> list1=projRepo.findByStartDateTimeBetweenOrEndDateTimeBetween(startDate,endDate,sDate,eDate);
        List<Project> list2=projRepo.findByStartDateTimeBeforeAndEndDateTimeAfter(startDate, endDate);
//        List<Project> list3=projRepo.findByEndDateTimeBetween(startDate,endDate);
        list1.addAll(list2);
//        list1.addAll(list3);
        return list1;
    }

    public Project getProjectById(Long id) {
        return projRepo.getProjectById(id);
    }


}
