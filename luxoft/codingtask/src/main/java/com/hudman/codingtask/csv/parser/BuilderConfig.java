package com.hudman.codingtask.csv.parser;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@Component
public class BuilderConfig {

    Map<TypeConfig, ParserConfig> configs;

    public ParserConfig getConfig(TypeConfig typeConfig, Class<?> type) {
        if (this.configs == null) {
            configs = new HashMap<>();
        }

        var optionalConfig = Stream.of(configs)
                .filter(config -> config.containsKey(typeConfig))
                .findFirst();

        if (optionalConfig.isPresent()) {
            return optionalConfig.get().get(typeConfig);
        }

        var newConfig  = switch (typeConfig) {
            case DTO_CSV -> assembleDtoCsvConfig(type);
        };
        configs.put(typeConfig, newConfig);

        return newConfig;
    }

    private ParserConfig assembleDtoCsvConfig(Class<?> type) {
        return new ParserConfig().setNewMapper().setType(type).setJavaTimeModule().setSchemaFromMapper(type);
    }
}
