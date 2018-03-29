package com.themeeplers.statistics.repository;

import com.themeeplers.statistics.model.db.BGGGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BGGGameRepository extends PagingAndSortingRepository<BGGGame, Long> {
    BGGGame findByBggId(Long bggId);

    @Query("SELECT b from bgg_game where b.weight=(SELECT max(a.weight) FROM bgg_game a) limit 1")
    BGGGame findTopOrderByWeightDesc();

    @Query("SELECT b from bgg_game where b.rating=(SELECT max(a.rating) FROM bgg_game a) limit 1")
    BGGGame findTopOrderByRatingDesc();
}
