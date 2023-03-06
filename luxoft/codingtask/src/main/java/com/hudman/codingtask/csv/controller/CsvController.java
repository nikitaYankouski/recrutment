package com.hudman.codingtask.csv.controller;

import com.hudman.codingtask.csv.dto.DtoCsv;
import com.hudman.codingtask.csv.exception.NotFound;
import com.hudman.codingtask.csv.service.CsvService;
import com.hudman.codingtask.csv.util.CsvUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@RestController
@RequestMapping("csv")
public class CsvController {

    private CsvService csvService;

    CsvController(CsvService service) {
        this.csvService = service;
    }

    @GetMapping(produces = "application/json", value = "{primaryKey}")
    public DtoCsv getByPrimaryKey(@PathVariable("primaryKey") String primaryKey) {
        if (primaryKey.isEmpty()) {
            final var msg = "primaryKey can not be empty";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);
        }

        try {
            return csvService.fetch(primaryKey);
        } catch (NotFound notFoundExc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, notFoundExc.getMessage());
        }
    }

    @PostMapping()
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
        if (!CsvUtil.hasCsvFormat(file) || file.isEmpty()) {
            final var msg = "File must have csv type and not empty";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);
        }

        try {
            csvService.save(file);
        } catch (IOException exception) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("{primaryKey}")
    public ResponseEntity<String> remove(@PathVariable("primaryKey") String primaryKey) {
        if (primaryKey.isEmpty()) {
            final var msg = "primaryKey can not be empty";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);
        }

        try {
            csvService.remove(primaryKey);
        } catch (NotFound notFoundExc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, notFoundExc.getMessage());
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
