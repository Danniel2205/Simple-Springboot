package com.bootcamp.day2.exception;

public class AnalysisAlreadyExistsException extends RuntimeException {
    public AnalysisAlreadyExistsException(String kalimat) {
        super("Data sudah ada");
    }
}

