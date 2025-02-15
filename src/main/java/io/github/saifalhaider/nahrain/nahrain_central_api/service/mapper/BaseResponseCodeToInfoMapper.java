package io.github.saifalhaider.nahrain.nahrain_central_api.service.mapper;

import io.github.saifalhaider.nahrain.nahrain_central_api.model.dto.ApiResponseDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.model.dto.responseCode.BaseResponseCode;

import java.util.List;

public class BaseResponseCodeToInfoMapper implements Mapper<ApiResponseDto.StatusInfo, BaseResponseCode> {

    @Override
    public ApiResponseDto.StatusInfo toEntity(BaseResponseCode baseResponseCode) {
        return ApiResponseDto.StatusInfo.builder()
                .code(baseResponseCode.getCode())
                .message(baseResponseCode.getMessage())
                .build();
    }

    @Override
    public List<ApiResponseDto.StatusInfo> toEntityList(List<BaseResponseCode> baseResponseCodes) {
        return baseResponseCodes.stream().map(this::toEntity).toList();
    }
}
