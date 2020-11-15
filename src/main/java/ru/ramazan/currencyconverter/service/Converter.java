package ru.ramazan.currencyconverter.service;

import org.springframework.stereotype.Service;
import ru.ramazan.currencyconverter.data.entity.Currency;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class Converter {

    public BigDecimal convert(Currency fromCurrency, Currency toCurrency, BigDecimal sum) {
        BigDecimal fromRate = fromCurrency.getRate().divide(new BigDecimal(fromCurrency.getNominal()), 4, RoundingMode.HALF_UP);
        BigDecimal toRate = toCurrency.getRate().divide(new BigDecimal(toCurrency.getNominal()), 4, RoundingMode.HALF_UP);

        BigDecimal ratesRatio = fromRate.divide(toRate, RoundingMode.HALF_UP);
        return sum.multiply(ratesRatio);
    }
}
