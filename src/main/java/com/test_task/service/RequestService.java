package com.test_task.service;

import com.test_task.entity.Banner;
import com.test_task.entity.Request;
import com.test_task.repository.BannerRepository;
import com.test_task.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RequestService {
    private final RequestRepository requestRepository;
    private final BannerRepository bannerRepository;

    @Autowired
    public RequestService(RequestRepository requestRepository, BannerRepository bannerRepository) {
        this.requestRepository = requestRepository;
        this.bannerRepository = bannerRepository;
    }

    public Optional<String> getBannerByCategory(String category, String ipAddress, String userAgent) {
        Date date = new Date();
        List<Banner> list = bannerRepository.findByCategory_ReqNameAndDeletedIsFalseOrderByPriceDesc(category);
        for (Banner currentBanner : list) {
            if (!requestRepository.existsByBannerAndUserAgentAndIpAddressAndDateAfter(
                    currentBanner,
                    userAgent,
                    ipAddress,
                    new Date(date.getTime() - ChronoUnit.MILLIS.between(LocalTime.MIDNIGHT, LocalTime.MAX))
            )) {
                addNewRequest(currentBanner, ipAddress, userAgent, date);
                return Optional.of(currentBanner.getContent());
            }
        }
        return Optional.empty();
    }

    private void addNewRequest(Banner banner, String ipAddress, String userAgent, Date date){
        Request request = new Request();
        request.setId(null);
        request.setBanner(banner);
        request.setUserAgent(userAgent);
        request.setIpAddress(ipAddress);
        request.setDate(date);
        requestRepository.save(request);
    }
}
