package com.example.Tourlist_spot.controller;

import com.example.Tourlist_spot.dto.MarkerDto;
import com.example.Tourlist_spot.dto.TourlistDto;
import com.example.Tourlist_spot.service.TourlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/Tourlist")
public class TourlistController {
    private final TourlistService tourlistService;


}
