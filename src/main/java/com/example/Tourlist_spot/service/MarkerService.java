package com.example.Tourlist_spot.service;

import com.example.Tourlist_spot.dto.InfoDto;
import com.example.Tourlist_spot.dto.MarkerDto;
import com.example.Tourlist_spot.entity.Info;
import com.example.Tourlist_spot.entity.Marker;
import com.example.Tourlist_spot.repository.InfoRepository;
import com.example.Tourlist_spot.repository.MarkerRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MarkerService {

    private final MarkerRepository markerRepository;
    private final InfoRepository infoRepository;


    @Transactional
    public Marker saveMarker(MarkerDto dto) {
        Marker marker = dto.toEntity();
        if(marker.getId() != null) {
            return null;
        }
        return markerRepository.save(marker);
    }

    public InfoDto getMarkerInfo(Long markerId) {
        // Marker 조회
        Marker marker = markerRepository.findById(markerId)
                .orElseThrow(() -> new RuntimeException("Marker not found"));

        // Marker id와 동일한 id를 가진 Info 조회
        Info info = infoRepository.findByMarker_Id(markerId)
                .orElseThrow(() -> new RuntimeException("Info not found for marker"));

        // InfoDto 생성하여 반환 (마커 정보도 포함)
        return new InfoDto(info.getId(), info.getName(), info.getDescription(), marker.getLatitude(), marker.getLongitude());
    }



    public List<MarkerDto> getAllMarkers() {
        List<Marker> markers = markerRepository.findAll();
        log.debug("Markers from DB: {}", markers);

        List<MarkerDto> markerDtos = markers.stream()
                .map(marker -> new MarkerDto(marker.getId(), marker.getLatitude(),marker.getLongitude()))
                .collect(Collectors.toList());

        log.debug("Markers from DB: {}", markerDtos);

        return markerDtos;
    }
}
