package bbc.news.elections.repository;

import bbc.news.elections.model.ConstituencyResult;

import java.util.Map;

public interface MapBasedRepository {
    ConstituencyResult getResult(Integer id);

    void newResult(ConstituencyResult result);

    Map<Integer, ConstituencyResult> getAll();

    void reset();
}
