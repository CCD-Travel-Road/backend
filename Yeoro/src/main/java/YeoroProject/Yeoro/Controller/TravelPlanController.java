package YeoroProject.Yeoro.Controller;

import YeoroProject.Yeoro.dto.CustomRequest;
import YeoroProject.Yeoro.gpt.OpenAiClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public/travel")
@CrossOrigin(origins = "http://localhost:3000")
public class TravelPlanController {

    private static final Logger logger = LoggerFactory.getLogger(TravelPlanController.class);

    @Autowired
    private OpenAiClient openAiClient;

    @PostMapping("/plan")
    public String getTravelPlan(@RequestBody CustomRequest request) {
        String prompt = generatePrompt(request);
        logger.info("Generated prompt: {}", prompt);
        try {
            return openAiClient.getTravelPlan(prompt);
        } catch (RuntimeException e) {
            logger.error("Error generating travel plan: {}", e.getMessage(), e);
            return "여행 계획 생성 중 오류가 발생했습니다.";
        }
    }

    private String generatePrompt(CustomRequest request) {
        return String.format("""
               여행 일자: %s
               장소: %s
               예산: %s
               여행 목적: %s
               기타 사항: %s
              
              위의 조건에 맞춘 도보 여행 일정(코스)을/를 다른 구성으로 3개를 제안해 주세요.\s
              여행 일정에 따라 코스 배열의 형식을 다음과 같이 구성해주세요:
              1. 당일치기 여행의 경우: 코스 당 하나의 배열로 [여행지 코스 배열] 형식으로 구성해 주세요. 배열 내부는 [여행지,여행지,여행지,...]의 형식이어야 합니다.
              2. 2일 이상의 여행일정의 경우: [1일차 여행지 코스 배열], [2일차 여행지 코스 배열], [3일차 여행지 코스 배열] 형식으로 구성해 주세요. 이때도 여행지 이름만 나열되어야 합니다.
              코스에서 1일차 2일차는 배열로 구분합니다. 배열안에 여행지 이름만 출력해주세요. 가로 배열은 여행 일수에 맞춰야 합니다. 그 이상의 추가는 안됩니다.
              
              여행지들을 다양해야 합니다. 검색을 통해 실제 있는 곳인지 확인을 한 후 여행 코스를 제작해주세요. 
              
              배열 예시
              course 1: [여행지 코스 배열], [여행지 코스 배열], [여행지 코스 배열], ...
              course 2: [여행지 코스 배열], [여행지 코스 배열], [여행지 코스 배열], ...
              course 3: [여행지 코스 배열], [여행지 코스 배열], [여행지 코스 배열], ...
               
               코스 조건
               course 1: 최단거리로 여행지들이 걸어서 한 여행지에서 다음 여행지로 이동 시 10분을 넘기지 않는 동선에 있는 곳으로 구성해주세요.
               course 2: 경치가 좋거나 사람들의 후기나 리뷰, 평점이 많거나 좋을 곳으로 구성해주세요.
               course 3: 음식이나 카페 등을 포함한 여행지로 SNS나 웹에서 인기가 많은 곳들로 구성해주세요.
               
              각 코스는 동선이 효율적으로 이루어져야 합니다. 모든 코스는 도보 이동을 원칙으로 합니다.
              출발하는 여행지는 유명한(사람들이 많이 찾고 SNS에 올리는 장소) 장소로 시작해주고, 다음 여행지는 출발지에서 도보로 가장 가까운 곳이어야 합니다. 최대 4가지의 여행지를 잡아주세요.
               
              특수하게 멀리 있는 여행지는 교통수단을 이용해 이동할 수 있도록 계획해 주세요. 하루 동안 이동하는 일정은 동선이 꼬이지 않도록 구성해 주세요. 하루 동안 다양한 여행지를 들릴 수 있도록 계획해 주세요. 하루의 모든 일정은 최대 8시간 안에 구경하거나 체험할 수 있어야 합니다.
              방문지와 코스의 설명은 제외하고 여행지들의 이름만 제공해 주세요. 방문지들은 한글로 적어주세요. notes에 내용이 적혔다고해서 추가적인 설명을 하지 마세요. 배열 형태의 예시처럼만 출력해주세요.
              문장들은 다 붙여주세요. 띄어쓰기나 줄바꿈 할 필요 없이 한 문장으로 만들어주시면 됩니다.
               
               
               """,
            request.getDate(),
            request.getLocation(),
            request.getBudget(),
            request.getPurpose(),
            request.getNotes());
    }

}
