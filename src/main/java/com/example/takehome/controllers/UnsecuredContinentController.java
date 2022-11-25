package com.example.takehome.controllers;

import com.example.takehome.models.Continent;
import com.example.takehome.services.QueryGraphqlService;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

@RestController
@Slf4j
public class UnsecuredContinentController {

    @Autowired
    private QueryGraphqlService queryGraphqlService;
    private final Bucket bucket;

    public UnsecuredContinentController() {
        Bandwidth limit = Bandwidth.classic(5, Refill.greedy(5, Duration.ofSeconds(1)));
        this.bucket = Bucket4j.builder()
                .addLimit(limit)
                .build();
    }

    @PostMapping(value = "/unsecured_countries_in_same_continent", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    List<Continent> getUnsecuredCountriesInContinents(@RequestBody String countriesJsonStr) throws IOException, JSONException {
        log.info("countriesJson = " + countriesJsonStr);
        return queryGraphqlService.getCountryDetails(new JSONObject(countriesJsonStr));
    }
}
