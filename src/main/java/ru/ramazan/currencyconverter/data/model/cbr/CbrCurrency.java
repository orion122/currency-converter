package ru.ramazan.currencyconverter.data.model.cbr;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import ru.ramazan.currencyconverter.data.deserializer.BigDecimalDeserializer;

import java.math.BigDecimal;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CbrCurrency {
    @JacksonXmlProperty(localName = "ID")
    private String id;

    @JacksonXmlProperty(localName = "NumCode")
    private Long numCode;

    @JacksonXmlProperty(localName = "CharCode")
    private String charCode;

    @JacksonXmlProperty(localName = "Nominal")
    private Long nominal;

    @JacksonXmlProperty(localName = "Name")
    private String name;

    @JacksonXmlProperty(localName = "Value")
    @JsonDeserialize(using = BigDecimalDeserializer.class)
    private BigDecimal rate;
}
