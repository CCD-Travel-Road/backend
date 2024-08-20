package YeoroProject.Yeoro.Model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "travel_spots")
public class TravelSpot {

    @Id
    private String id;

    //외래키
    private String courseId;            // 코스 아이디
    private String userId;              // 제작하는 회원 아이디

    private String travelSpotName;      // 여행지 이름
    private String budget;              // 예산
    private String food;                // 음식
    private String publicTransport;     // 대중교통
    private String memo;                // 메모
}
