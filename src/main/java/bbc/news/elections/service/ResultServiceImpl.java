package bbc.news.elections.service;

import bbc.news.elections.model.ConstituencyResult;
import bbc.news.elections.model.PartyResult;
import bbc.news.elections.model.Scoreboard;
import bbc.news.elections.repository.MapBasedRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ResultServiceImpl implements ResultService {
    private final MapBasedRepository mapBasedRepository;

    public ResultServiceImpl(MapBasedRepository mapBasedRepository) {
        this.mapBasedRepository = mapBasedRepository;
    }

    @Override
    public void newResult(ConstituencyResult result) {
        mapBasedRepository.newResult(result);
    }

    @Override
    public ConstituencyResult getResult(Integer id) {
        return mapBasedRepository.getResult(id);
    }

    @Override
    public Scoreboard getScoreboardResults() {
        Map<String, Integer> partySeats;
        String winner = "noone";
        int totalVotes = 0;
        int totalSharePercentage = 0;

        partySeats = new HashMap<>();

        for (Map.Entry<Integer, ConstituencyResult> entry : mapBasedRepository.getAll().entrySet()) {
            ConstituencyResult constituencyResult = entry.getValue();

            List<PartyResult> partyResults = constituencyResult.getPartyResults();

            partyResults.sort((a, b) -> b.getVotes().compareTo(a.getVotes()));

            //Top result of the array means party got most votes in constituency so gets a seat in parliament
            //so increment map entry by one
            String party = partyResults.get(0).getParty();
            partySeats.merge(party, 1, Integer::sum);
        }

        for (Map.Entry<String, Integer> entry : partySeats.entrySet()) {
            if (entry.getValue() >= 325) {
                winner = entry.getKey();
            }
            totalVotes += entry.getValue();
        }

        return new Scoreboard(partySeats, winner, totalVotes, totalSharePercentage);
    }

    @Override
    public void reset() {
        mapBasedRepository.reset();
    }


}
