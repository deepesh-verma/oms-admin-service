package org.navya.oms.omsadminservices.functional;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.navya.oms.omsadminservices.OmsAdminServicesApplicationTests;
import org.navya.oms.omsadminservices.model.ApiResponseModel;
import org.navya.oms.omsadminservices.model.SexEnum;
import org.navya.oms.omsadminservices.model.UserModel;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;

@Slf4j
public class UserApiTests extends OmsAdminServicesApplicationTests {

  private static final String USERS_API_URI = "/users";

  @Nested
  @DisplayName("Tests for POST /users API")
  class AddUsersTest {

    @Test
    void testAddUserError() {

      UserModel newUser = UserModel.builder()
              .userName("User")
              .firstName("first-name")
              .lastName("last-name-")
              .dateOfBirth(LocalDate.now())
              .age(25)
              .sex(SexEnum.other)
              .email("user@test.com")
              .build();

      ResponseEntity<ApiResponseModel<UserModel>> response = callAddUserApi(newUser);
      log.info("Response from addUser : \n {}", response);
      assertThat(response).isNotNull();
      assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
      assertThat(response.getBody()).isNotNull();
      assertThat(response.getBody().getError()).isNotNull();
      List<String> errors = response.getBody().getError().getErrors();
      assertThat(errors).isNotNull().hasSize(1);
      assertThat(errors.get(0)).contains("isSoftDeleted").contains("must not be null");
      assertThat(response.getBody().getData()).isNull();
    }

    @Test
    void testAddUserSuccess() {

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
                      .sex(SexEnum.male)
                      .email("user" + index + "@test.com")
                      .build())
          .map(this::callAddUserApi)
              .forEach(this::validateAddUserSuccessResponse);
    }

    private void validateAddUserSuccessResponse(ResponseEntity<ApiResponseModel<UserModel>> response) {
      log.info("Response from addUser : \n {}", response);
      assertThat(response).isNotNull();
      assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
      assertThat(response.getBody()).isNotNull();
      assertThat(response.getBody().getError()).isNull();
      assertThat(response.getBody().getData()).isNotNull().hasSize(1);
    }

    private ResponseEntity<ApiResponseModel<UserModel>> callAddUserApi(UserModel newUser) {
      HttpEntity<UserModel> requestEntity = getRequestEntity(newUser);
      return
          restTemplate.exchange(
              USERS_API_URI,
              HttpMethod.POST,
              requestEntity,
              new ParameterizedTypeReference<ApiResponseModel<UserModel>>() {});
    }
  }

  private HttpEntity<UserModel> getRequestEntity(UserModel newUser) {
    MultiValueMap<String, String> headers = getHeaders();
    return new HttpEntity<>(newUser, headers);
  }
}
