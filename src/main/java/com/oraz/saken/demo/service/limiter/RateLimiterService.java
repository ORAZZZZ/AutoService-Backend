package com.oraz.saken.demo.service.limiter;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimiterService {
    private final ConcurrentHashMap<String, Bucket> ipBuckets = new ConcurrentHashMap<>();
    private final int MAX_REQUESTS = 5;
    private final Duration TIME_PERIOD = Duration.ofMinutes(1);

    public boolean isAllowed(String clientId) {
        Bucket bucket = ipBuckets.computeIfAbsent(clientId, this::createNewBucket);

        return bucket.tryConsume(1);
    }

    private Bucket createNewBucket(String clientId) {
        Bandwidth limit = Bandwidth.simple(MAX_REQUESTS, TIME_PERIOD);

        return Bucket4j.builder()
                .addLimit(limit)
                .build();
    }
}
