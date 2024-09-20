package YeoroProject.Yeoro.Service;

import YeoroProject.Yeoro.Model.TravelCourse;
import YeoroProject.Yeoro.Model.TravelSpot;
import YeoroProject.Yeoro.Repository.TravelCourseRepository;
import YeoroProject.Yeoro.Repository.TravelSpotRepository;
import YeoroProject.Yeoro.Repository.UserRepository;
import YeoroProject.Yeoro.dto.TravelCourseRequest;
import YeoroProject.Yeoro.dto.TravelDetailsRequest;
import YeoroProject.Yeoro.dto.TravelSendRequest;
import java.time.LocalDate;
import java.util.ArrayList;
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
    public String createTravelCourse(TravelCourseRequest travelCourseRequest) {

        // 유저 정보 학인
        String userID = userService.findById(travelCourseRequest.getUserEmail());

        // 기존 코스 확인
        Optional<String> existingCourseIdOptional = findCourseId(userID,
            travelCourseRequest.getCourseName(),
            travelCourseRequest.getStartDate(),
            travelCourseRequest.getEndDate(),
            travelCourseRequest.getUpdateDate());

        if (existingCourseIdOptional.isPresent()) {
            // 기존 코스가 있을 경우 업데이트
            Optional<TravelCourse> existingCourseOptional = travelCourseRepository.findById(existingCourseIdOptional.get());
            if (existingCourseOptional.isPresent()) {
                return saveOrUpdateCourse(existingCourseOptional.get(), travelCourseRequest);
            }
        }

        // 새 코스 저장
        TravelCourse newCourse = new TravelCourse();
        newCourse.setUserId(userID);
        return saveOrUpdateCourse(newCourse, travelCourseRequest);
    }


    /**
     * 세부 코스를 저장하는 함수
     *
     * @param travelDetailsRequest 세부 코스 저장을 위한 DTO
     * @return 저장된 세부 코스의 데이터
     */
    public String createTravelSpotDetails(TravelDetailsRequest travelDetailsRequest) {
        // 유저 정보 확인
        String userId = userService.findById(travelDetailsRequest.getUserEmail());

        // 기존 코스 확인
        Optional<String> existingCourseIdOptional = findCourseId(userId,
            travelDetailsRequest.getCourseName(),
            travelDetailsRequest.getStartDate(),
            travelDetailsRequest.getEndDate(),
            travelDetailsRequest.getUpdateDate());

        if (existingCourseIdOptional.isPresent()) {
            // 해당 코스 ID와 여행지 이름으로 기존 여행지 확인
            Optional<TravelSpot> existingSpotOptional = travelSpotRepository.findByCourseIdAndTravelSpotName(existingCourseIdOptional.get(), travelDetailsRequest.getTravelSpotName());

            if (existingSpotOptional.isPresent()) {
                // 기존 여행지가 있을 경우 업데이트
                return saveOrUpdateTravelSpot(existingSpotOptional.get(), travelDetailsRequest, existingCourseIdOptional.get());
            }

            // 새로운 세부 여행지 생성 및 저장
            TravelSpot newSpot = new TravelSpot();
            newSpot.setUserId(userId);
            return saveOrUpdateTravelSpot(newSpot, travelDetailsRequest, existingCourseIdOptional.get());

        } else {
            return "해당하는 코스가 존재하지 않습니다. 먼저 코스를 생성해주세요.";
        }
    }


    /**
     * 유저의 이메일을 기반으로 해당 유저가 제작한 여행 코스와 세부 정보를 조회하여 반환하는 메소드
     * @param userEmail 사용자 이메일
     * @return TravelSendRequest 객체 리스트
     */
    public List<TravelSendRequest> getAllTravelCourses(String userEmail) {
        // 사용자 ID 조회
        String userId = userService.findById(userEmail);

        // 사용자 ID로 여행 코스 조회
        List<TravelCourse> travelCourses = travelCourseRepository.findByUserId(userId);

        // 결과 저장 리스트 초기화
        List<TravelSendRequest> travelSendRequests = new ArrayList<>();

        // 각 코스에 대한 세부 여행지 정보 조회
       for(TravelCourse travelCourse : travelCourses) {
           TravelSendRequest response = new TravelSendRequest();
           response.setTravelCourse(travelCourse);

           // 코스 ID로 세부 코스 조회
           List<TravelSpot> travelSpots = travelSpotRepository.findByCourseId(travelCourse.getId());
           response.setTravelSpots(travelSpots);

           travelSendRequests.add(response);
       }

       return travelSendRequests;
    }



    /**
     * 유저 ID, 코스 이름, 날짜로 코스 ID를 확인 및 반환하는 메소드
     *
     * @param userId 사용자 ID
     * @param courseName 코스 이름
     * @param startDate 코스 시작 날짜
     * @param endDate 코스 종료 날짜
     * @param updateDate 코스 업데이트 날짜 (Optional)
     * @return Optional의 코스 ID값
     */
    public Optional<String> findCourseId(String userId, String courseName, LocalDate startDate, LocalDate endDate, LocalDate updateDate) {

        // 유저 ID, 코스 이름으로 코스 찾기
        Optional<TravelCourse> travelCourse = travelCourseRepository.findByUserIdAndCourseName(userId, courseName);

        if (travelCourse.isPresent()) {
            TravelCourse course = travelCourse.get();

            // 코스 날짜 확인
            if (course.getStartDate().equals(startDate) && course.getEndDate().equals(endDate)) {
                if ((updateDate == null && course.getUpdateDate() == null) ||
                    (updateDate != null && updateDate.equals(course.getUpdateDate()))) {
                    return Optional.of(course.getId());
                }
            }
        }
        return Optional.empty();
    }

    /**
     * TravelCourse 객체를 저장 또는 업데이트하는 공통 함수
     *
     * @param travelCourse 생성 및 업데이트 할 TravelCourse 객체
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
     * TravelSpot 객체를 저장 또는 업데이트하는 공통 함수
     *
     * @param travelSpot 생성 및 업데이트 할 TravelSpot 객체
     * @param travelDetailsRequest DTO로 받은 세부 코스 정보
     * @param courseId 코스 ID
     * @return 저장 또는 업데이트 결과 메시지
     */
    private String saveOrUpdateTravelSpot(TravelSpot travelSpot, TravelDetailsRequest travelDetailsRequest, String courseId) {
        boolean isNew = travelSpot.getId() == null;

        // 여행지 정보 설정
        travelSpot.setCourseId(courseId);
        travelSpot.setUserId(travelDetailsRequest.getUserEmail());  // userId 설정
        travelSpot.setTravelSpotName(travelDetailsRequest.getTravelSpotName());
        travelSpot.setBudget(travelDetailsRequest.getBudget());
        travelSpot.setFood(travelDetailsRequest.getFood());
        travelSpot.setPublicTransport(travelDetailsRequest.getPublicTransport());
        travelSpot.setMemo(travelDetailsRequest.getMemo());

        // 저장 또는 업데이트
        travelSpotRepository.save(travelSpot);

        return isNew ? "세부 코스가 성공적으로 저장되었습니다." : "세부 코스가 성공적으로 업데이트되었습니다.";
    }
}
