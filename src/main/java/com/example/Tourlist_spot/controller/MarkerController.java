package com.example.Tourlist_spot.controller;

import com.example.Tourlist_spot.dto.InfoDto;
import com.example.Tourlist_spot.dto.MarkerDto;
import com.example.Tourlist_spot.entity.Marker;
import com.example.Tourlist_spot.service.MarkerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/markers")
@RequiredArgsConstructor
@Slf4j
public class MarkerController {


    private final MarkerService markerService;

    //모든 마커 로드
    @GetMapping
    public List<MarkerDto> getAllMarkers() {
        return markerService.getAllMarkers();
    }

    //마커 정보 조회
    @GetMapping("/{markerId}")
    public ResponseEntity<MarkerDto> getMarker(@PathVariable("markerId") Long markerId) {
        try {
            MarkerDto markerDto = markerService.getMarkerInfo(markerId);
            return ResponseEntity.ok(markerDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //마커 생성
    @PostMapping("/save")
    public ResponseEntity<MarkerDto> saveMarker(@RequestBody MarkerDto dto) {

        Marker saved = markerService.saveMarker(dto);

        if (saved == null) {
            log.error("Failed to create Marker: ID already exists");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        // Marker → MarkerDto 변환 후 반환
        MarkerDto responseDto = new MarkerDto(saved.getId(), saved.getLatitude(), saved.getLongitude(), saved.getName(), saved.getDescription());
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);

    }

    //마커 수정
    @PatchMapping("/update/{markerId}")
    public ResponseEntity<MarkerDto> updateMarker(@PathVariable("markerId") Long markerId, @RequestBody MarkerDto dto) {

        //서비스에게 위임
        MarkerDto updateDto = markerService.update(markerId,dto);

        //결과응답
        return ResponseEntity.status(HttpStatus.OK).body(updateDto);
    }

    //마커 삭제
    @DeleteMapping("/delete/{markerId}")
    public ResponseEntity<MarkerDto> deleteMarker(@PathVariable("markerId") Long markerId) {

        //서비스에게 위임
        MarkerDto deleteDto = markerService.delete(markerId);

        //결과응답
        return ResponseEntity.status(HttpStatus.OK).body(deleteDto);
    }






}
