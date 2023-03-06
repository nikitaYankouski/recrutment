package com.hudman.codingtask.csv.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.hudman.codingtask.csv.enity.Csv;
import com.hudman.codingtask.csv.parser.ParserConfig;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoCsv {
    @JsonProperty("PRIMARY_KEY")
    private String primaryKey;
    @JsonProperty("NAME")
    private String name;
    @JsonProperty("DESCRIPTION")
    private String description;
    @JsonProperty("UPDATED_TIMESTAMP")
    @JsonFormat(pattern = ParserConfig.TIME_PATTERN)
    private LocalDateTime updatedTimestamp;

    public static List<Csv> mapToCsv(List<DtoCsv> dto) {
        return dto.stream()
                .map(item -> new Csv(
                        item.primaryKey,
                        item.name,
                        item.description,
                        item.updatedTimestamp)
                ).toList();
    }

    public static DtoCsv mapToDto(Csv item) {
        return new DtoCsv(
                item.getPrimaryKey(),
                item.getName(),
                item.getDescription(),
                item.getUpdatedTimestamp()
        );
    }
}
