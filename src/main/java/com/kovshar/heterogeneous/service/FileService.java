package com.kovshar.heterogeneous.service;

import com.kovshar.heterogeneous.enums.FileExtensions;

import java.io.File;
import java.time.format.DateTimeFormatter;

public interface FileService {

    byte[] getContent();

    String getFileName(String title);

    FileExtensions supportedType();
}
