package com.example.Tourlist_spot.entity;

import com.example.Tourlist_spot.dto.MarkerDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor

@AllArgsConstructor
public class Marker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private double latitude;
    @Column
    private double longitude;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private String image;
    @Column
    private String tel;
    @Column
    private String address;


    public void patch(MarkerDto dto) {
        // 예외 발생: ID가 다르면 수정 불가
        if (this.id != dto.getId()) {
            throw new IllegalArgumentException("ID가 다릅니다");
        }

        // 객체 갱신: null도 허용하여 값을 갱신
        this.name = dto.getName() != null ? dto.getName() : this.name;
        this.description = dto.getDescription() != null ? dto.getDescription() : this.description;
        this.tel = dto.getTel() != null ? dto.getTel() : this.tel; // 전화번호 처리 추가
        this.address = dto.getAddress() != null ? dto.getAddress() : this.address;
        this.image = dto.getImageUrl() != null ? dto.getImageUrl() : this.image;
    }


    public static Marker fromDto(MarkerDto dto) {
        return new Marker(
                null,
                dto.getLatitude(),
                dto.getLongitude(),
                dto.getName(),
                null,
                dto.getImageUrl(),
                dto.getTel(),
                dto.getAddress()
        );
    }
}
