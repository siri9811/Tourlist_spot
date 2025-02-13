package com.example.Tourlist_spot.controller;

import com.example.Tourlist_spot.service.TourlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/Tourlist")
public class TourlistController {
    private final TourlistService tourlistService;


}
