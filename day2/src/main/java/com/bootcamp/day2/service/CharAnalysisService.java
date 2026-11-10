package com.bootcamp.day2.service;

import com.bootcamp.day2.dto.request.CharAnalysisRequest;
import com.bootcamp.day2.dto.response.CharAnalysisResponse;
import java.util.List;

public interface CharAnalysisService {
    List<CharAnalysisResponse> cari(String kata);
    CharAnalysisResponse simpan(CharAnalysisRequest request);
    CharAnalysisResponse cariById(Long id);
    List<CharAnalysisResponse> cariMinimal(int min);
    List<CharAnalysisResponse> cariTerbaru();
    CharAnalysisResponse analisisDariQuote();
}
