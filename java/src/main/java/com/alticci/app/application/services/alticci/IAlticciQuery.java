package com.alticci.app.application.services.alticci;

import com.alticci.app.application.responses.SingleResultDto;
import com.alticci.app.application.services.alticci.dtos.AlticciDto;

import java.util.concurrent.ConcurrentMap;

public interface IAlticciQuery {
    SingleResultDto<AlticciDto> findById(Long value);
    ConcurrentMap<Object,Object> keys(String cacheName);
}
