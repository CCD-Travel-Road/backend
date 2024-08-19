package YeoroProject.Yeoro.Controller;

import YeoroProject.Yeoro.Model.TravelCourse;
import YeoroProject.Yeoro.Service.TravelService;
import YeoroProject.Yeoro.dto.TravelCourseRequest;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/travel")
public class TravelCourseController {

    private static final Logger logger = LoggerFactory.getLogger(TravelCourseController.class); // 클래스 수준의 로거 변수

    @Autowired
    private TravelService travelService;

    // 새로운 여행 코스를 생성하는 엔드포인트
    @PostMapping
    public ResponseEntity<String> createTravelCourse(@RequestBody TravelCourseRequest travelCourseRequest) {
        String travelCourse = travelService.createTravelCourse(travelCourseRequest);
        return ResponseEntity.ok(travelCourse);
    }

//    // 여행지 세부 내용을 추가하는 엔드포인트(이것도 DTO로 수정해서 한 번에 받기)
//    @PostMapping("/spot/details")
//    public TravelSpot addTravelSpotDetails(@RequestBody TravelDetailsRequest travelDetailsRequest) {
//        TravelSpot travelSpot = travelService.createTravelSpotDetails(travelDetailsRequest);
//        return ResponseEntity.ok(travelSpot).getBody();
//    }





}
