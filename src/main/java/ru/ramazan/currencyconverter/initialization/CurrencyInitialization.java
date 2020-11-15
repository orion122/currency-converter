package ru.ramazan.currencyconverter.initialization;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.ramazan.currencyconverter.data.entity.Currency;
import ru.ramazan.currencyconverter.data.model.cbr.CbrCurrency;
import ru.ramazan.currencyconverter.data.model.cbr.CbrExchangeRates;
import ru.ramazan.currencyconverter.repository.CurrencyRepository;
import ru.ramazan.currencyconverter.service.CbrService;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@Component
@RequiredArgsConstructor
public class CurrencyInitialization {

    private final CbrService cbrService;
    private final CurrencyRepository currencyRepository;

    @PostConstruct
    public void postConstruct() {
        CbrExchangeRates cbrExchangeRates = cbrService.getExchangeRates();
        saveCurrencies(cbrExchangeRates);
    }

    private void saveCurrencies(CbrExchangeRates cbrExchangeRates) {
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
