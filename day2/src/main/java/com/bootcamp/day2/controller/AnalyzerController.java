package com.bootcamp.day2.controller;

import com.bootcamp.day2.dto.request.CharAnalysisRequest;
import com.bootcamp.day2.dto.response.BaseResponse;
import com.bootcamp.day2.dto.response.CharAnalysisResponse;
import com.bootcamp.day2.service.CharAnalysisService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;


import java.util.List;
@Slf4j //simple logging facade for java , cara untuk trap log di sb biar ke print di console
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/api")
public class AnalyzerController {

    private final CharAnalysisService charAnalysisService;

    @GetMapping("/find")
    public BaseResponse<List<CharAnalysisResponse>> cari(@RequestParam(required = false) String kalimat) {
        return BaseResponse.ok(charAnalysisService.cari(kalimat));
    }

    @PostMapping("/v1")
    public BaseResponse<CharAnalysisResponse> simpan (
            @Valid
            @RequestBody CharAnalysisRequest request,
            @RequestHeader(value = "X-Request-Id", required = false) String requestId){ //header boleh tidak dikirim
        log.info("X-Request-Id: {}, kalimat: {}.", requestId, request.getKalimat());
        return BaseResponse.ok(charAnalysisService.simpan(request));
    }

    @GetMapping("/v1/{id}")
    public BaseResponse<CharAnalysisResponse> cariById(@PathVariable Long id) {
        CharAnalysisResponse hasil = charAnalysisService.cariById(id);
        return BaseResponse.ok(hasil);
    }

    @GetMapping("/v1/minimal")
    public BaseResponse<List<CharAnalysisResponse>> cariMinimal(@RequestParam int min) {
        return BaseResponse.ok(charAnalysisService.cariMinimal(min));
    }

    @GetMapping("/v1/terbaru")
    public BaseResponse<List<CharAnalysisResponse>> cariTerbaru() {
        return BaseResponse.ok(charAnalysisService.cariTerbaru());
    }

    @PostMapping ("/from-quote")
    public BaseResponse<CharAnalysisResponse> fromQuote() {
        return BaseResponse.ok(charAnalysisService.analisisDariQuote());
    }
}
