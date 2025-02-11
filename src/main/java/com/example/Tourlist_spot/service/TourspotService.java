package com.example.Tourlist_spot.service;

import com.example.Tourlist_spot.dto.TourspotDto;
import com.example.Tourlist_spot.entity.Tourspot;
import com.example.Tourlist_spot.repository.TourspotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TourspotService {

    private final RestTemplate restTemplate;
    private final TourspotRepository tourspotRepository;

    public void SaveTourspot() {
        List<TourspotDto> tourspotDtos = fetchTourspotData();

        //DB에 저장
        for (TourspotDto Dto : tourspotDtos) {
            Tourspot tourspot = new Tourspot();
            tourspot.setLatitude(Dto.getLatitude());
            tourspot.setLongitude(Dto.getLongitude());
        }
    }

    public List<TourspotDto> fetchTourspotData() {
        String TourspotUrl = "https://apis.data.go.kr/6300000/openapi2022/tourspot/gettourspot?serviceKey=" +
                "vG2QqAV7m0IVPuTaxWDlhICOjQlodxziHnwM55R9U6rA6C%252FFkS5wDCLCqHoBl%252BD%252FfWaIjNakSHiJ76fuLi8kCw%253D%253D&" +
                "pageNo=1&numOfRows=12";

        ResponseEntity<List<TourspotDto>> response = restTemplate.exchange(TourspotUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<TourspotDto>>() {});
        return response.getBody();
    }


}
