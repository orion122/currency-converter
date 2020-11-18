package ru.ramazan.currencyconverter.graphql.exception;

public class CurrencyNotFoundException extends GraphQLBaseException {
    public CurrencyNotFoundException(String message, String invalidField) {
        super(message, invalidField);
    }
}
