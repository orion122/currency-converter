package ru.ramazan.currencyconverter.data.model.output;

import lombok.Data;
import ru.ramazan.currencyconverter.data.entity.ConversionHistory;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ConversionHistoryOutputModel {
    private Long id;
    private String fromCurrency;
    private String toCurrency;
    private BigDecimal sum;
    private BigDecimal rate;
    private LocalDate date;

    public ConversionHistoryOutputModel(ConversionHistory entity) {
        this.id = entity.getId();
        this.fromCurrency = entity.getFromCurrency().getName();
        this.toCurrency = entity.getToCurrency().getName();
        this.sum = entity.getSum();
        this.rate = entity.getRate();
        this.date = entity.getDate();
    }
}
