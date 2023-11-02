package com.topsquad.shortenservice.model;

import lombok.Data;

@Data
public class ShortenRequest {
    private String url;
    private String token;
}
