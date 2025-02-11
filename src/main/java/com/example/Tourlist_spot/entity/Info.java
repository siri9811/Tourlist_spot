package com.example.Tourlist_spot.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Info {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @OneToOne
    @JoinColumn(name = "marker_id")  // 외래키 설정
    private Marker marker;  // Marker와의 관계 설정


}
