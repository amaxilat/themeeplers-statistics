package com.themeeplers.statistics.repository;

import com.themeeplers.statistics.model.db.GameEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface GameEntryRepository extends JpaRepository<GameEntry, Long> {
    @Query("SELECT DISTINCT a.url FROM GameEntry a")
    List<String> findDistinctUrl();

    long countByUrlAndDate(final String url, final long date);

    long countByUrl(String url);

    @Query("SELECT DISTINCT a.date FROM GameEntry a")
    Set<Long> findDistinctDate();

    Set<GameEntry> findByDate(final Long aLong);
}
