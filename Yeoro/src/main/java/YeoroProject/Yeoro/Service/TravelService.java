package YeoroProject.Yeoro.Service;

import YeoroProject.Yeoro.Model.TravelCourse;
import YeoroProject.Yeoro.Repository.TravelCourseRepository;
import YeoroProject.Yeoro.Repository.TravelSpotRepository;
import YeoroProject.Yeoro.Repository.UserRepository;
import YeoroProject.Yeoro.dto.TravelCourseRequest;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TravelService {


    private final TravelCourseRepository travelCourseRepository;
    private final TravelSpotRepository travelSpotRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public TravelService(TravelCourseRepository travelCourseRepository,
        TravelSpotRepository travelSpotRepository, UserRepository userRepository, UserService userService) {
        this.travelCourseRepository = travelCourseRepository;
        this.travelSpotRepository = travelSpotRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    /**
     * 새로운 여행 코스를 생성하거나 업데이트 최근 업데이트 날짜와 유저로 된 코스 존재 시 코스 수정 기능 최근 업데이트 날짜 존재하지 않으면 새로운 코스 저장
     *
     * @param travelCourseRequest 여행 코스를 저장 및 업데이트 하기 위한 DTO
     * @return 저장된 코스의 데이터가 전부 반홥됩니다.
     */

    // 여행지 중복 확인해야함. (날짜랑 해당 유저로 된 코스 존재하는지! )
    public String createTravelCourse(TravelCourseRequest travelCourseRequest) {

        TravelCourse travelCourse = new TravelCourse();
        travelCourse.setUserId(userService.findById(travelCourseRequest.getUserEmail()));
        travelCourse.setCourseName(travelCourseRequest.getCourseName());
        travelCourse.setLocation(travelCourseRequest.getLocation());
        travelCourse.setStartDate(travelCourseRequest.getStartDate());
        travelCourse.setEndDate(travelCourseRequest.getEndDate());
        travelCourse.setTravelSpots(travelCourseRequest.getTravelSpots());
        travelCourse.setUpdateDate(travelCourseRequest.getUpdateDate());
        // 저장
        travelCourseRepository.save(travelCourse);
        return "코스가 성공적으로 저장되었습니다.";
    }

    // 코스에서 여행지의 세부 내용을 추가하거나 업데이트
//    public TravelSpot createTravelSpotDetails(TravelDetailsRequest travelDetailsRequest) {
//        TravelSpot travelSpot = new TravelSpot();
//        return
//    }




    /**
     * 코스가 존재하는지의 여부를 확인 및 courseID 반환
     *
     * @param courseName 부모에 해당하는 코스 이름
     * @param courseDate 코스의 전체 날짜 [20XX-XX-XX, 20XX-XX-XX]
     * @return 코스 ID값을 반환합니다.
     */
    public String findByCourseID(String courseName, String[] courseDate) {
        // DTO의 날짜 배열 첫 번째 값과 마지막 값
        List<String> travelDate = Arrays.stream(courseDate).toList();
        if(travelDate.isEmpty()) {
            throw new RuntimeException("여행 날짜가 제공되지 않았습니다.");
        }

        LocalDate dtoStartDate = LocalDate.parse(courseDate[0]);
        LocalDate dtoEndDate = LocalDate.parse(courseDate[1]);

        /* 코스 존재 여부 확인 */
        // 코스 이름 확인
        Optional<TravelCourse> travelCourse = travelCourseRepository.findByCourseName(courseName);

        // 코스 날짜 확인 (시작 날짜와 마지막 날짜)
        if (travelCourse.isPresent()) {
            // 코스 날짜 확인 (시작 날짜와 마지막 날짜)
            TravelCourse course = travelCourse.get();
            if (course.getStartDate().equals(dtoStartDate) && course.getEndDate().equals(dtoEndDate)) {
                // 일치 시 해당 코스 아이디 전송
                return course.getId();
            } else {
                return "해당하는 코스가 존재하지 않습니다.";
            }
        } else {
            return "해당하는 코스가 존재하지 않습니다.";
        }
    }



}
