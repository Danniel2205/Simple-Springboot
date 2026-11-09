package com.bootcamp.day2.service.impl;

import com.bootcamp.day2.dto.request.CharAnalysisRequest;
import com.bootcamp.day2.dto.response.CharAnalysisResponse;
import com.bootcamp.day2.entity.CharAnalysis;
import com.bootcamp.day2.repository.CharAnalysisRepository;
import com.bootcamp.day2.service.CharAnalysisService;
import com.bootcamp.day2.util.CharCounter;
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
        CharCounter counter = new CharCounter(request.getKalimat());
        CharAnalysis data = new CharAnalysis();
        data.setKalimat(request.getKalimat());
        data.setTotalHuruf(counter.getTotalHuruf());
        data.setCreatedAt(LocalDateTime.now());
        CharAnalysis saved = charAnalysisRepository.save(data);
        return CharAnalysisResponse.from(saved, counter.toMap());
    }

    public CharAnalysisResponse cariById() {
        return cariById(null);
    }

    @Override
    public CharAnalysisResponse cariById(Long id) {
        return charAnalysisRepository.findById(id)
                .map(this::toResponse) // count frekuensi lewat charcounter kalo gk ada return null
                .orElse(null);
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

    private CharAnalysisResponse toResponse(CharAnalysis data) {
        CharCounter counter = new CharCounter(data.getKalimat());
        return CharAnalysisResponse.from(data, counter.toMap());
    }


}
