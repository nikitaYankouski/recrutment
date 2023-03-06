package com.hudman.codingtask.csv.repository;

import com.hudman.codingtask.csv.enity.Csv;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CsvRepository extends JpaRepository<Csv, Long> {
    Optional<Csv> getByPrimaryKey(String primaryKey);
}
