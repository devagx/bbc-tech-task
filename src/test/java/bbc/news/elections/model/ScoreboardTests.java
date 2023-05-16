package bbc.news.elections.model;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ScoreboardTests {
    @Test
    void getTotalPartySeats_validScoreboard_returnsValidPartySeats() {
        Map<String, Integer> expectedPartySeats = new HashMap<>();
        expectedPartySeats.put("Test1", 1);
        expectedPartySeats.put("Test2", 2);

        Scoreboard expectedScoreboard = new Scoreboard(expectedPartySeats, "", 0, 0);

        Map<String, Integer> resultPartySeats = expectedScoreboard.getTotalPartySeats();

        assertThat(resultPartySeats).isEqualTo(expectedPartySeats);
        assertThat(resultPartySeats).hasSize(2);
    }

    @Test
    void getPartyWinner_validScoreboard_returnsValidWinner() {
        Scoreboard expectedScoreboard = new Scoreboard(null, "TestWinner", 0, 0);

        String result = expectedScoreboard.getPartyWinner();

        assertThat(result).isEqualTo("TestWinner");
    }

    @Test
    void getPartyTotalVotes_validScoreboard_returnsValidTotalResults() {
        Scoreboard expectedScoreboard = new Scoreboard(null, "", 2, 0);

        int result = expectedScoreboard.getPartyTotalVotes();

        assertThat(result).isEqualTo(2);
    }

    @Test
    void getPartyTotalSharePercentage_validScoreboard_returnsValidTotalPertSharePercentage() {
        Scoreboard expectedScoreboard = new Scoreboard(null, "", 0, 2);

        int result = expectedScoreboard.getPartyTotalSharePercentage();

        assertThat(result).isEqualTo(2);
    }
}
