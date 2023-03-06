package com.hudman.codingtask.csv.util;

import org.springframework.web.multipart.MultipartFile;

public class CsvUtil {
    public static final String TYPE = "text/csv";

    public static boolean hasCsvFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }
}
