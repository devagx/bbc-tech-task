package bbc.news.elections.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ConstituencyResultTests {
    @Test
    void getId_constituencyResult_returnsValidId() {
        ConstituencyResult constituencyResult = new ConstituencyResult(1, "name", 2, null);

        int result = constituencyResult.getId();

        assertThat(result).isEqualTo(1);
    }

    @Test
    void getName_constituencyResult_returnsValidName() {
        ConstituencyResult constituencyResult = new ConstituencyResult(1, "name", 2, null);

        String result = constituencyResult.getName();

        assertThat(result).isEqualTo("name");
    }

    @Test
    void getSeqNo_constituencyResult_returnsValidSeqNo() {
        ConstituencyResult constituencyResult = new ConstituencyResult(1, "name", 2, null);

        int result = constituencyResult.getSeqNo();

        assertThat(result).isEqualTo(2);
    }

    @Test
    void getPartyResults_constituencyResult_returnsValidPartyResults() {
        List<PartyResult> expected = List.of(new PartyResult("party", 2, BigDecimal.valueOf(40)));
        ConstituencyResult constituencyResult = new ConstituencyResult(1, "name", 2, expected);

        List<PartyResult> result = constituencyResult.getPartyResults();

        assertThat(result).isEqualTo(expected);
    }
}
