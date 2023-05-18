package com.example.testlocal.core.file;

public enum FilePathEnum {
    FILE1("path/to/file1.txt"),
    FILE2("path/to/file2.txt"),
    FILE3("path/to/file3.txt");
    
    private final String path;
    
    private FilePathEnum(String path) {
        this.path = path;
    }
    
    public String getPath() {
        return path;
    }
}