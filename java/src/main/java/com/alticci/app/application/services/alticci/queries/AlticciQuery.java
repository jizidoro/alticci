package com.alticci.app.application.services.alticci.queries;

import com.alticci.app.application.responses.SingleResultDto;
import com.alticci.app.application.services.alticci.IAlticciQuery;
import com.alticci.app.application.services.alticci.dtos.AlticciDto;
import com.alticci.app.core.alticci.usecases.UcAlticciCalculateValue;
import com.alticci.app.core.bases.UseCaseFacade;
import com.github.benmanes.caffeine.cache.Cache;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.ConcurrentMap;

@Service
@RequiredArgsConstructor
public class AlticciQuery implements IAlticciQuery {

    private final UseCaseFacade facade;


    private final CacheManager cacheManager;

    @Cacheable(cacheNames = "alticci")
    public SingleResultDto<AlticciDto> findById(Long value) {
        var uc = new UcAlticciCalculateValue(value, cacheManager);
        var ucResult = facade.execute(uc);
        var response = new AlticciDto();
        response.setCalculatedValue(ucResult.toString());

        return new SingleResultDto<>(response);
    }


    public ConcurrentMap<Object, Object> keys(String cacheName) {
        CaffeineCache caffeineCache = (CaffeineCache) cacheManager.getCache("alticci");
        Cache<Object, Object> nativeCache = caffeineCache.getNativeCache();
        // var oto = (SingleResultDto<AlticciDto>) nativeCache.asMap().get(12l);
        return nativeCache.asMap();
    }
}
