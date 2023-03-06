package com.hudman.codingtask.csv.parser;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class ParserConfig {
    private CsvMapper mapper;
    private CsvSchema schema;
    private Class<?> type;
    public static final String TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    public ParserConfig(CsvMapper mapper, CsvSchema schema, Class<?> type) {
        this.mapper = mapper;
        this.schema = schema;
        this.type = type;
    }

    public ParserConfig() {}

    public CsvMapper getMapper() {
        return mapper;
    }

    public ParserConfig setNewMapper() {
        this.mapper = new CsvMapper();
        return this;
    }

    public ParserConfig setMapper(CsvMapper mapper) {
        this.mapper = mapper;
        return this;
    }

    public CsvSchema getSchema() {
        return schema;
    }

    public ParserConfig setSchema(CsvSchema schema) {
        this.schema = schema;
        return this;
    }

    public ParserConfig setSchemaFromMapper(Class<?> type) {
        this.schema = mapper.schemaFor(type).withHeader();
        return this;
    }

    public Class<?> getType() {
        return type;
    }

    public ParserConfig setType(Class<?> type) {
        this.type = type;
        return this;
    }

    public ParserConfig setRegisterModule(Module module) {
        this.mapper.registerModule(module);
        return this;
    }

    public ParserConfig setJavaTimeModule() {
        this.mapper.registerModule(new JavaTimeModule());
        return this;
    }
}
