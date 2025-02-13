package com.example.Tourlist_spot.controller;
import com.example.Tourlist_spot.service.TourlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequiredArgsConstructor
public class MapController {

    private final TourlistService tourlistService; // 불변 <- 한번 세팅되면 변하지 않는

    @GetMapping({"/", ""})
    public String maker(Model model) {
        return "main";
    }

    @Value("${kakao.Api.Key}") // 이거는 나중에 한번더 알려드림
    private String kakaoApiKey;


    @GetMapping("/map")
    public String getMapPage(Model model) {
        model.addAttribute("kakaoApiKey", kakaoApiKey);
        return "/map/map"; // Thymeleaf 또는 JSP 템플릿 이름
    }
}
