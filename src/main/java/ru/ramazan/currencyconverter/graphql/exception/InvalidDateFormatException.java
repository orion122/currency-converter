package ru.ramazan.currencyconverter.graphql.exception;

public class InvalidDateFormatException extends GraphQLBaseException {
    public InvalidDateFormatException() {
        super("Невалидный формат даты");
    }
}
