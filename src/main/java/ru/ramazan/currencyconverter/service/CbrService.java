package ru.ramazan.currencyconverter.service;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.ramazan.currencyconverter.data.model.cbr.CbrExchangeRates;
import ru.ramazan.currencyconverter.exception.CbrRequestException;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class CbrService {

    @Value("${cbrf_url}")
    private String url;

    private final CloseableHttpClient httpClient;
    private static final XmlMapper MAPPER = new XmlMapper();

    public CbrExchangeRates getExchangeRates() {
        HttpGet request = new HttpGet(url);

        try (CloseableHttpResponse response = httpClient.execute(request)) {

            HttpEntity entity = response.getEntity();
            String xml = EntityUtils.toString(entity);
            return MAPPER.readValue(xml, CbrExchangeRates.class);

        } catch (IOException e) {
            // fixme: дублируются строки
            log.error("Невозможно получить данные с сайта ЦБ", e);
             throw new CbrRequestException("Невозможно получить данные с сайта ЦБ");
        }
    }
}
