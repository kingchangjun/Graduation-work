package com.example.ttst.controller;

import com.example.ttst.service.GptRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gpt-recommend")
public class GptRecommendationController {

    @Autowired
    private GptRecommendationService gptRecommendationService;

    @GetMapping
    public ResponseEntity<String> getGptRecommendation(@RequestParam String query) {
        String recommendation = gptRecommendationService.getGptRecommendation(query);
        return ResponseEntity.ok(recommendation);
    }
}
