package YeoroProject.Yeoro.Model;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Getter
@Setter
@Document(collection = "travel_courses")
public class TravelCourse {
    @Id
    private String id;                    // 코스 아이디

    private String userId;                      // 코스 제작하는 회원 아이디
    private String courseName;                  // 코스 이름
    // private String description;              // 코스 설명
    private String location;                    // 주 지역 이름(ex.서울, 전주, 제주도, ...)
    private LocalDate startDate;                // 여행 시작일
    private LocalDate endDate;                  // 여행 마지막일
    private List<List<String>> travelSpots;     // 날짜별 여행지 리스트
    private String updateDate;                  // 코스 수정 날짜, 시간(가장 최근 업데이트 시간)

}
