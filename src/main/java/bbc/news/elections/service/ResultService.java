package bbc.news.elections.service;

import bbc.news.elections.model.ConstituencyResult;
import bbc.news.elections.model.Scoreboard;

public interface ResultService {
    void newResult(ConstituencyResult result);

    ConstituencyResult getResult(Integer id);

    Scoreboard getScoreboardResults();

    void reset();
}
