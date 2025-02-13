package com.example.Tourlist_spot.service;
import com.example.Tourlist_spot.dto.MarkerDto;
import com.example.Tourlist_spot.entity.Marker;
import com.example.Tourlist_spot.repository.MarkerRepository;
import com.example.Tourlist_spot.service.data.Item;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class TourlistService {


    private final ObjectMapper objectMapper;
    @Value("${TOURLIST_API_KEY}")  // 환경 변수로부터 API Key를 주입받음
    private String apiKey;

    @PostConstruct
    public void printApiKey() {
        System.out.println("API Key: " + apiKey);  // 주입된 API 키 출력
    }

    private final RestTemplate restTemplate;
    private final MarkerRepository markerRepository;


    // 관광지 데이터를 DB에 저장하는 메서드
    @PostConstruct
    public void fetchAndSaveTourData() {

        log.info("Fetching TourData...");
        RestClient restClient = RestClient.builder()
                .baseUrl("https://apis.data.go.kr")
                .build();

        ResponseEntity<String> response = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/B551011/KorPetTourService/areaBasedList")
                        .queryParam("serviceKey", apiKey)
                        .queryParam("MobileOS", "WIN")
                        .queryParam("MobileApp", "MobileApp")
                        .queryParam("contentTypeId", "12")
                        .queryParam("_type", "json")
                        .build())
                .retrieve()
                .toEntity(String.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            log.info("API 호출 성공: 응답 데이터 처리 중...");

            List<MarkerDto> tourlistDtos = parseTourlistResponse(response.getBody());

            List<Marker> markers = tourlistDtos.stream()
                    .map(Marker::fromDto)
                    .collect(Collectors.toList());

            markerRepository.saveAll(markers);
            log.info("데이터 저장 완료.");
        } else {
            log.error("API 호출 실패 또는 응답 본문이 비어 있습니다. 상태 코드: {}", response.getStatusCode());
        }
    }

    private List<MarkerDto> parseTourlistResponse(String jsonResponse) {
        List<MarkerDto> markerList = new ArrayList<>();
        try {
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            JsonNode items = rootNode.path("response").path("body").path("items").path("item");

            if (items.isArray()) {
                for (JsonNode item : items) {
                    // Item 객체로 변환
                    Item itemObject = objectMapper.treeToValue(item, Item.class);

                    // Item 객체를 MarkerDto로 변환
                    MarkerDto markerDto = convertToMarkerDto(itemObject);

                    markerList.add(markerDto);
                    log.info("Parsed MarkerDto: {}", markerDto);
                }
            }
        } catch (Exception e) {
            log.error("JSON 파싱 중 오류 발생", e);
        }
        return markerList;
    }

    // Item을 MarkerDto로 변환하는 메서드
    private MarkerDto convertToMarkerDto(Item item) {
        MarkerDto markerDto = new MarkerDto();

        // 주소 매핑 (addr1 + addr2)
        markerDto.setAddress(item.addr1 + " " + item.addr2);

        // 좌표 매핑 (mapx -> latitude, mapy -> longitude)
        markerDto.setLatitude(Double.parseDouble(item.mapy));
        markerDto.setLongitude(Double.parseDouble(item.mapx));

        // 이름 매핑 (title)
        markerDto.setName(item.title);

        // 전화번호 매핑 (tel)
        markerDto.setTel(item.tel);

        // 추가적으로 필요한 필드 매핑
        // 예: 이미지 (firstimage)
        markerDto.setImageUrl(item.firstimage);

        // 설명 또는 기타 정보 매핑
        markerDto.setDescription("설명: " + item.contentid); // 예시로 contentid를 설명에 넣음

        return markerDto;
    }

}


//
//            String encodedApiKey = URLEncoder.encode(apiKey, StandardCharsets.UTF_8.toString());
//
//            String url = "https://apis.data.go.kr/B551011/KorPetTourService/areaBasedList" +
//                    "?serviceKey=" + apiKey +
//                    "&MobileOS=WIN&MobileApp=MobileApp&contentTypeId=12&_type=json";
//
//            System.out.println("Requesting URL: " + url);
//
//            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
//            System.out.println("Response Status Code: " + response.getStatusCode());
//            System.out.println("Response Body: " + response.getBody());
//
//            if (response.getBody() != null) {
//                List<TourlistDto> tourlistDtos = parseTourlistResponse(response.getBody());
//
//                // Marker 엔티티로 변환 후 저장
//                for (TourlistDto dto : tourlistDtos) {
//                    Marker marker = new Marker();
//                    marker.setLatitude(dto.getMapy());
//                    marker.setLongitude(dto.getMapx());
//                    marker.setName(dto.getTitle());
//                    marker.setDescription(dto.getAddr1() + " " + dto.getAddr2());
//                    marker.setFirstimage(dto.getFirstimage());
//                    marker.setTel(dto.getTel());
//                    marker.setAddr1(dto.getAddr1());
//                    marker.setAddr2(dto.getAddr2());
//
//                    markerRepository.save(marker);
//                }
//            } else {
//                System.err.println("응답 본문이 비어 있습니다.");
//            }



    // Jackson

    // JSON 응답을 TourlistDto 리스트로 변환하는 메서드
//    private List<TourlistDto> parseTourlistResponse(String jsonResponse) {
//        List<TourlistDto> tourlist = new ArrayList<>();
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            JsonNode rootNode = objectMapper.readTree(jsonResponse);
//            JsonNode items = rootNode.path("response").path("body").path("items").path("item");
//
//            if (items.isArray()) {
//                for (JsonNode item : items) {
//                    TourlistDto dto = new TourlistDto();
//                    dto.setMapx(item.path("mapx").asDouble());
//                    dto.setMapy(item.path("mapy").asDouble());
//                    dto.setTitle(item.path("title").asText());
//                    dto.setAddr1(item.path("addr1").asText());
//                    dto.setAddr2(item.path("addr2").asText());
//                    dto.setFirstimage(item.path("firstimage").asText());
//                    dto.setTel(item.path("tel").asText());
//
//                    tourlist.add(dto);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return tourlist;
//    }




