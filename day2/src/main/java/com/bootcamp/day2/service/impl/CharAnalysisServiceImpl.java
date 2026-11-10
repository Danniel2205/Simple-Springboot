package com.bootcamp.day2.service.impl;

import com.bootcamp.day2.dto.feign.QuoteResponse;
import com.bootcamp.day2.dto.request.CharAnalysisRequest;
import com.bootcamp.day2.dto.response.CharAnalysisResponse;
import com.bootcamp.day2.entity.CharAnalysis;
import com.bootcamp.day2.exception.AnalysisAlreadyExistsException;
import com.bootcamp.day2.exception.AnalysisNotFoundException;
import com.bootcamp.day2.exception.QuoteUnavailableException;
import com.bootcamp.day2.repository.CharAnalysisRepository;
import com.bootcamp.day2.rest.QuoteClient;
import com.bootcamp.day2.service.CharAnalysisService;
import com.bootcamp.day2.util.CharCounter;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CharAnalysisServiceImpl implements CharAnalysisService {

    private final CharAnalysisRepository charAnalysisRepository;
    private final QuoteClient client;

    @Override
    public List<CharAnalysisResponse> cari(String kata) {
        List<CharAnalysis> semua;
        if (kata == null || kata.isBlank()) {
            semua = charAnalysisRepository.findAll();
        } else {
            semua = charAnalysisRepository.findByKalimatContainingIgnoreCase(kata);
        }

        List<CharAnalysisResponse> hasil = new ArrayList<>();
        for (CharAnalysis data: semua) {
            hasil.add(toResponse(data));
        }
        return hasil;
    }


    @Override
    public CharAnalysisResponse simpan(CharAnalysisRequest request) {
        String kalimat = request.getKalimat().trim();
        if (charAnalysisRepository.existsByKalimatIgnoreCase(kalimat)){
            throw new AnalysisAlreadyExistsException(kalimat);
        }
        return simpanFein(kalimat, null);
    }

    public CharAnalysisResponse cariById() {
        return cariById(null);
    }

    @Override
    public CharAnalysisResponse cariById(Long id) {
        CharAnalysis data = charAnalysisRepository.findById(id)
                .orElseThrow(() -> new AnalysisNotFoundException(id));
        return toResponse(data);
    }

    @Override
    public List<CharAnalysisResponse> cariMinimal(int min) {
        List<CharAnalysisResponse> hasil = new ArrayList<>();
        for (CharAnalysis data : charAnalysisRepository.findByTotalHurufMinimal(min)) {
            hasil.add(toResponse(data));
        }
        return hasil;
    }

    @Override
    public List<CharAnalysisResponse> cariTerbaru() {
        List<CharAnalysisResponse> hasil = new ArrayList<>();
        for (CharAnalysis data : charAnalysisRepository.findTerbaru()) {
            hasil.add(toResponse(data));
        }
        return hasil;
    }

    @Override
    public CharAnalysisResponse analisisDariQuote(){
        QuoteResponse quote;
        try {
            quote = client.getQuote();
        } catch (FeignException feignException){
            throw new QuoteUnavailableException("Service Unavailable");
        }
        return simpanFein(quote.getQuote(),quote.getAuthor());
    }

    private CharAnalysisResponse simpanFein (String kalimat, String author) {
        CharCounter counter = new CharCounter(kalimat);
        CharAnalysis data = new CharAnalysis();

        data.setKalimat(kalimat);
        data.setAuthor(author);//dari request
        data.setTotalHuruf(counter.getTotalHuruf()); //dari hasil hitung
        data.setCreatedAt(LocalDateTime.now()); //waktu server

        charAnalysisRepository.save(data);
        return CharAnalysisResponse.from(data, counter.toMap());
    }

    private List<CharAnalysisResponse> toResponseList(List<CharAnalysis> semua) {
        List<CharAnalysisResponse> hasil = new ArrayList<>();
        for (CharAnalysis data : semua) {
            hasil.add(toResponse(data));
        }
        return hasil;
    }

    private CharAnalysisResponse toResponse(CharAnalysis data) {
        CharCounter counter = new CharCounter(data.getKalimat());
        return CharAnalysisResponse.from(data, counter.toMap());
    }


}
