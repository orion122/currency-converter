package ru.ramazan.currencyconverter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ramazan.currencyconverter.data.entity.Currency;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class CurrencyConversionService {

    private static final int SCALE = 4;
    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;
    private final ConversionHistoryService conversionHistoryService;

    public BigDecimal convert(Currency fromCurrency, Currency toCurrency, BigDecimal sum) {
        BigDecimal ratesRatio = calculateRatesRatio(fromCurrency, toCurrency);
        conversionHistoryService.saveConversion(fromCurrency, toCurrency, sum, ratesRatio);
        return sum.multiply(ratesRatio);
    }

    private BigDecimal calculateRatesRatio(Currency fromCurrency, Currency toCurrency) {
        BigDecimal fromRate = fromCurrency.getRate().divide(new BigDecimal(fromCurrency.getNominal()), SCALE, ROUNDING_MODE);
        BigDecimal toRate = toCurrency.getRate().divide(new BigDecimal(toCurrency.getNominal()), SCALE, ROUNDING_MODE);

        return fromRate.divide(toRate, ROUNDING_MODE);
    }
}
