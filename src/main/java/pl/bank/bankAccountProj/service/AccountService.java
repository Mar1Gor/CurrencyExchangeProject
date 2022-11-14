package pl.bank.bankAccountProj.service;

import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bank.bankAccountProj.dto.ExchangeCurrencyDto;
import pl.bank.bankAccountProj.dto.GetAccountInfoDto;
import pl.bank.bankAccountProj.entity.Account;
import pl.bank.bankAccountProj.entity.SubAccount;
import pl.bank.bankAccountProj.repository.AccountRepository;
import pl.bank.bankAccountProj.repository.SubAccountRepository;
import pl.bank.bankAccountProj.repository.UserRepository;
import pl.bank.bankAccountProj.util.DateUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class AccountService {


    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final NbpConnectionService nbpConnectionService;

    private final SubAccountRepository subAccountRepository;

    @Value("${availableCurrencies:PLN,USD}")
    private String availableCurrencies;
    @Value("${USDspread:1}")
    private Integer USDspread;
    @Value("${PLNspread:1}")//todo do poprawy logika
    private Integer PLNspread;
    @Autowired
    public AccountService(UserRepository userRepository, AccountRepository accountRepository, NbpConnectionService nbpConnectionService, SubAccountRepository subAccountRepository) {

        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.nbpConnectionService = nbpConnectionService;
        this.subAccountRepository = subAccountRepository;
    }

    public GetAccountInfoDto getAccountInfo(String accountId) {
        Account account = accountRepository.getById(accountId);
        return mapAccount(account);

    }

    private GetAccountInfoDto mapAccount(Account account) {
        return GetAccountInfoDto.builder()
                .pesel(account.getUser().getPesel())
                .balancePln(account.getBalancePln())
                .balanceUsd(account.getBalanceUsd())
                .lastOperationDate(account.getModifyDate())
                .build();
    }
    @Transactional
    public ExchangeCurrencyDto currencyExchange(String currencyFrom, String currencyTo, String accountId, BigDecimal amount) {
        Account account = accountRepository.getById(accountId);
        if (account == null) {
            //blad - nieznaleziono rachunku
        }
        validateCurrencyExchange(currencyFrom, currencyTo, account, amount);
        // dla zalozenia ze operujemy tylko na wymianie z PLN tak jak w polskich bankach
        Double plnTradeValue = nbpConnectionService.getTodaysTradePlnValue(currencyFrom == "PLN" ? currencyTo : currencyFrom);
        if (plnTradeValue == 0) {
            //blad - blad pobierania danych z nbp
        }
        if (currencyFrom == "PLN") {
            doExchange(true, amount, account, plnTradeValue);
        } else {
            doExchange(false, amount, account, plnTradeValue);
        }
        return ExchangeCurrencyDto
                .builder()
                .accountId(accountId)
                .finalBalancePln(account.getBalancePln())
                .finalBalanceUsd(account.getBalanceUsd())
                .build();
    }

    private void doExchange(boolean fromPLN, BigDecimal amount, Account account, Double nbpTradeValue) {
        BigDecimal plnAmount = account.getBalancePln();
        BigDecimal usdAmount = account.getBalanceUsd();
        if (fromPLN) {
            plnAmount = plnAmount.subtract(amount);
            usdAmount = amount.multiply(BigDecimal.valueOf(USDspread)).divide(BigDecimal.valueOf(nbpTradeValue));
        } else {
            usdAmount = usdAmount.subtract(amount);
            plnAmount = amount.multiply(BigDecimal.valueOf(PLNspread)).multiply(BigDecimal.valueOf(nbpTradeValue));
        }
        account.setBalancePln(plnAmount);
        account.setBalanceUsd(usdAmount);
        account.setModifyDate(DateUtils.getCurrTime());
        accountRepository.save(account);
    }

    private void validateCurrencyExchange(String currencyFrom, String currencyTo, Account account, BigDecimal amount) {

        List<String> ac = availableCurrencies == null ? Collections.emptyList() : List.of(availableCurrencies.split(","));
        if (!ac.contains(currencyTo) || !ac.contains(currencyFrom) ) {
            //blad - niemozliwa do zrealizowania transakcja
        }
        if (currencyFrom == "PLN" && currencyTo == "USD" && account.getBalancePln().compareTo(amount) == -1) {
            //blad - za malo srodkow na koncie
        }
        if (currencyFrom == "USD" && currencyTo == "PLN" && account.getBalanceUsd().compareTo(amount) == -1) {
            //blad - za malo srodkow na koncie
        }

    }
}
