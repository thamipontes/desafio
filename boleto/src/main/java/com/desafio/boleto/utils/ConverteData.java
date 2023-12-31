package com.desafio.boleto.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ConverteData {

    public static LocalDate converterData(String data){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return LocalDate.parse(data, formatter);
    }

    public static String converterDataAtual(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate dataFormatada = LocalDate.now();
        return dataFormatada.format(formatter);
    }
}
