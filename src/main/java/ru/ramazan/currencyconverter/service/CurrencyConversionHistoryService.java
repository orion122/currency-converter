package ru.ramazan.currencyconverter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ramazan.currencyconverter.data.entity.ConversionHistory;
import ru.ramazan.currencyconverter.data.entity.Currency;
import ru.ramazan.currencyconverter.data.model.ConversionStatistic;
import ru.ramazan.currencyconverter.repository.ConversionHistoryRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyConversionHistoryService {

    private final ConversionHistoryRepository conversionHistoryRepository;

    public void saveConversion(Currency fromCurrency, Currency toCurrency, BigDecimal sum, BigDecimal rate) {
        ConversionHistory conversionHistory = new ConversionHistory();
        conversionHistory.setFromCurrency(fromCurrency);
        conversionHistory.setToCurrency(toCurrency);
        conversionHistory.setSum(sum);
        conversionHistory.setRate(rate);

        conversionHistoryRepository.save(conversionHistory);
    }

    public List<ConversionStatistic> getConversionStatistics() {
        return conversionHistoryRepository.getConversionStatistics(LocalDate.now().minusDays(7));
    }
}
