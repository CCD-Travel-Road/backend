package YeoroProject.Yeoro.dto;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustomRequest {
    private String date;                // 일정
    private String location;            // 여행 장소
    private String budget;              // 예산
    private String purpose;             // 여행 목적
    private String notes;               // 기타

}
