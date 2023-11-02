package com.topsquad.shortenservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Data
@Getter
@Setter
@Table(name = "urls", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"short_code"})
})
@AllArgsConstructor
@NoArgsConstructor
public class ShortURL {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "original_url", nullable = false)
    private String originalUrl;

    @Column(name = "short_code",nullable = false, unique = true)
    private String shortCode;

    @Column(name = "created_by_id", nullable = false)
    private Integer createdById;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created_at;

}
