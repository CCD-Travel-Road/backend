package YeoroProject.Yeoro.gpt;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OpenAiClient {

    private static final Logger logger = LoggerFactory.getLogger(OpenAiClient.class);

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.api.url}")
    private String apiUrl;

    private final OkHttpClient client = new OkHttpClient();

    public String getTravelPlan(String prompt) {
        // Using org.json to format JSON body
        JSONObject json = new JSONObject();
        json.put("model", "gpt-4o");
        json.put("messages", new JSONArray().put(new JSONObject().put("role", "user").put("content", prompt)));

        RequestBody body = RequestBody.create(json.toString(), MediaType.parse("application/json; charset=utf-8"));

        Request request = new Request.Builder()
            .url(apiUrl)
            .addHeader("Authorization", "Bearer " + apiKey)
            .post(body)
            .build();

        logger.info("Sending request to OpenAI API: {}", request);
        logger.info("Request body: {}", json.toString());

        try (Response response = client.newCall(request).execute()) {
            String responseBodyString = response.body() != null ? response.body().string() : "null";
            if (!response.isSuccessful()) {
                logger.error("Unexpected response code: {}. Response body: {}", response, responseBodyString);
                throw new RuntimeException("Unexpected code " + response + ". Response body: " + responseBodyString);
            }

            logger.info("Response body: {}", responseBodyString);
            return responseBodyString;
        } catch (Exception e) {
            logger.error("Error generating travel plan: {}", e.getMessage(), e);
            return "여행 계획 생성 중 오류가 발생했습니다.";
        }
    }
}
