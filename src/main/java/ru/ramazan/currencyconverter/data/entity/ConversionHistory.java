package ru.ramazan.currencyconverter.data.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
public class ConversionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "from_currency_id", nullable = false)
    private Currency fromCurrency;

    @ManyToOne
    @JoinColumn(name = "to_currency_id", nullable = false)
    private Currency toCurrency;

    @Column(nullable = false, precision = 9, scale = 5)
    private BigDecimal sum;

    @Column(nullable = false, precision = 9, scale = 5)
    private BigDecimal rate;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDate date;
}
