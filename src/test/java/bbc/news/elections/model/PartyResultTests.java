package bbc.news.elections.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class PartyResultTests {
    @Test
    void getParty_validPartyResult_returnsValidParty() {
        PartyResult partyResult = new PartyResult("party", 2, BigDecimal.valueOf(2));

        String result = partyResult.getParty();

        assertThat(result).isEqualTo("party");
    }

    @Test
    void getVotes_validPartyResult_returnsValidVotes() {
        PartyResult partyResult = new PartyResult("party", 2, BigDecimal.valueOf(1));

        int result = partyResult.getVotes();

        assertThat(result).isEqualTo(2);
    }

    @Test
    void getShare_validPartyResult_returnsValidShare() {
        PartyResult partyResult = new PartyResult("party", 2, BigDecimal.valueOf(3));

        BigDecimal result = partyResult.getShare();

        assertThat(result).isEqualTo(BigDecimal.valueOf(3));
    }
}
