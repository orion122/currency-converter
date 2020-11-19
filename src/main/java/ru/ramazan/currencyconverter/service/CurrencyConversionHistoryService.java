package ru.ramazan.currencyconverter.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.ramazan.currencyconverter.data.entity.ConversionHistory;
import ru.ramazan.currencyconverter.data.entity.Currency;
import ru.ramazan.currencyconverter.data.model.output.ConversionStatistic;
import ru.ramazan.currencyconverter.graphql.exception.InvalidDateFormatException;
import ru.ramazan.currencyconverter.repository.ConversionHistoryRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyConversionHistoryService {

    private final ConversionHistoryRepository conversionHistoryRepository;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    /**
     * Сохраняет произведенную конвертацию
     *
     * @param fromCurrency - конвертируемая валюта
     * @param toCurrency   - валюта, в которую происходила конвертация
     * @param sum          - количество переведенных средств
     * @param rate         - курс одной валюты относительно другой
     */
    public void saveConversion(Currency fromCurrency, Currency toCurrency, BigDecimal sum, BigDecimal rate) {
        ConversionHistory conversionHistory = new ConversionHistory();
        conversionHistory.setFromCurrency(fromCurrency);
        conversionHistory.setToCurrency(toCurrency);
        conversionHistory.setSum(sum);
        conversionHistory.setRate(rate);

        conversionHistoryRepository.save(conversionHistory);
    }

    /**
     * @return - статистика произведенных конвертаций за последние 7 дней
     */
    public List<ConversionStatistic> getConversionStatistics() {
        return conversionHistoryRepository.getConversionStatistics();
    }

    /**
     *
     * @param afterDateInString - дата (в виде строки), по которой фильтруется история конвертаций
     * @return - история произведенных конвертаций после с выбранной даты (afterDateInString)
     */
    public List<ConversionHistory> getConversionHistory(String afterDateInString) {
        try {
            LocalDate afterDate = LocalDate.parse(afterDateInString, formatter);
            return conversionHistoryRepository.findByDateGreaterThan(afterDate);
        } catch (DateTimeParseException e) {
            log.error("Неправильный формат даты", e);
            throw new InvalidDateFormatException();
        }
    }
}
