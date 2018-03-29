package com.themeeplers.statistics.repository;

import com.themeeplers.statistics.model.db.BGGGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BGGGameRepository extends PagingAndSortingRepository<BGGGame, Long> {
    BGGGame findByBggId(Long bggId);

    @Query("SELECT max(a.weight)) FROM GameEntry a")
    BGGGame findTopOrderByWeightDesc();

    @Query("SELECT max(a.rating)) FROM GameEntry a")
    BGGGame findTopOrderByRatingDesc();
}
