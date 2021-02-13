package org.navya.oms.omsadminservices.model;

import lombok.Data;

import java.util.List;

@Data(staticConstructor = "of")
public class ApiResponseModel<T> {

    private final List<T> data;

    private final ErrorResponseModel error;
}
