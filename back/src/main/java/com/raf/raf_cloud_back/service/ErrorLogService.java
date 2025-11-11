package com.raf.raf_cloud_back.service;

import com.raf.raf_cloud_back.dto.ErrorLogResponse;
import com.raf.raf_cloud_back.model.ErrorLog;

import java.util.List;

public interface ErrorLogService {

    public List<ErrorLogResponse> getErrorLogs(Long userId, List<String> permissions);

    public ErrorLog createError(ErrorLog errorLog);
}
