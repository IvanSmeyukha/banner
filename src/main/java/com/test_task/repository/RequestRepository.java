package com.test_task.repository;

import com.test_task.entity.Banner;
import com.test_task.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {
    boolean existsByBannerAndUserAgentAndIpAddressAndDateAfter(Banner banner, String userAgent, String ipAddress, Date date);
}
