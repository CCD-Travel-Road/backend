package YeoroProject.Yeoro.Repository;

import YeoroProject.Yeoro.Model.CourseModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CourseRepository extends MongoRepository<CourseModel, String> {

}
