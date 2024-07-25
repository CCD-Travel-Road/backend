package YeoroProject.Yeoro.Model;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Getter
@Setter
@Document(collection = "courses")
public class CourseModel {
    @Id
    private String id;
    private String courseId;
    private String creatorId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String majorTravelArea;
    private List<String> detailedTravelDestinations;

}
