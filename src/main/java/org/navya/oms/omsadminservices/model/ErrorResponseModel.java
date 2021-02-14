package org.navya.oms.omsadminservices.model;

import java.util.List;
import lombok.Data;
import lombok.NonNull;

@Data
public class ErrorResponseModel {

  @NonNull private String omsTraceId;

  @NonNull private String message;

  @NonNull List<String> errors;
}
