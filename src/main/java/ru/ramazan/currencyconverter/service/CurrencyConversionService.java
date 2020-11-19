package ru.ramazan.currencyconverter.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.ramazan.currencyconverter.data.entity.Currency;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyConversionService {

    private static final int SCALE = 5;
    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;
    private final CurrencyConversionHistoryService currencyConversionHistoryService;

    /**
     * Конвертирует из одной валюты в другую
     * @param fromCurrency - конвертируемая валюта
     * @param toCurrency - валюта, в которую происходит конвертация
     * @param sum - количество переводимых средств
     * @return - возвращает результат конвертации (количество переводенных средств)
     */
    public BigDecimal convert(Currency fromCurrency, Currency toCurrency, BigDecimal sum) {
        BigDecimal ratesRatio = calculateRatesRatio(fromCurrency, toCurrency);
        currencyConversionHistoryService.saveConversion(fromCurrency, toCurrency, sum, ratesRatio);
        log.info("Произведена конвертация из '{}' в '{}'", fromCurrency.getName(), toCurrency.getName());
        return sum.multiply(ratesRatio);
    }

    /**
     * Рассчитывает курс одной валюты относительно другой
     * @param fromCurrency - конвертируемая валюта
     * @param toCurrency - валюта, в которую происходит конвертация
     * @return - курс
     */
    private BigDecimal calculateRatesRatio(Currency fromCurrency, Currency toCurrency) {
        BigDecimal fromRate = fromCurrency.getRate().divide(new BigDecimal(fromCurrency.getNominal()), SCALE, ROUNDING_MODE);
        BigDecimal toRate = toCurrency.getRate().divide(new BigDecimal(toCurrency.getNominal()), SCALE, ROUNDING_MODE);

        return fromRate.divide(toRate, ROUNDING_MODE);
    }
}
