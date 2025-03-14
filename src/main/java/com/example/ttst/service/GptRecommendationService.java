package com.example.ttst.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import java.util.List;
import java.util.Map;

@Service
public class GptRecommendationService {

    private final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";
    private final String API_KEY = "YOUR_OPENAI_API_KEY"; //  여기에 API 키 입력

    public String getGptRecommendation(String query) {
        String prompt = "다음 요구사항에 맞는 제품을 추천해줘: " + query;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(API_KEY);

        JSONObject requestBody = new JSONObject();
        requestBody.put("model", "gpt-4");
        requestBody.put("messages", List.of(Map.of("role", "user", "content", prompt)));

        HttpEntity<String> request = new HttpEntity<>(requestBody.toString(), headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(OPENAI_API_URL, request, String.class);

        return response.getBody();
    }
}
