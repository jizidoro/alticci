package com.alticci.app.core.alticci.usecases;

import com.alticci.app.application.responses.SingleResultDto;
import com.alticci.app.application.services.alticci.dtos.AlticciDto;
import com.alticci.app.core.bases.UseCase;
import com.github.benmanes.caffeine.cache.Cache;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.caffeine.CaffeineCache;

import java.math.BigInteger;


@Getter
@Setter
public class UcAlticciCalculateValue extends UseCase<Integer> {

    private Long initialValue;
    private Integer calculatedValue;
    private final CacheManager _cacheManager;

    public UcAlticciCalculateValue(Long value, CacheManager cacheManager) {
        super();
        initialValue = value;
        calculatedValue = 0;
        _cacheManager = cacheManager;
    }

    @Override
    protected Integer execute() {
        return alticciCalculator(initialValue);
    }

    public Integer alticciCalculator(Long actualValue) {
        if (actualValue == 0) {
            return 0;
        } else if (actualValue == 1) {
            return 1;
        } else if (actualValue == 2) {
            return 1;
        } else {
            CaffeineCache caffeineCache = (CaffeineCache) _cacheManager.getCache("alticci");
            Cache<Object, Object> nativeCache = caffeineCache.getNativeCache();
            var oto = (SingleResultDto<AlticciDto>) nativeCache.asMap().get(actualValue);
            if (oto != null) {
                return Integer.parseInt(oto.getData().getCalculatedValue());
            } else {
                calculatedValue = alticciCalculator(actualValue - 3) + alticciCalculator(actualValue - 2);
                return calculatedValue;
            }
        }
    }

}
