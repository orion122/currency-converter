package ru.ramazan.currencyconverter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ramazan.currencyconverter.data.entity.Currency;
import ru.ramazan.currencyconverter.repository.CurrencyRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CurrencyService {

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
        Currency fromCurrency = currencyRepository.getOne(fromCurrencyId); // fixme: findById with exception
        Currency toCurrency = currencyRepository.getOne(toCurrencyId); // fixme: findById with exception

        return conversionService.convert(fromCurrency, toCurrency, sum);
    }
}
