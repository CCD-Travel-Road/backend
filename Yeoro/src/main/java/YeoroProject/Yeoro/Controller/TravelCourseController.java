package YeoroProject.Yeoro.Controller;

import YeoroProject.Yeoro.Model.TravelCourse;
import YeoroProject.Yeoro.Service.TravelService;
import YeoroProject.Yeoro.dto.TravelCourseRequest;
import YeoroProject.Yeoro.dto.TravelDetailsRequest;
import YeoroProject.Yeoro.dto.TravelSendRequest;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public/travel")
public class TravelCourseController {

    private static final Logger logger = LoggerFactory.getLogger(TravelCourseController.class); // 클래스 수준의 로거 변수

    @Autowired
    private TravelService travelService;

    /**
     * 새로운 여행 코스를 생성하는 엔드포인트
     * @param travelCourseRequest 여행 코스 내용 저장 DTO
     * @return 코스 저장 성공 여부
     */
    @PostMapping("/create")
    public ResponseEntity<String> createTravelCourse(@RequestBody TravelCourseRequest travelCourseRequest) {
        String response = travelService.createTravelCourse(travelCourseRequest);
        return ResponseEntity.ok(response);
    }


    /**
     * 여행 코스의 세부 내용 저장하는 엔드포인트
     * @param travelDetailsRequest 여행 코스의 세부 내용 저장 DTO
     * @return 세부 내용 저장 성공 여부
     */
    @PostMapping("/detail")
    public ResponseEntity<String> addTravelSpotDetails(@RequestBody TravelDetailsRequest travelDetailsRequest) {
        String response = travelService.createTravelSpotDetails(travelDetailsRequest);
        return ResponseEntity.ok(response);
    }

    /**
     * 사용자의 이메일로 모든 여행 코스와 세부 여행지 정보를 조회하는 엔드포인트
     * @param userEmail userEmail 사용자 이메일 (쿼리 파라미터)
     * @return 사용자의 여행 코스와 세부 여행지 정보를 포함한 리스트
     */
    @GetMapping("/getAll")
    public List<TravelSendRequest> getTravelSendRequest(@RequestParam String userEmail) {
        return travelService.getAllTravelCourses(userEmail);
    }






}
