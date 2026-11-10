package com.bootcamp.day2.rest;

import com.bootcamp.day2.dto.feign.QuoteResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient (name = "quoteClient", url = "${quote.api.url}")
public interface QuoteClient {

    @GetMapping("/quotes/random")
    QuoteResponse getQuote();

    @GetMapping("/quotes/random/{length}")
    List<QuoteResponse> getQuotes(@PathVariable("length")int length);
}
