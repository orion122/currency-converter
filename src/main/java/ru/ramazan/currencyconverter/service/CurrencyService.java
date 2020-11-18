package ru.ramazan.currencyconverter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ramazan.currencyconverter.data.entity.Currency;
import ru.ramazan.currencyconverter.data.model.cbr.CbrCurrency;
import ru.ramazan.currencyconverter.data.model.cbr.CbrExchangeRates;
import ru.ramazan.currencyconverter.repository.CurrencyRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

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

    @Transactional
    public BigDecimal convert(String fromCurrencyId, String toCurrencyId, BigDecimal sum) {
        Currency fromCurrency = currencyRepository.getOne(fromCurrencyId);
        Currency toCurrency = currencyRepository.getOne(toCurrencyId);

        LocalDate today = LocalDate.now();

        if (!fromCurrency.getUpdateDate().equals(today) || !toCurrency.getUpdateDate().equals(today)) {
            updateOrCreateCurrencies();
        }

        return conversionService.convert(fromCurrency, toCurrency, sum);
    }

    public void updateOrCreateCurrencies() {
        CbrExchangeRates cbrExchangeRates = cbrService.getExchangeRates();

        LocalDate exchangeRatesDate = cbrExchangeRates.getDate();

        List<Currency> currencies = cbrExchangeRates.getCbrCurrencies().stream()
                .map(cbrCurrency -> buildCurrency(cbrCurrency, exchangeRatesDate))
                .collect(toList());

        currencyRepository.saveAll(currencies);
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
