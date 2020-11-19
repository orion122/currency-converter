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
import ru.ramazan.currencyconverter.graphql.exception.CbrRequestException;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@RequiredArgsConstructor
public class CbrService {

    public static final String DATE_PATTERN = "dd/MM/yyyy";

    @Value("${cbrf_url_template}")
    private String cbrfUrlTemplate;

    private final CloseableHttpClient httpClient;
    private static final XmlMapper MAPPER = new XmlMapper();

    public CbrExchangeRates getExchangeRates() {
        String todayDate = LocalDate.now().format(DateTimeFormatter.ofPattern(DATE_PATTERN));
        String url = String.format(cbrfUrlTemplate, todayDate);
        HttpGet request = new HttpGet(url);

        try (CloseableHttpResponse response = httpClient.execute(request)) {

            HttpEntity entity = response.getEntity();
            String xml = EntityUtils.toString(entity);
            log.info("Получены данные с сайта ЦБ");
            return MAPPER.readValue(xml, CbrExchangeRates.class);

        } catch (IOException e) {
            log.error("Невозможно получить данные с сайта ЦБ", e);
            throw new CbrRequestException();
        }
    }
}
