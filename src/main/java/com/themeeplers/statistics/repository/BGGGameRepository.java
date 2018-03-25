package com.themeeplers.statistics.repository;

import com.themeeplers.statistics.model.db.BGGGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BGGGameRepository extends JpaRepository<BGGGame, Long> {
    BGGGame findByBggId(Long bggId);
}
