package ru.ramazan.currencyconverter.data.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
public class Currency {
    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long numCode;

    @Column(nullable = false)
    private String charCode;

    @Column(nullable = false)
    private Long nominal;

    @Column(nullable = false)
    private LocalDate updateDate;

    @Column(nullable = false, precision = 9, scale = 5)
    private BigDecimal rate;
}
