package com.hudman.codingtask.csv.parser;

import com.fasterxml.jackson.databind.MappingIterator;
import com.hudman.codingtask.csv.dto.DtoCsv;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public class ParserCsv {

    public static List<DtoCsv> parseToDtoCsv(MultipartFile file, ParserConfig config) throws IOException {
        final var mapper = config.getMapper();
        final var type = config.getType();
        final var schema = config.getSchema();

        MappingIterator<DtoCsv> iterator =
                mapper.readerFor(type).with(schema).readValues(file.getInputStream());
        return iterator.readAll();
    }
}
