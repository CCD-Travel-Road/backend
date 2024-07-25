package YeoroProject.Yeoro.Service;

import YeoroProject.Yeoro.Model.CourseModel;
import YeoroProject.Yeoro.Repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseSrvice {
    @Autowired
    private static CourseRepository courseRepository;

    public static CourseModel saveCourse(CourseModel course) {
        return courseRepository.save(course);
    }

}
