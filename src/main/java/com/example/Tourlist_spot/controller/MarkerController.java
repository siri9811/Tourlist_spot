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

    @GetMapping
    public List<MarkerDto> getAllMarkers() {
        return markerService.getAllMarkers();
    }

    @GetMapping("/{markerId}")
    public ResponseEntity<InfoDto> getMarker(@PathVariable("markerId") Long markerId) {
        try {
            InfoDto infoDto = markerService.getMarkerInfo(markerId);
            return ResponseEntity.ok(infoDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/save")
    public ResponseEntity<MarkerDto> saveMarker(@RequestBody MarkerDto dto) {

        Marker saved = markerService.saveMarker(dto);

        if (saved == null) {
            log.error("Failed to create Marker: ID already exists");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        // Marker → MarkerDto 변환 후 반환
        MarkerDto responseDto = new MarkerDto(saved.getId(), saved.getLatitude(), saved.getLongitude());
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);

    }




}
