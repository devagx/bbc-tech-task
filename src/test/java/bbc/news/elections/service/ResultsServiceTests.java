package bbc.news.elections.service;

import bbc.news.elections.model.ConstituencyResult;
import bbc.news.elections.model.Scoreboard;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
class ResultsServiceTests {
    @Autowired
    private ResultService resultService;

    @Test
    void newResultAndgetResult_validConstituency_returnsCorrectConstituency() {
        ConstituencyResult expected = new ConstituencyResult(1, "name", 2, null);

        resultService.newResult(expected);

        ConstituencyResult result = resultService.getResult(1);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void getScoreboardResults_validScoreboard_returnsCorrectScoreboardWinner() {
        ResultService resultServiceMock = Mockito.mock(ResultService.class);
        Scoreboard expectedScoreboard = new Scoreboard(null, "TestWinner", 0, 0);

        when(resultServiceMock.getScoreboardResults()).thenReturn(expectedScoreboard);

        assertThat(resultServiceMock.getScoreboardResults().getPartyWinner()).isEqualTo("TestWinner");
    }

    @Test
    void reset_callMethod_verifyResetMethodCalledOnce() {
        ResultService mockResultService = Mockito.mock(ResultService.class);

        doNothing().when(mockResultService).reset();

        mockResultService.reset();

        verify(mockResultService, times(1)).reset();
    }
}
