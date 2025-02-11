package com.example.Tourlist_spot.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InfoDto {
    private Long id;
    private double latitude;
    private double longitude;
    private String infoName;
    private String infoDescription;


    public InfoDto(Long id, String name, String description, double latitude, double longitude) {
    }
}
