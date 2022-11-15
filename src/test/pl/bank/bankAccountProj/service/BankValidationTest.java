package pl.bank.bankAccountProj.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BankValidationTest {

    @Test
    @DisplayName("Nbp proper usd course test")
    void checkWorkHoursFormat() {
        Assertions.assertNotEquals(0,
                new NbpConnectionService()
                        .getTodaysTradePlnValue("USD", "https://api.nbp.pl/api/exchangerates/rates/a/"));
    }

}