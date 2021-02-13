package org.navya.oms.omsadminservices.model;

import lombok.Data;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Data
public class ErrorResponseModel {

    @NonNull
    private String omsTraceId;

    @NonNull
    private String message;

    @NonNull
    List<String> errors;
}
