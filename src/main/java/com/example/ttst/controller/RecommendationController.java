package com.example.ttst.controller;

import com.example.ttst.entity.Product;
import com.example.ttst.service.GptRecommendationService;
import com.example.ttst.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/recommendation")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService){
        this.recommendationService = recommendationService;
    }


    @Autowired
    private GptRecommendationService gptRecommendationService;

    @GetMapping("/ml/{userId}")
    public List<Product> getRecommendations() {
        return recommendationService.getRecommendations();
    }
    @GetMapping("/ai")
    public ResponseEntity<String> getGptRecommendations(@RequestParam String query) {
        String recommendation = gptRecommendationService.getGptRecommendation(query);
        return ResponseEntity.ok(recommendation);
    }
}
