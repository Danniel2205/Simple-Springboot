package com.bootcamp.day2.dto.response;

import com.bootcamp.day2.entity.CharAnalysis;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class CharAnalysisResponse {
    private Long id;
    private String kalimat;
    private int totalHuruf;
    private String author;
    private Map<Character, Integer> frekuensi;

    public static CharAnalysisResponse from (CharAnalysis data,Map<Character, Integer> frekuensi){
        return new CharAnalysisResponse(data.getId(), data.getKalimat(), data.getTotalHuruf(),data.getAuthor(), frekuensi);
    }
}
