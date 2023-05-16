package bbc.news.elections.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class Scoreboard {
    private final Map<String, Integer> totalPartySeats;
    private final String partyWinner;
    private final int partyTotalVotes;
    private final int partyTotalSharePercentage;

    public Scoreboard(
            @JsonProperty("totalPartySeats") Map<String, Integer> totalPartySeats,
            @JsonProperty("partyWinner") String partyWinner,
            @JsonProperty("partyTotalVotes") int partyTotalVotes,
            @JsonProperty("partyTotalSharePercentage") int partyTotalSharePercentage
    ) {
        this.totalPartySeats = totalPartySeats;
        this.partyWinner = partyWinner;
        this.partyTotalVotes = partyTotalVotes;
        this.partyTotalSharePercentage = partyTotalSharePercentage;
    }

    public Map<String, Integer> getTotalPartySeats() {
        return totalPartySeats;
    }

    public String getPartyWinner() {
        return partyWinner;
    }

    public int getPartyTotalVotes() {
        return partyTotalVotes;
    }

    public int getPartyTotalSharePercentage() {
        return partyTotalSharePercentage;
    }
}
