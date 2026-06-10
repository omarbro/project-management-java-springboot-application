package com.example.demo.service;
import com.example.demo.model.Report;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {
    @Autowired
    private ProjectService projectService;

/*

    public String exportReport(List<Report> report, String reportFormat) throws FileNotFoundException, JRException {
        String path="C:\\Users\\omar\\Documents";
//        List<Project> projects=projectService.allProjects(start,end,start,end);
        File file= ResourceUtils.getFile("classpath:projectReport.jrxml");

        JasperReport jasperReport= JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource=new JRBeanCollectionDataSource(report);
        Map<String, Object> parameters=new HashMap<>();
        JasperPrint jasperPrint= JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        if(reportFormat.equalsIgnoreCase("html")){
            JasperExportManager.exportReportToHtmlFile(jasperPrint, path+"\\projects.html");
        }
        if(reportFormat.equalsIgnoreCase("pdf")){
            JasperExportManager.exportReportToPdfFile(jasperPrint, path+"\\projects.pdf");
        }

        return "reportGenerated";
    }*/
   public byte[] exportReport(List<Report> report, String reportFormat) throws JRException,FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:projectReport.jrxml");

        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(report);
        Map<String, Object> parameters = new HashMap<>();
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        if (reportFormat.equalsIgnoreCase("pdf")) {
            return JasperExportManager.exportReportToPdf(jasperPrint);
        }

        return JasperExportManager.exportReportToPdf(jasperPrint);
    }

}


