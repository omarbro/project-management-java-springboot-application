package com.example.demo.controller;

import com.example.demo.config.SecurityConfig;
import com.example.demo.model.Project;
import com.example.demo.model.User;
import com.example.demo.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProjectRestController.class)
@Import(SecurityConfig.class)
class ProjectRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectRepository projectRepository;

    @Test
    @WithMockUser
    void projectApiNeverExposesMemberEmailOrPassword() throws Exception {
        // Arrange: a project with one member who has an email and a password
        User member = new User();
        member.setUserName("john");
        member.setEmail("john@example.com");
        member.setPassword("secret-value");

        Project project = new Project();
        project.setId(1L);
        project.setProjectName("Demo");
        project.setProjectOwner("omar");
        project.setProjectMembers(Collections.singletonList(member));

        when(projectRepository.findAll()).thenReturn(Collections.singletonList(project));

        // Act + Assert
        mockMvc.perform(get("/api/v1/projects"))
                .andExpect(status().isOk())
                .andExpect(content().string(not(containsString("password"))))
                .andExpect(content().string(not(containsString("john@example.com"))))
                .andExpect(jsonPath("$[0].members[0]").value("john"));
    }
}
