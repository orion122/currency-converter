package ru.ramazan.currencyconverter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.ramazan.currencyconverter.data.entity.ConversionHistory;
import ru.ramazan.currencyconverter.data.model.ConversionStatistic;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ConversionHistoryRepository extends JpaRepository<ConversionHistory, Long> {

    @Query("SELECT " +
            "NEW ru.ramazan.currencyconverter.data.model.ConversionStatistic(ch.fromCurrency, ch.toCurrency, AVG(ch.rate), SUM(ch.sum)) " +
            "FROM ConversionHistory ch " +
            "WHERE ch.date >= :sevenDaysAgo " +
            "GROUP BY ch.fromCurrency, ch.toCurrency")
    List<ConversionStatistic> getConversionStatistic(LocalDate sevenDaysAgo);
}
