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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.util.MultiValueMap;

@Slf4j
public class UserApiTests extends OmsAdminServicesApplicationTests {

  private static final String USERS_API_URI = "/users";

  private HttpEntity<UserModel> getRequestEntity(UserModel userModel) {
    MultiValueMap<String, String> headers = getHeaders();
    return new HttpEntity<>(userModel, headers);
  }

  private ResponseEntity<ApiResponseModel<UserModel>> callAddUserApi(UserModel newUser) {
    HttpEntity<UserModel> requestEntity = getRequestEntity(newUser);
    return restTemplate.exchange(
        USERS_API_URI,
        HttpMethod.POST,
        requestEntity,
        new ParameterizedTypeReference<ApiResponseModel<UserModel>>() {});
  }

  private UserModel getValidUserModel(int index) {
    return UserModel.builder()
        .userName("User-" + index)
        .firstName("first-name-" + index)
        .lastName("last-name-" + index)
        .dateOfBirth(LocalDate.now().minusYears(index))
        .age(index)
        .sex(SexEnum.male)
        .email("user" + index + "@test.com")
        .build();
  }

  @Nested
  @DisplayName("Tests for POST /users API")
  @Sql(scripts = "classpath:cleanup.sql")
  class AddUsersTest {

    @Test
    void testAddUserError() {

      UserModel newUser =
          UserModel.builder()
              .userName("User")
              .firstName("first-name")
              .lastName("last-name-")
              .dateOfBirth(LocalDate.now())
              .sex(SexEnum.other)
              .email("user@test.com")
              .build();

      ResponseEntity<ApiResponseModel<UserModel>> response = callAddUserApi(newUser);
      log.info("Response from addUser : \n {}", response);
      assertBadRequestValidationFailedResponse(response);
      List<String> errors = response.getBody().getError().getErrors();
      assertThat(errors).isNotNull().hasSize(1);
      assertThat(errors.get(0)).contains("age").contains("must not be null");
      assertThat(response.getBody().getData()).isNull();
    }

    @Test
    void testAddUserSuccess() {

      IntStream.range(0, 10)
          .mapToObj(UserApiTests.this::getValidUserModel)
          .map(UserApiTests.this::callAddUserApi)
          .forEach(this::validateAddUserSuccessResponse);
    }

    private void validateAddUserSuccessResponse(
        ResponseEntity<ApiResponseModel<UserModel>> response) {
      log.info("Response from addUser : \n {}", response);
      assertSuccessResponse(response);
      assertThat(response.getBody().getData()).isNotNull().hasSize(1);
    }
  }

  @Nested
  @DisplayName("Tests for GET /users API")
  @Sql(scripts = "classpath:cleanup.sql")
  class GetUsersTest {

    @Test
    void testGetUsers() {

      // Add 10 users
      IntStream.range(0, 10)
          .mapToObj(UserApiTests.this::getValidUserModel)
          .forEach(UserApiTests.this::callAddUserApi);

      ResponseEntity<ApiResponseModel<UserModel>> response =
          restTemplate.exchange(
              USERS_API_URI,
              HttpMethod.GET,
              getRequestEntity(null),
              new ParameterizedTypeReference<ApiResponseModel<UserModel>>() {});
      assertSuccessResponse(response);

      // Should get back all 10 users
      assertThat(response.getBody().getData().size()).isEqualTo(10);
    }
  }

  @Nested
  @DisplayName("Tests for PUT /users API")
  @Sql(scripts = "classpath:cleanup.sql")
  class UpdateUserTest {

    @Test
    void testUpdateUser() {

      ResponseEntity<ApiResponseModel<UserModel>> newUserResponse =
          UserApiTests.this.callAddUserApi(UserApiTests.this.getValidUserModel(15));

      String newEmail = "new-email@test.com";
      UserModel updatedUserModel = newUserResponse.getBody().getData().get(0);
      updatedUserModel.setEmail(newEmail);

      ResponseEntity<ApiResponseModel<UserModel>> response =
          restTemplate.exchange(
              USERS_API_URI,
              HttpMethod.PUT,
              getRequestEntity(updatedUserModel),
              new ParameterizedTypeReference<ApiResponseModel<UserModel>>() {});
      assertSuccessResponse(response);
      assertThat(response.getBody().getData()).isNotNull().hasSize(1);
      UserModel updatedUserModelResponse = response.getBody().getData().get(0);
      assertThat(updatedUserModelResponse)
          .isNotNull()
          .hasFieldOrPropertyWithValue("email", newEmail);
    }
  }

  @Nested
  @DisplayName("Tests for DELETE /users API")
  @Sql(scripts = "classpath:cleanup.sql")
  class DeleteUserTest {

    @Test
    void testDeleteUser() {

      ResponseEntity<ApiResponseModel<UserModel>> newUserResponse =
          UserApiTests.this.callAddUserApi(UserApiTests.this.getValidUserModel(15));

      String newUserId = newUserResponse.getBody().getData().get(0).getUserId().toString();

      ResponseEntity<ApiResponseModel<UserModel>> response =
          restTemplate.exchange(
              USERS_API_URI + "/" + newUserId,
              HttpMethod.DELETE,
              getRequestEntity(null),
              new ParameterizedTypeReference<ApiResponseModel<UserModel>>() {});
      assertSuccessResponse(response);
      assertThat(response.getBody().getData()).isNull();

      ResponseEntity<ApiResponseModel<UserModel>> getUserResponse = callGetUserByIdApi(newUserId);
      assertSuccessResponse(getUserResponse);
      assertThat(response.getBody().getData()).isNull();
    }
  }

  private ResponseEntity<ApiResponseModel<UserModel>> callGetUserByIdApi(String userId) {
    return restTemplate.exchange(
        USERS_API_URI + "/" + userId,
        HttpMethod.GET,
        getRequestEntity(null),
        new ParameterizedTypeReference<ApiResponseModel<UserModel>>() {});
  }
}
