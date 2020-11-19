package ru.ramazan.currencyconverter.initialization;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ramazan.currencyconverter.service.CurrencyService;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class CurrencyInitialization {

    private final CurrencyService currencyService;

    @PostConstruct
    public void postConstruct() {
        currencyService.updateOrCreateCurrencies();
    }
}
