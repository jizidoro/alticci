package com.comrades.app.core.alticci.usecases;

import com.comrades.app.application.responses.SingleResultDto;
import com.comrades.app.application.services.alticci.dtos.AlticciDto;
import com.comrades.app.core.bases.UseCase;
import com.github.benmanes.caffeine.cache.Cache;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.caffeine.CaffeineCache;

import java.math.BigInteger;


@Getter
@Setter
public class UcAlticciCalculateValue extends UseCase<BigInteger> {

    private Long initialValue;
    private BigInteger calculatedValue;
    private final CacheManager _cacheManager;

    public UcAlticciCalculateValue(Long value, CacheManager cacheManager) {
        super();
        initialValue = value;
        calculatedValue = new BigInteger("0");
        _cacheManager = cacheManager;
    }

    @Override
    protected BigInteger execute() {
        return alticciCalculator(initialValue);
    }

    public BigInteger alticciCalculator(Long actualValue) {
        if (actualValue == 0) {
            return new BigInteger("0");
        } else if (actualValue == 1) {
            return new BigInteger("1");
        } else if (actualValue == 2) {
            return new BigInteger("1");
        } else {
            CaffeineCache caffeineCache = (CaffeineCache) _cacheManager.getCache("alticci");
            Cache<Object, Object> nativeCache = caffeineCache.getNativeCache();
            var oto = (SingleResultDto<AlticciDto>) nativeCache.asMap().get(actualValue);
            if (oto != null) {
                return new BigInteger(oto.getData().getCalculatedValue());
            } else {
                calculatedValue = calculatedValue.add(alticciCalculator(actualValue - 3)).add(alticciCalculator(actualValue - 2));
                return calculatedValue;
            }
        }
    }

}
