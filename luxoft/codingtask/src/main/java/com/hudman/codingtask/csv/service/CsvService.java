package com.hudman.codingtask.csv.service;

import com.hudman.codingtask.csv.dto.DtoCsv;
import com.hudman.codingtask.csv.enity.Csv;
import com.hudman.codingtask.csv.exception.NotFound;
import com.hudman.codingtask.csv.parser.BuilderConfig;
import com.hudman.codingtask.csv.parser.ParserConfig;
import com.hudman.codingtask.csv.parser.ParserCsv;
import com.hudman.codingtask.csv.parser.TypeConfig;
import com.hudman.codingtask.csv.repository.CsvRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class CsvService {

    private CsvRepository csvRepository;

    private BuilderConfig builder;

    private final Logger log = LoggerFactory.getLogger(CsvService.class);

    public CsvService(CsvRepository csvRepository, BuilderConfig builder) {
        this.builder = builder;
        this.csvRepository = csvRepository;
    }

    public DtoCsv fetch(String primaryKey) throws NotFound {
        final var csv = csvRepository.getByPrimaryKey(primaryKey);
        if (csv.isEmpty()) {
            log.warn("not found entity by primary key = " + primaryKey);
            throw new NotFound("not found by the primary key");
        }

        final var response = DtoCsv.mapToDto(csv.get());
        log.info("Given away csv by id = " + csv.get().getId());
        return response;
    }

    public void save(MultipartFile file) throws IOException {
        var config = builder.getConfig(TypeConfig.DTO_CSV, DtoCsv.class);

        final var listDto = ParserCsv.parseToDtoCsv(file, config);
        final var listCsv = DtoCsv.mapToCsv(listDto);
        csvRepository.saveAll(listCsv);
        log.info("Saved " + listDto.size() + " elements");
    }

    public void remove(String primaryKey) throws NotFound {
        final var response = csvRepository.getByPrimaryKey(primaryKey);
        if (response.isEmpty()) {
            log.warn("not found entity by primary key = " + primaryKey);
            throw new NotFound("not found by the primary key");
        }
        csvRepository.delete(response.get());
        log.info("Removed element by id = " + response.get().getId());
    }
}
