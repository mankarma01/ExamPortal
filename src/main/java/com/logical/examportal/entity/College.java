package com.logical.examportal.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class College {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long collegeId;
    private String collegeName;
    private String collegeAddress;
    private LocalDateTime dateTime;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH} )
    @JoinColumn(name = "city_id", referencedColumnName = "city_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private City city;

    @Transient
    private Long cityId;

    private Boolean isActive = true;
}
