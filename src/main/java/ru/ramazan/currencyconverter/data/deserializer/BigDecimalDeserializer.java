package ru.ramazan.currencyconverter.data.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * Позволяет использовать запятую при десериализации нецелочисленного поля в XML
 */
public class BigDecimalDeserializer extends JsonDeserializer<BigDecimal> {

    @Override
    public BigDecimal deserialize(JsonParser parser, DeserializationContext context) throws IOException {

        String rawString = parser.getText();

        if (rawString.contains(",")) {
            rawString = rawString.replace(",", ".");
        }

        return new BigDecimal(rawString);
    }
}
