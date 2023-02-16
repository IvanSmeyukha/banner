package com.test_task.controller;

import com.test_task.entity.Banner;
import com.test_task.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/banners")
public class BannerController {

    private final BannerService bannerService;

    @Autowired
    public BannerController(BannerService bannerService) {
        this.bannerService = bannerService;
    }

    @PostMapping
    public ResponseEntity<String> addBanner(@RequestBody Banner banner) {
        banner.setId(null);
        Optional<String> error = bannerService.saveBanner(banner);
        return error
                .map(s -> new ResponseEntity<>(s, HttpStatus.CONFLICT))
                .orElseGet(() -> ResponseEntity.ok("OK"));
    }

    @PutMapping
    public ResponseEntity<String> updateBanner(@RequestBody Banner banner) {
        Optional<String> error = bannerService.saveBanner(banner);
        return  error
                .map(s -> new ResponseEntity<>(s, HttpStatus.CONFLICT))
                .orElseGet(() -> ResponseEntity.ok("OK"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBanner(@PathVariable int id) {
        Optional<String> error = bannerService.deleteBanner(id);
        return  error
                .map(s -> new ResponseEntity<>(s, HttpStatus.CONFLICT))
                .orElseGet(() -> ResponseEntity.ok("OK"));
    }

    @GetMapping
    public ResponseEntity<List<Banner>> getBannersList(@RequestParam(name = "name", defaultValue = "") String name) {
        return new ResponseEntity<>(bannerService.getBannersList(name), HttpStatus.OK);
    }

}
