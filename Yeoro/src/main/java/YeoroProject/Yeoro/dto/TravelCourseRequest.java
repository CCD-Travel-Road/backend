package YeoroProject.Yeoro.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TravelCourseRequest {

    private String userEmail;                       //제작자 이메일
    private String courseName;                      // 현재 작성죽인 코스의 이름
    private String location;                        // 지역 위치
    private LocalDate startDate;                    // 여행 시작일
    private LocalDate endDate;                      // 여행 마지막일
    private List<List<String>> travelSpots;         // 여행지 목록
    private String createDate;                      // 코스 제작 시간(가장 마지막에 저장하는 업데이트 시간)
    private String updateDate;                      // 코스 제작 시간(현재 저장하는 업데이트 시간)
}
