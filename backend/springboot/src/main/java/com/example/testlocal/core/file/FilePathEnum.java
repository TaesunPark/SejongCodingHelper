package com.example.testlocal.core.file;

public enum FilePathEnum {
    C_FILE_PATH("/Users/taesunpark/home/c/gcc/test4.c"),
    C_INPUT_TEXT_FILE_PATH("/Users/taesunpark/home/c/gcc/test.txt"),
    C_EXECUTION_FILE_PATH("/Users/taesunpark/home/c/gcc/test"),
    C_EXECUTION_PATH("/Users/taesunpark/home/c/gcc/test"),
    PYTHON_FILE_PATH("/Users/taesunpark/home/python3/test.py"),
    PYTHON_INPUT_TEXT_FILE_PATH("/Users/taesunpark/home/python3/test.txt"),
    PYTHON_FILENAME("test.py"),
    PYTHON_EXECUTION_PATH("/Users/taesunpark/home/python3/test.py")
    ;

    private final String path;
    
    private FilePathEnum(String path) {
        this.path = path;
    }
    
    public String getPath() {
        return path;
    }
}