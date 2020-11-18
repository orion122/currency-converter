package ru.ramazan.currencyconverter.graphql.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public abstract class GraphQLBaseException extends RuntimeException implements GraphQLError {

    private final String invalidField;

    public GraphQLBaseException(String message, String invalidField) {
        super(message);
        this.invalidField = invalidField;
    }

    public GraphQLBaseException(String message) {
        this(message, null);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @JsonIgnore
    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }

    @Override
    public ErrorType getErrorType() {
        return null;
    }

    @Override
    public Map<String, Object> getExtensions() {
        return Collections.singletonMap("invalidField", invalidField);
    }

    @JsonIgnore
    @Override
    public StackTraceElement[] getStackTrace() {
        return super.getStackTrace();
    }
}
