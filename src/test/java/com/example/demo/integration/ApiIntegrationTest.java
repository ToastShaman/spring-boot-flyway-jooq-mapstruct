package com.example.demo.integration;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

import com.example.demo.DemoApplication;
import io.restassured.RestAssured;
import java.time.Duration;
import java.time.Instant;
import java.util.Random;
import net.datafaker.Faker;
import net.datafaker.fileformats.Format;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(
    classes = DemoApplication.class,
    webEnvironment = WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("integration")
public class ApiIntegrationTest {

  private final Faker faker = new Faker(new Random(122232));

  @LocalServerPort
  private int serverPort;

  @BeforeEach
  public void setup() {
    RestAssured.basePath = "/api/v1";
    RestAssured.port = serverPort;
  }

  @Test
  void creates_events() {
    String request = Format.toJson()
        .set("title", () -> faker.name().firstName())
        .set("startDate", () -> Instant.now().plus(Duration.ofDays(1)))
        .build()
        .generate();

    given()
        .log().all()
        .body(request)
        .when()
        .header("Content-Type", "application/json")
        .post("/events")
        .then()
        .assertThat()
        .statusCode(201)
        .body("id", greaterThanOrEqualTo(1));
  }
}
