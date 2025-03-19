package io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.mapper;

import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.ApiResponseDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.BaseResponseCode;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.Mapper;
import java.util.List;

public class BaseResponseCodeToInfoMapper implements Mapper<ApiResponseDto.StatusInfo, BaseResponseCode> {

    @Override
    public ApiResponseDto.StatusInfo mapToDomain(BaseResponseCode baseResponseCode) {
        return ApiResponseDto.StatusInfo.builder()
                .code(baseResponseCode.getCode())
                .message(baseResponseCode.getMessage())
                .build();
    }

    @Override
    public List<ApiResponseDto.StatusInfo> mapToDomainList(List<BaseResponseCode> baseResponseCodes) {
        return baseResponseCodes.stream().map(this::mapToDomain).toList();
    }
}
