package com.example.Tourlist_spot.service;

import com.example.Tourlist_spot.dto.MarkerDto;
import com.example.Tourlist_spot.entity.Marker;
import com.example.Tourlist_spot.repository.MarkerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MarkerService {

    private final MarkerRepository markerRepository;

    //마커 저장
    @Transactional
    public MarkerDto saveMarker(MarkerDto dto) {
        Marker marker = dto.toEntity();
        if (marker.getId() != null) {
            return null;
        }
        Marker savedMarker = markerRepository.save(marker);
        return MarkerDto.from(savedMarker);
        // 다른 엔티티에서도 DB를 추가하고 있다면?
    }

    // 스프링이 2개가 켜져있고, DB는 하나만 쓸때
    // JPA 캐시 DB를 복제해서 스프링 JPA에서 쓰는중
    // 스프링 -- DB (같은 데이터를 가지고 있지만 시기가 다르다)
    // 스프링1 -- DB -- 스프링2 <-

    //마커 정보 조회

    /**
     * 마커 정보 조회
     *
     * @param markerId 마커 ID
     * @return 마커 Data
     */
    @Transactional(readOnly = true)
    public MarkerDto getMarkerInfo(Long markerId) {
        // Marker 조회
        // Null Point Exception NPE
        Optional<Marker> marker = markerRepository.findById(markerId);

        if (marker.isEmpty()) {
            throw new RuntimeException();
        }

        return MarkerDto.from(marker.get());
    }


    //모든 마커 조회
    @Transactional(readOnly = true)
    public List<MarkerDto> getAllMarkers() {
//        List<Marker> markers = markerRepository.findAll();
//        log.debug("Markers from DB: {}", markers);

        // 컴구에 메모리 영역 스탭 힙 영역 차이 (속도의차이)
        List<MarkerDto> markerDtos = markerRepository.findAll().stream()
                .map(MarkerDto::from)
                .collect(Collectors.toList());

//        List<MarkerDto> markerDtos = new ArrayList<MarkerDto>();
//        for (Marker marker : markerRepository.findAll()) {
//            markerDtos.add(MarkerDto.from(marker));
//        }
        log.debug("Converted MarkerDtos: {}", markerDtos);
        return markerDtos;
    }

    //마커 수정
    @Transactional
    public MarkerDto update(Long id, MarkerDto dto) {
        //마커 조회 및 예외 발생
        Marker target = markerRepository.findById(id).orElseThrow(null);
        //마커 수정
        target.patch(dto);

        //마커 엔티티 DTO 변환 및 반환
        return MarkerDto.from(target);
    }

    @Transactional
    public boolean delete(Long id) {
        // 마커 조회 여부 확인
        if (!markerRepository.existsById(id)) { //클래스 생성해서 예외처리
            throw new IllegalArgumentException("Marker not found");
        }

        //마커 삭제
        markerRepository.deleteById(id);
        return true;
    }
}
