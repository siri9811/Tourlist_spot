package com.example.Tourlist_spot.dto;

import com.example.Tourlist_spot.entity.Marker;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MarkerDto {

    private Long id;
    private double latitude;
    private double longitude;
    private String name;
    private String description;

    public static MarkerDto createMarkerDto(Marker marker) {
        return new MarkerDto(marker.getId(),
                marker.getLatitude(),
                marker.getLongitude(),
                marker.getName(),
                marker.getDescription());
    }


    public Marker toEntity() {
        // 새로 추가된 필드는 null로 초기화
        Marker marker = new Marker();
        marker.setId(id);
        marker.setLatitude(latitude);
        marker.setLongitude(longitude);
        marker.setName(name);
        marker.setDescription(description);
        // 새로 추가된 필드 (예: address, firstImageUrl, phoneNumber)는 null로 설정
        marker.setAddr1(null);
        marker.setAddr2(null);
        marker.setFirstimage(null);
        marker.setTel(null);
        return marker;
    }

}
