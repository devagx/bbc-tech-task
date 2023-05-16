package bbc.news.elections.controller;

import bbc.news.elections.model.ConstituencyResult;
import bbc.news.elections.model.Scoreboard;
import bbc.news.elections.service.ResultService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ResultsControllerTests {
    @MockBean
    private ResultService resultService;

    @Autowired
    private TestRestTemplate template;

    @LocalServerPort
    private int port;

    private URL base;

    @BeforeEach
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/");
    }

    @Nested
    @DisplayName("Controller end point tests ensuring valid status codes returned for valid requests and posts")
    class ControllerExceptionsNotThrown {
        @Test
        void resultsController_validScoreboard_returnHttpStatusOK() {
            when(resultService.getScoreboardResults())
                    .thenReturn(new Scoreboard(null, "", 0, 1));

            ResponseEntity<Scoreboard> scoreboard = template.getForEntity(base.toString() + "/scoreboard", Scoreboard.class);

            assertThat(scoreboard.getStatusCode()).isEqualTo(HttpStatus.OK);
        }

        @Test
        void resultsController_validResult_returnHttpStatusOK() {
            when(resultService.getResult(1))
                    .thenReturn(new ConstituencyResult(1, "name", 2, null));

            ResponseEntity<ConstituencyResult> constituencyResult = template.getForEntity(base.toString() + "/result/1", ConstituencyResult.class);

            assertThat(constituencyResult.getStatusCode()).isEqualTo(HttpStatus.OK);
        }

        @Test
        void resultsController_validNewResult_returnHttpStatusCREATED() {
            ConstituencyResult cr = new ConstituencyResult(1, "name", 2, null);
            ResponseEntity<String> result = template.postForEntity(base.toString() + "/result", cr, String.class);

            assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        }
    }

    @Nested
    @DisplayName("Controller end point tests ensuring exceptions are thrown for invalid requests and posts")
    class ControllerExceptionsThrown {
        @Test
        void resultsController_invalidScoreboard_exceptionThrown() {
            when(resultService.getScoreboardResults())
                    .thenReturn(null);

            ResponseEntity<Scoreboard> scoreboard = template.getForEntity(base.toString() + "/scoreboard", Scoreboard.class);

            assertThat(scoreboard.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        }

        @Test
        void resultsController_invalidResult_exceptionThrown() {
            when(resultService.getResult(1))
                    .thenReturn(null);

            ResponseEntity<ConstituencyResult> constituencyResult = template.getForEntity(base.toString() + "/result/1", ConstituencyResult.class);

            assertThat(constituencyResult.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        }

        @Test
        void resultsController_invalidNewResult_exceptionThrown() {
            ConstituencyResult cr = new ConstituencyResult(null, "name", 2, null);
            ResponseEntity<String> result = template.postForEntity(base.toString() + "/result", cr, String.class);

            assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            assertThat(result.getBody()).isEqualTo("Id was null");
        }
    }
}
