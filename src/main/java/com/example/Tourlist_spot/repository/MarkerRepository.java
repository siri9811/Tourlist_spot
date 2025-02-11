package com.example.Tourlist_spot.repository;

import com.example.Tourlist_spot.entity.Marker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MarkerRepository extends JpaRepository<Marker, Long> {
    //모든 마커 조회
    List<Marker> findAll();



}
