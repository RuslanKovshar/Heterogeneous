package com.kovshar.heterogeneous.conventers;

import com.kovshar.heterogeneous.enums.FileExtensions;
import org.springframework.core.convert.converter.Converter;

public class StringToEnumConverter implements Converter<String, FileExtensions> {
    @Override
    public FileExtensions convert(String source) {
        return FileExtensions.valueOf(source.toUpperCase());
    }
}
