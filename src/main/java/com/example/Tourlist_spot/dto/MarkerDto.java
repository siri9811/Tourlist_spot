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

    public Marker toEntity() {
        return new Marker (null, latitude, longitude);
    }
}
