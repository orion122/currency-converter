package ru.ramazan.currencyconverter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ramazan.currencyconverter.data.entity.Currency;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, String> {
}
