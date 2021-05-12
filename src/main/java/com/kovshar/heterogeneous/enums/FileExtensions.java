package com.kovshar.heterogeneous.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum FileExtensions {
    JSON(".json"), CSV(".csv");


    private final String extension;

    FileExtensions(String extension) {
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }

    @JsonCreator
    public static FileExtensions forValues(String type) {
        for (FileExtensions extension : FileExtensions.values()) {
            if (extension.name().equalsIgnoreCase(type)) {
                return extension;
            }
        }
        throw new RuntimeException("Extension '" + type + "' not supported yet!");
    }
}
