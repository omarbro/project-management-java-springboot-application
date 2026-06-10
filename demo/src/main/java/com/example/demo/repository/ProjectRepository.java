package com.example.demo.repository;
import com.example.demo.model.Project;

        import org.springframework.data.jpa.repository.JpaRepository;
        import org.springframework.stereotype.Repository;

        import javax.persistence.Id;
        import java.util.Date;
        import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Long> {
    public List<Project> findByStartDateTimeBetweenOrEndDateTimeBetween(Date startDate, Date endDate, Date sDate, Date eDate);
    public List<Project> findByStartDateTimeBeforeAndEndDateTimeAfter(Date startDate, Date endDate);


    Project getProjectById(Long id);
}
