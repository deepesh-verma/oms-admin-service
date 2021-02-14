package org.navya.oms.omsadminservices.functional;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.navya.oms.omsadminservices.OmsAdminServicesApplicationTests;
import org.navya.oms.omsadminservices.model.ApiResponseModel;
import org.navya.oms.omsadminservices.model.UserModel;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Slf4j
public class UserApiTests extends OmsAdminServicesApplicationTests {

  private static final String USERS_API_URI = "/users";

  @Test
  void testAddUser() {

    IntStream.range(0, 10)
        .mapToObj(
            index ->
                UserModel.builder()
                    .userName("User-" + index)
                    .isSoftDeleted(0)
                    .firstName("first-name-" + index)
                    .lastName("last-name-" + index)
                    .dateOfBirth(LocalDate.now().minusYears(index))
                    .age(index)
                    .sex("male")
                    .email("user" + index + "@test.com")
                    .build())
        .forEach(this::callAddUserApi);
  }

  private void callAddUserApi(UserModel newUser) {
    HttpEntity<UserModel> requestEntity = getRequestEntity(newUser);
    ResponseEntity<ApiResponseModel<UserModel>> response =
        restTemplate.exchange(
            USERS_API_URI,
            HttpMethod.POST,
            requestEntity,
            new ParameterizedTypeReference<ApiResponseModel<UserModel>>() {});
    log.info("Response from addUser : \n {}", response);
    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getError()).isNull();
    assertThat(response.getBody().getData()).isNotNull().hasSize(1);
  }

  private HttpEntity<UserModel> getRequestEntity(UserModel newUser) {
    MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
    headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    return new HttpEntity<>(newUser, headers);
  }
}
