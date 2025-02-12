package com.example.Tourlist_spot.service;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import com.example.Tourlist_spot.dto.TourlistDto;
import com.example.Tourlist_spot.entity.Marker;
import com.example.Tourlist_spot.repository.MarkerRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class TourlistService {




    @Value("${TOURLIST_API_KEY}")  // 환경 변수로부터 API Key를 주입받음
    private String apiKey;

    @PostConstruct
    public void printApiKey() {
        System.out.println("API Key: " + apiKey);  // 주입된 API 키 출력
    }
    private final RestTemplate restTemplate;
    private final MarkerRepository markerRepository;


    // 주기적으로 외부 API에서 데이터를 가져와 DB에 저장
    // 관광지 데이터를 DB에 저장하는 메서드
    @PostConstruct
    public void fetchAndSaveTourData() {
        try {
            String encodedApiKey = URLEncoder.encode(apiKey, StandardCharsets.UTF_8.toString());

            String url = "https://apis.data.go.kr/B551011/KorPetTourService/areaBasedList" +
                    "?serviceKey=" + apiKey +
                    "&MobileOS=WIN&MobileApp=MobileApp&contentTypeId=12&_type=json";

            System.out.println("Requesting URL: " + url);

            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            System.out.println("Response Status Code: " + response.getStatusCode());
            System.out.println("Response Body: " + response.getBody());

            if (response.getBody() != null) {
                List<TourlistDto> tourlistDtos = parseTourlistResponse(response.getBody());

                // Marker 엔티티로 변환 후 저장
                for (TourlistDto dto : tourlistDtos) {
                    Marker marker = new Marker();
                    marker.setLatitude(dto.getMapy());
                    marker.setLongitude(dto.getMapx());
                    marker.setName(dto.getTitle());
                    marker.setDescription(dto.getAddr1() + " " + dto.getAddr2());
                    marker.setFirstimage(dto.getFirstimage());
                    marker.setTel(dto.getTel());
                    marker.setAddr1(dto.getAddr1());
                    marker.setAddr2(dto.getAddr2());

                    markerRepository.save(marker);
                }
            } else {
                System.err.println("응답 본문이 비어 있습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // JSON 응답을 TourlistDto 리스트로 변환하는 메서드
    private List<TourlistDto> parseTourlistResponse(String jsonResponse) {
        List<TourlistDto> tourlist = new ArrayList<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            JsonNode items = rootNode.path("response").path("body").path("items").path("item");

            if (items.isArray()) {
                for (JsonNode item : items) {
                    TourlistDto dto = new TourlistDto();
                    dto.setMapx(item.path("mapx").asDouble());
                    dto.setMapy(item.path("mapy").asDouble());
                    dto.setTitle(item.path("title").asText());
                    dto.setAddr1(item.path("addr1").asText());
                    dto.setAddr2(item.path("addr2").asText());
                    dto.setFirstimage(item.path("firstimage").asText());
                    dto.setTel(item.path("tel").asText());

                    tourlist.add(dto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tourlist;
    }

}


