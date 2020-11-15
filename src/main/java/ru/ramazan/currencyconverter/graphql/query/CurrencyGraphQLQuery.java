package ru.ramazan.currencyconverter.graphql.query;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ramazan.currencyconverter.data.entity.Currency;
import ru.ramazan.currencyconverter.service.CurrencyService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CurrencyGraphQLQuery implements GraphQLQueryResolver {

    private final CurrencyService currencyService;

    public List<Currency> getCurrencies() {
        return currencyService.getCurrencies();
    }

    public Optional<Currency> getCurrency(String id) {
        return currencyService.getCurrency(id);
    }

    public BigDecimal convert(String fromCurrencyId, String toCurrencyId, BigDecimal sum) {
        return currencyService.convert(fromCurrencyId, toCurrencyId, sum);
    }
}
