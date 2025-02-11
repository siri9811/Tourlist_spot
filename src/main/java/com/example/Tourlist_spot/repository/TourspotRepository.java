package com.example.Tourlist_spot.repository;

import com.example.Tourlist_spot.entity.Tourspot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TourspotRepository extends JpaRepository<Tourspot, Long> {
    List<Tourspot> findByLatitudeAndLongitude(Double lat, Double lot);

}
