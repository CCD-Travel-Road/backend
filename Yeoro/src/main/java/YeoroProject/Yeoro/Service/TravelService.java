package YeoroProject.Yeoro.Service;

import YeoroProject.Yeoro.Model.TravelCourse;
import YeoroProject.Yeoro.Model.TravelSpot;
import YeoroProject.Yeoro.Repository.TravelCourseRepository;
import YeoroProject.Yeoro.Repository.TravelSpotRepository;
import YeoroProject.Yeoro.Repository.UserRepository;
import YeoroProject.Yeoro.dto.TravelCourseRequest;
import YeoroProject.Yeoro.dto.TravelDetailsRequest;
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
    private final UserService userService;

    @Autowired
    public TravelService(TravelCourseRepository travelCourseRepository,
        TravelSpotRepository travelSpotRepository, UserRepository userRepository, UserService userService) {
        this.travelCourseRepository = travelCourseRepository;
        this.travelSpotRepository = travelSpotRepository;
        this.userService = userService;
    }

    /**
     * 새로운 여행 코스를 생성하거나 업데이트 최근 업데이트 날짜와 유저로 된 코스 존재 시 코스 수정 기능 최근 업데이트 날짜 존재하지 않으면 새로운 코스 저장
     *
     * @param travelCourseRequest 여행 코스를 저장 및 업데이트 하기 위한 DTO
     * @return 저장된 코스의 데이터가 전부 반홥됩니다.
     */

    // 여행지 중복 확인해야함. (코스 아이디로 확인)
    public String createTravelCourse(TravelCourseRequest travelCourseRequest) {

        // 유저 정보 학인
        String userID = userService.findById(travelCourseRequest.getUserEmail());

        // 동일한 코스 이름과 날짜를 가진 코스가 있는지 확인 (updateDate 포함)
        String[] courseDate = {travelCourseRequest.getStartDate().toString(), travelCourseRequest.getEndDate().toString()};
        String existingCourseID = findByCourseID(userID, travelCourseRequest.getCourseName(), courseDate, travelCourseRequest.getUpdateDate());

        if (!existingCourseID.equals("해당하는 코스가 존재하지 않습니다.")) {
            // 동일한 코스가 존재할 경우 업데이트 처리
            Optional<TravelCourse> existingCourseOptional = travelCourseRepository.findById(existingCourseID);
            if (existingCourseOptional.isPresent()) {
                TravelCourse existingCourse = existingCourseOptional.get();
                return saveOrUpdateCourse(existingCourse, travelCourseRequest);
            }
        }

        // 새 코스 저장
        TravelCourse newCourse = new TravelCourse();
        newCourse.setUserId(userID);
        return saveOrUpdateCourse(newCourse, travelCourseRequest);
    }

    /**
     * TravelCourse 객체를 저장 또는 업데이트하는 공통 함수
     *
     * @param travelCourse 및 업데이트 할 TravelCourse 객체
     * @param travelCourseRequest DTO로 받은 여행 코스 정보
     * @return 저장 또는 업데이트 결과 메시지
     */
    private String saveOrUpdateCourse(TravelCourse travelCourse, TravelCourseRequest travelCourseRequest) {
        boolean isNew = travelCourse.getId() == null;  // 새로운 객체인지 확인
        travelCourse.setCourseName(travelCourseRequest.getCourseName());
        travelCourse.setLocation(travelCourseRequest.getLocation());
        travelCourse.setStartDate(travelCourseRequest.getStartDate());
        travelCourse.setEndDate(travelCourseRequest.getEndDate());
        travelCourse.setTravelSpots(travelCourseRequest.getTravelSpots());
        travelCourse.setUpdateDate(travelCourseRequest.getUpdateDate());

        travelCourseRepository.save(travelCourse);

        return isNew ? "코스가 성공적으로 저장되었습니다." : "코스가 성공적으로 업데이트되었습니다.";
    }

    /**
     * 세부 코스를 저장하는 함수
     *
     * @param travelDetailsRequest 세부 코스 저장을 위한 DTO
     * @return 저장된 세부 코스의 데이터
     */
    public String createTravelSpotDetails(TravelDetailsRequest travelDetailsRequest) {
        // 유저 정보 확인
        String userID = userService.findById(travelDetailsRequest.getUserEmail());

        // 코스 존재 여부 확인 (코스 이름, 날짜 및 업데이트 날짜 기반으로)
        String[] courseDate = travelDetailsRequest.getTravelDate().toArray(new String[0]);
        String existingCourseID = findByCourseID(userID, travelDetailsRequest.getCourseName(), courseDate, travelDetailsRequest.getUpdateDate());

        if (existingCourseID.equals("해당하는 코스가 존재하지 않습니다.")) {
            return "해당하는 코스가 존재하지 않습니다. 먼저 코스를 생성해주세요.";
        }

        // 여행지 중복 확인
        Optional<TravelSpot> existingSpot = travelSpotRepository.findByCourseIdAndTravelSpotName(existingCourseID, travelDetailsRequest.getTravelSpotName());
        if (existingSpot.isPresent()) {
            return "해당하는 여행지는 이미 존재합니다.";
        }

        // 세부 코스 저장
        TravelSpot travelSpot = new TravelSpot();
        travelSpot.setCourseId(existingCourseID);
        travelSpot.setUserId(userID);
        travelSpot.setTravelSpotName(travelDetailsRequest.getTravelSpotName());
        travelSpot.setBudget(travelDetailsRequest.getBudget());
        travelSpot.setFood(travelDetailsRequest.getFood());
        travelSpot.setPublicTransport(travelDetailsRequest.getPublicTransport());
        travelSpot.setMemo(travelDetailsRequest.getMemo());

        // 세부 코스 저장
        travelSpotRepository.save(travelSpot);
        return "세부 코스가 성공적으로 저장되었습니다.";
    }

    /**
     * 코스가 존재하는지의 여부를 확인 및 courseID 반환
     *
     * @param courseName 부모에 해당하는 코스 이름
     * @param courseDate 코스의 전체 날짜 [20XX-XX-XX, 20XX-XX-XX]
     * @return 코스 ID값을 반환합니다.
     */
    public String findByCourseID(String userID, String courseName, String[] courseDate, String updateDate) {

        // courseDate 배열이 null이거나 비어있는지 확인
        if (courseDate == null || courseDate.length == 0) {
            throw new RuntimeException("여행 날짜가 제공되지 않았습니다.");
        }

        // DTO의 날짜 배열 첫 번째 값과 마지막 값
        List<String> travelDate = Arrays.stream(courseDate).toList();
        if (travelDate.isEmpty()) {
            throw new RuntimeException("여행 날짜가 제공되지 않았습니다.");
        }

        LocalDate dtoStartDate = LocalDate.parse(courseDate[0]);
        LocalDate dtoEndDate = LocalDate.parse(courseDate[1]);

        // 유저 ID, 코스 이름으로 코스 찾기
        Optional<TravelCourse> travelCourse = travelCourseRepository.findByUserIdAndCourseName(
            userID, courseName);

        if (travelCourse.isPresent()) {
            TravelCourse course = travelCourse.get();

            // 코스 날짜 확인
            if (course.getStartDate().equals(dtoStartDate) && course.getEndDate().equals(dtoEndDate)) {
                // updateDate가 null이 아니거나 course.getUpdateDate()가 null이 아닐 때 비교
                if ((updateDate == null && course.getUpdateDate() == null) || (updateDate != null && updateDate.equals(course.getUpdateDate()))) {
                    return course.getId();
                }
            }
        }
        return "해당하는 코스가 존재하지 않습니다.";
    }
}
