package ru.ramazan.currencyconverter.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ramazan.currencyconverter.data.entity.Currency;
import ru.ramazan.currencyconverter.data.model.cbr.CbrCurrency;
import ru.ramazan.currencyconverter.data.model.cbr.CbrExchangeRates;
import ru.ramazan.currencyconverter.graphql.exception.CurrencyNotFoundException;
import ru.ramazan.currencyconverter.repository.CurrencyRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyService {

    private final CbrService cbrService;
    private final CurrencyRepository currencyRepository;
    private final CurrencyConversionService conversionService;

    public List<Currency> getCurrencies() {
        return currencyRepository.findAll();
    }

    public Optional<Currency> getCurrency(String id) {
        return currencyRepository.findById(id);
    }

    /**
     * Конвертирует из одной валюты в другую
     * @param fromCurrencyId - id конвертируемой валюты
     * @param toCurrencyId - id валюты, в которую происходит конвертация
     * @param sum - количество переводимых средств
     * @return - результат конвертации (количество переводенных средств)
     */
    @Transactional
    public BigDecimal convert(String fromCurrencyId, String toCurrencyId, BigDecimal sum) {
        Currency fromCurrency = getCurrencyOrThrow(fromCurrencyId, "fromCurrencyId");
        Currency toCurrency = getCurrencyOrThrow(toCurrencyId, "toCurrencyId");

        LocalDate today = LocalDate.now();

        if (!fromCurrency.getUpdateDate().equals(today) || !toCurrency.getUpdateDate().equals(today)) {
            updateOrCreateCurrencies();

            fromCurrency = getCurrencyOrThrow(fromCurrencyId, "fromCurrencyId");
            toCurrency = getCurrencyOrThrow(toCurrencyId, "toCurrencyId");
        }

        return conversionService.convert(fromCurrency, toCurrency, sum);
    }

    private Currency getCurrencyOrThrow(String currencyId, String fieldName) {
        return currencyRepository.findById(currencyId)
                .orElseThrow(() -> new CurrencyNotFoundException("Не найдена валюта с таким id", fieldName));
    }

    /**
     * Достаются курсы валют с сайта ЦБРФ и сохраняются в БД или обновляются, если уже существовали.
     */
    public void updateOrCreateCurrencies() {
        CbrExchangeRates cbrExchangeRates = cbrService.getExchangeRates();
        log.info("Данные с сайта ЦБ распарсены");

        LocalDate exchangeRatesDate = cbrExchangeRates.getDate();

        List<Currency> currencies = cbrExchangeRates.getCbrCurrencies().stream()
                .map(cbrCurrency -> buildCurrency(cbrCurrency, exchangeRatesDate))
                .collect(toList());

        currencyRepository.saveAll(currencies);
        log.info("Курсы валют сохранены в БД");
    }

    private Currency buildCurrency(CbrCurrency cbrCurrency, LocalDate exchangeRatesDate) {
        Currency currency = new Currency();
        currency.setId(cbrCurrency.getId());
        currency.setNumCode(cbrCurrency.getNumCode());
        currency.setCharCode(cbrCurrency.getCharCode());
        currency.setName(cbrCurrency.getName());
        currency.setNominal(cbrCurrency.getNominal());
        currency.setRate(cbrCurrency.getRate());
        currency.setUpdateDate(exchangeRatesDate);

        return currency;
    }
}
