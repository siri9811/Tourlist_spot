package com.example.Tourlist_spot.service;

import com.example.Tourlist_spot.dto.MarkerDto;
import com.example.Tourlist_spot.entity.Marker;
import com.example.Tourlist_spot.repository.MarkerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MarkerService {

    private final MarkerRepository markerRepository;

    //마커 저장
    @Transactional
    public Marker saveMarker(MarkerDto dto) {
        Marker marker = dto.toEntity();
        if (marker.getId() != null) {
            return null;
        }
        return markerRepository.save(marker);
    }


    //마커 정보 조회
    public MarkerDto getMarkerInfo(Long markerId) {
        // Marker 조회
        Marker marker = markerRepository.findById(markerId)
                .orElseThrow(() -> new RuntimeException("Marker not found"));

        return new MarkerDto(marker.getId(), marker.getLatitude(), marker.getLongitude(), marker.getName(), marker.getDescription());
    }


    //모든 마커 조회
    public List<MarkerDto> getAllMarkers() {
        List<Marker> markers = markerRepository.findAll();
        log.debug("Markers from DB: {}", markers);

        List<MarkerDto> markerDtos = markers.stream()
                .map(marker -> new MarkerDto(
                        marker.getId(),
                        marker.getLatitude(),
                        marker.getLongitude(),
                        marker.getName(),
                        marker.getDescription()
                ))
                .collect(Collectors.toList());

        log.debug("Converted MarkerDtos: {}", markerDtos);

        return markerDtos;
    }

    //마커 수정
    public MarkerDto update(Long id, MarkerDto dto) {

        //마커 조회 및 예외 발생
        Marker target = markerRepository.findById(id).orElseThrow(null);

        //마커 수정
        target.patch(dto);

        //마커 DB로 갱신
        Marker updated = markerRepository.save(target);

        //마커 엔티티 DTO 변환 및 반환
        return MarkerDto.createMarkerDto(updated);
    }

    public MarkerDto delete(Long id) {
        //마커 조회
        Marker target = markerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Marker not found"));

        //마커 삭제
        markerRepository.delete(target);

        return MarkerDto.createMarkerDto(target);
    }
}
