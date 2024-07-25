package YeoroProject.Yeoro.Controller;

import YeoroProject.Yeoro.Model.CourseModel;
import YeoroProject.Yeoro.Repository.CourseRepository;
import YeoroProject.Yeoro.Service.CourseSrvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/courses")
public class CourseController {


    @Autowired
    private CourseRepository courseRepository;


    @PostMapping
    public CourseModel saveCourse(@RequestBody CourseModel course) {
        return CourseSrvice.saveCourse(course);
    }

}
