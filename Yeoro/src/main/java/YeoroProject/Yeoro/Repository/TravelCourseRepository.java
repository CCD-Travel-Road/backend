package YeoroProject.Yeoro.Repository;

import YeoroProject.Yeoro.Model.TravelCourse;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelCourseRepository extends MongoRepository<TravelCourse, String> {

    Optional<TravelCourse> findByCourseName (String courseName);

    boolean findByStartDateAndEndDate(LocalDate startDate, LocalDate endDate);

}
