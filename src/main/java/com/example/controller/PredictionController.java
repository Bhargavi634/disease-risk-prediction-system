package com.example.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;

@Controller
public class PredictionController {
    private final RestTemplate rt = new RestTemplate();
    @Value("${ml.service.url}")
    private String mlUrl;

    @PostMapping("/predict/diabetes")
    public String predictDiabetes(@RequestParam int age, @RequestParam double bmi,
                                  @RequestParam double glucose, @RequestParam String gender,
                                  HttpSession session, Model m) {
        if(session.getAttribute("user")==null) return "redirect:/login";
        String url = mlUrl + "/predict/diabetes";
        Map<String,Object> payload = new HashMap<>();
        payload.put("age", age);
        payload.put("bmi", bmi);
        payload.put("glucose", glucose);
        payload.put("gender", gender);
        ResponseEntity<Map> resp = rt.postForEntity(url, payload, Map.class);
        m.addAttribute("result", resp.getBody());
        return "result";
    }

    @PostMapping("/predict/heart")
    public String predictHeart(@RequestParam int age, @RequestParam double bmi,
                               @RequestParam double cholesterol, @RequestParam String gender,
                               HttpSession session, Model m) {
        if(session.getAttribute("user")==null) return "redirect:/login";
        String url = mlUrl + "/predict/heart";
        Map<String,Object> payload = new HashMap<>();
        payload.put("age", age);
        payload.put("bmi", bmi);
        payload.put("cholesterol", cholesterol);
        payload.put("gender", gender);
        ResponseEntity<Map> resp = rt.postForEntity(url, payload, Map.class);
        m.addAttribute("result", resp.getBody());
        return "result";
    }
}
