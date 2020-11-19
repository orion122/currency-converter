package ru.ramazan.currencyconverter.data.model.output;

import lombok.AllArgsConstructor;
import ru.ramazan.currencyconverter.data.entity.Currency;

import java.math.BigDecimal;

@AllArgsConstructor
public class ConversionStatistic {
    private final Currency fromCurrency;
    private final Currency toCurrency;
    private final Double averageRate;
    private final BigDecimal sum;
}
