package com.kovshar.heterogeneous.service;

import com.kovshar.heterogeneous.enums.FileExtensions;
import org.json.JSONArray;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;

public interface FileService {

    byte[] getContent(JSONArray jsonArray);

    byte[] getContent();

    String getFileName(String title);

    FileExtensions supportedType();
}
