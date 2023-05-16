package bbc.news.elections.repository;

import bbc.news.elections.model.ConstituencyResult;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.assertj.core.api.MapAssert.assertThatMap;
import static org.mockito.Mockito.*;

@SpringBootTest
class MapBasedRepositoryTests {
    @Autowired
    private MapBasedRepository mapBasedRepository;

    @Test
    void newResultAndgetResult_validConstituency_returnsCorrectConstituency() {
        ConstituencyResult expected = new ConstituencyResult(1, "name", 2, null);

        mapBasedRepository.newResult(expected);

        ConstituencyResult result = mapBasedRepository.getResult(1);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void getAll_validScoreboardMap_returnsCorrectScoreboardMap() {
        ConstituencyResult expected = new ConstituencyResult(1, "name", 2, null);

        mapBasedRepository.newResult(expected);

        assertThatMap(mapBasedRepository.getAll())
                .isNotNull()
                .contains(entry(1, expected));
    }

    @Test
    void reset_callMethod_verifyResetMethodCalledOnce() {
        MapBasedRepository mockMapBasedRepository = Mockito.mock(MapBasedRepository.class);

        doNothing().when(mockMapBasedRepository).reset();

        mockMapBasedRepository.reset();

        verify(mockMapBasedRepository, times(1)).reset();
    }
}
