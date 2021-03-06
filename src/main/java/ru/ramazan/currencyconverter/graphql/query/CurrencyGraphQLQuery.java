package ru.ramazan.currencyconverter.graphql.query;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ramazan.currencyconverter.data.entity.Currency;
import ru.ramazan.currencyconverter.data.model.output.ConversionHistoryOutputModel;
import ru.ramazan.currencyconverter.data.model.output.ConversionStatistic;
import ru.ramazan.currencyconverter.service.CurrencyConversionHistoryService;
import ru.ramazan.currencyconverter.service.CurrencyService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class CurrencyGraphQLQuery implements GraphQLQueryResolver {

    private final CurrencyService currencyService;
    private final CurrencyConversionHistoryService currencyConversionHistoryService;

    public List<Currency> getCurrencies() {
        return currencyService.getCurrencies();
    }

    public Optional<Currency> getCurrency(String id) {
        return currencyService.getCurrency(id);
    }

    public BigDecimal convert(String fromCurrencyId, String toCurrencyId, BigDecimal sum) {
        return currencyService.convert(fromCurrencyId, toCurrencyId, sum);
    }

    public List<ConversionStatistic> getConversionStatistics() {
        return currencyConversionHistoryService.getConversionStatistics();
    }

    public List<ConversionHistoryOutputModel> getConversionHistory(String afterDateInString) {
        return currencyConversionHistoryService.getConversionHistory(afterDateInString).stream()
                .map(ConversionHistoryOutputModel::new)
                .collect(toList());
    }
}
