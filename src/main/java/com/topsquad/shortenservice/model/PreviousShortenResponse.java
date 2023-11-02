package com.topsquad.shortenservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

import java.util.Date;

@Data
public class PreviousShortenResponse {
    private Integer id;
    private String originalUrl;
    private String shortCode;
    private Date created_at;
}
