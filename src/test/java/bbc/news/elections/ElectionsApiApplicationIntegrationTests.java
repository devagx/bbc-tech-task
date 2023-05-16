package bbc.news.elections;

import bbc.news.elections.model.ConstituencyResult;
import bbc.news.elections.model.Scoreboard;
import bbc.news.elections.service.ResultService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.assertj.core.api.MapAssert.assertThatMap;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ElectionsApiApplicationIntegrationTests {
    @LocalServerPort
    private int port;

    private URL base;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private ResultService resultService;

    @BeforeEach
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/");
    }

    @AfterEach
    public void reset() throws IOException {
        resultService.reset();
    }

    @Test
    void first5Test() throws IOException {
        Scoreboard scoreboard = runTest(5);

        assertThatMap(scoreboard.getTotalPartySeats())
                .isNotNull()
                .contains(
                        entry("LD", 1),
                        entry("LAB", 4)
                );
        assertThat(scoreboard.getPartyWinner()).isEqualTo("noone");
        // assert LD == 1
        // assert LAB = 4
        // assert winner = noone
    }

    @Test
    void first100Test() throws IOException {
        Scoreboard scoreboard = runTest(100);

        assertThatMap(scoreboard.getTotalPartySeats())
                .isNotNull()
                .contains(
                        entry("LD", 12),
                        entry("LAB", 56),
                        entry("CON", 31)
                );
        assertThat(scoreboard.getPartyWinner()).isEqualTo("noone");
        // assert LD == 12
        // assert LAB == 56
        // assert CON == 31
        // assert winner = noone
    }

    @Test
    void first554Test() throws IOException {
        Scoreboard scoreboard = runTest(554);

        assertThatMap(scoreboard.getTotalPartySeats())
                .isNotNull()
                .contains(
                        entry("LD", 52),
                        entry("LAB", 325),
                        entry("CON", 167)
                );
        assertThat(scoreboard.getPartyWinner()).isEqualTo("LAB");
        // assert LD == 52
        // assert LAB = 325
        // assert CON = 167
        // assert winner = LAB
    }

    @Test
    void allTest() throws IOException {
        Scoreboard scoreboard = runTest(650);

        assertThatMap(scoreboard.getTotalPartySeats())
                .isNotNull()
                .contains(
                        entry("LD", 62),
                        entry("LAB", 349),
                        entry("CON", 210)
                );
        assertThat(scoreboard.getPartyWinner()).isEqualTo("LAB");
        assertThat(scoreboard.getPartyTotalVotes()).isEqualTo(650);
        // assert LD == 62
        // assert LAB == 349
        // assert CON == 210
        // assert winner = LAB
        // assert sum = 650
    }


    private Scoreboard runTest(int numberOfResults) throws IOException {
        for (int i = 1; i <= numberOfResults; i++) {
            Class<?> clazz = this.getClass();
            InputStream is = clazz.getResourceAsStream(String.format("/sample-election-results/result%s.json", String.format("%03d", i)));
            ConstituencyResult cr = objectMapper.readValue(is, ConstituencyResult.class);
            template.postForEntity(base.toString() + "/result", cr, String.class);
        }
        ResponseEntity<Scoreboard> scores = template.getForEntity(base.toString() + "/scoreboard", Scoreboard.class);
        return scores.getBody();
    }
}
