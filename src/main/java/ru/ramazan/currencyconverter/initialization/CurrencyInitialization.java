package ru.ramazan.currencyconverter.initialization;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.ramazan.currencyconverter.service.CurrencyService;

import javax.annotation.PostConstruct;

@Slf4j
@Component
@RequiredArgsConstructor
public class CurrencyInitialization {

    private final CurrencyService currencyService;

    @PostConstruct
    public void postConstruct() {
        currencyService.updateOrCreateCurrencies();
    }
}
