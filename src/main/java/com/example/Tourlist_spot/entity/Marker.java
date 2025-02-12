package com.example.Tourlist_spot.entity;

import com.example.Tourlist_spot.dto.MarkerDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String firstimage;
    @Column
    private String tel;
    @Column
    private String addr1;
    @Column
    private String addr2;

    public void patch(MarkerDto dto) {
        //예외 발생
        if(this.id != dto.getId()) {
            throw new IllegalArgumentException("ID가 다릅니다");
        }
        //객체 갱신
        if(this.getName() != null) {
            this.name = dto.getName();
        }
        if(this.getDescription() != null) {
            this.description = dto.getDescription();
        }

    }
}
