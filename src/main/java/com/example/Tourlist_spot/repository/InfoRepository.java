package com.example.Tourlist_spot.repository;

import com.example.Tourlist_spot.entity.Info;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InfoRepository extends JpaRepository<Info,Long> {
    Optional<Info> findByMarker_Id(Long markerId);}
