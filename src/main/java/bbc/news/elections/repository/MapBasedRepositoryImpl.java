package bbc.news.elections.repository;

import bbc.news.elections.model.ConstituencyResult;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MapBasedRepositoryImpl implements MapBasedRepository {

    private final Map<Integer, ConstituencyResult> results;

    public MapBasedRepositoryImpl() {
        results = new ConcurrentHashMap<>();
    }

    @Override
    public ConstituencyResult getResult(Integer id) {
        return results.get(id);
    }

    @Override
    public void newResult(ConstituencyResult result) {
        results.put(result.getId(), result);
    }

    @Override
    public Map<Integer, ConstituencyResult> getAll() {
        return results;
    }

    @Override
    public void reset() {
        results.clear();
    }

}
