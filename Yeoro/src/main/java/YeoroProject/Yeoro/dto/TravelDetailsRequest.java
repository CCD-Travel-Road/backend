package YeoroProject.Yeoro.dto;


import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TravelDetailsRequest {

    private String userEmail;                   // 유저 확인용 이메일
    private String courseName;                  // 코스 존재 여부 확인용 코스 이름
    private LocalDate startDate;                // 코스 존재 여부 확인용
    private LocalDate endDate;
    public LocalDate updateDate;                    // 최근 업데이트 날짜

    private String travelSpotName;              // 여행지 이름(해당 지역 여행지 or 식당 or 카페 ...)
    private String budget;                      // 예산
    private String food;                        // 음식
    private String publicTransport;             // 대중교통
    private String memo;                        // 메모

}
