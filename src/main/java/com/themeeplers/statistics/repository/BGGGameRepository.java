package com.themeeplers.statistics.repository;

import com.themeeplers.statistics.model.db.BGGGame;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BGGGameRepository extends PagingAndSortingRepository<BGGGame, Long> {
    BGGGame findByBggId(Long bggId);

    @Query("SELECT a FROM bgg_game a where a.weight=(SELECT max(b.weight) FROM bgg_game b)")
    BGGGame findTopGameOrderByWeightDesc();

    @Query("SELECT max(a.weight) FROM bgg_game a")
    Double findTopOrderByWeightDesc();

    @Query("SELECT max(a.rating) FROM bgg_game a")
    Double findTopOrderByRatingDesc();

    @Query("SELECT a from bgg_game a where a.rating=(SELECT max(b.rating) FROM bgg_game b)")
    BGGGame findTopGameOrderByRatingDesc();
}
