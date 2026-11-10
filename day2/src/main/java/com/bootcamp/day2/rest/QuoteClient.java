package com.bootcamp.day2.rest;

import com.bootcamp.day2.dto.feign.QuoteResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient (name = "quoteClient", url = "${quote.api.url}")
public interface QuoteClient {

    @GetMapping("/quotes/random")
    QuoteResponse getQuote();
}
