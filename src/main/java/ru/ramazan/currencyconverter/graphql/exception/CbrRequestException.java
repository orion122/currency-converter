package ru.ramazan.currencyconverter.graphql.exception;

public class CbrRequestException extends GraphQLBaseException {
    public CbrRequestException() {
        super("Невозможно получить данные с сайта ЦБ");
    }
}
