package com.example.Tourlist_spot.controller;
import com.example.Tourlist_spot.dto.MarkerDto;
import com.example.Tourlist_spot.service.MarkerService;
import lombok.NonNull;
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
        return ResponseEntity.ok().body(markerService.getMarkerInfo(markerId));
    }

    // 1. 404 낫 빠운드
    // 2. 500 서버 에러
    // 3. 403 넌 접근 권한이 없다!
    // 4. 401 넌 인증되지 않았어

////    마커 생성
    @PostMapping("/save")
    public ResponseEntity<MarkerDto> saveMarker(
            @NonNull @RequestBody MarkerDto dto
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                markerService.saveMarker(dto)
        );
    }

    //마커 수정
    @PatchMapping("/update/{markerId}")
    public ResponseEntity<MarkerDto> updateMarker(
            @PathVariable("markerId") Long markerId,
            @RequestBody MarkerDto dto
    ) {
        //서비스에게 위임
        MarkerDto updateDto = markerService.update(markerId, dto);

        //결과응답
        return ResponseEntity.status(HttpStatus.OK).body(updateDto);
    }

    //마커 삭제
    @DeleteMapping("/delete/{markerId}")
    public ResponseEntity<Boolean> deleteMarker(
            @PathVariable("markerId") Long markerId
    ) {
        //결과응답
        return ResponseEntity.status(HttpStatus.OK).body(
                markerService.delete(markerId)
        );
    }

}

//@RestControllerAdvice
//class GlobalExceptionHandler {
//
//    @ExceptionHandler(RuntimeException.class)
//    public ResponseEntity<APIResponse> handleRuntimeException(RuntimeException e) {
//
//    }
//}

