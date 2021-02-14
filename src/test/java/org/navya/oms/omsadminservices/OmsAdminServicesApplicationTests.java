package org.navya.oms.omsadminservices;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.SQLException;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.navya.oms.omsadminservices.model.ApiResponseModel;
import org.navya.oms.omsadminservices.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.utility.DockerImageName;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OmsAdminServicesApplicationTests {

  private static MariaDBContainer mariaDB;

  @Autowired private DataSource dataSource;

  @Autowired protected TestRestTemplate restTemplate;

  @BeforeAll
  static void beforeAll() {

    mariaDB =
        new MariaDBContainer(DockerImageName.parse("mariadb:10.5.8"))
            .withDatabaseName("oms")
            .withUsername("oms")
            .withPassword("my-secret-pw");
    mariaDB.start();

    String jdbcUrl = mariaDB.getJdbcUrl();
    log.info("Setting system properties : jdbcUrl: {}", jdbcUrl);
    System.setProperty("spring.datasource.url", jdbcUrl);
  }

  protected MultiValueMap<String, String> getHeaders() {
    MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
    headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    return headers;
  }

  protected <T> void assertSuccessResponse(ResponseEntity<ApiResponseModel<T>> response) {
    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getError()).isNull();
  }

  protected void assertBadRequestValidationFailedResponse(
      ResponseEntity<ApiResponseModel<UserModel>> response) {
    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getError()).isNotNull();
  }

  @Test
  void contextLoads() throws SQLException {
    assertThat(dataSource).isNotNull();
    assertThat(dataSource.getConnection()).isNotNull();
  }
}
