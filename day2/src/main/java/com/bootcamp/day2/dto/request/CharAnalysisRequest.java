package com.bootcamp.day2.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Valid
public class CharAnalysisRequest {

    @NotBlank(message = "Kalimat wajib isi")
    @Size(max = 255, message = "Kalimat wajib isi sampai 255 character")
    private String kalimat;
}
