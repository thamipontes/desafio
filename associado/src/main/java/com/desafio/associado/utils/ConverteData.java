package com.desafio.associado.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ConverteData {
    private ConverteData() {
        throw new IllegalStateException("Utility class");
    }
    public static String converterDataAtual(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate dataFormatada = LocalDate.now();
        return dataFormatada.format(formatter);
    }
}
