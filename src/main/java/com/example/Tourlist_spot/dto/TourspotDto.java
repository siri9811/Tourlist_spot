package com.example.Tourlist_spot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TourspotDto {

    private double latitude;
    private double longitude;
    private String name;
}
