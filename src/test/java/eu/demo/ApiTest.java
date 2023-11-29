package eu.demo;

import eu.demo.util.Constants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(
        classes = {DemoApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApiTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void greetingShouldReturnSentString() {
        // Given a String that we send to the API GET endpoint
        String expectedString = "test-me";
        String requestString = "http://localhost:" + port
                + Constants.REST_API_PREFIX + "/hello/" + expectedString;

        // When we call said endpoint and get the response
        ResponseEntity<String> responseEntity =
                this.restTemplate.getForEntity(requestString, String.class);

        // That response is the test message sent.
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        assertThat(responseEntity.getBody()).isEqualTo(expectedString);
    }
}
