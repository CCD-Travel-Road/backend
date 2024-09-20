package YeoroProject.Yeoro.dto;

import YeoroProject.Yeoro.Model.TravelCourse;
import YeoroProject.Yeoro.Model.TravelSpot;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TravelSendRequest {
    TravelCourse travelCourse;
    List<TravelSpot> travelSpots;       // 해당 코스에 대한 세부 내용

}