package pl.bank.bankAccountProj.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.bank.bankAccountProj.dto.ExchangeCurrencyDto;
import pl.bank.bankAccountProj.dto.GetAccountInfoDto;
import pl.bank.bankAccountProj.entity.Account;
import pl.bank.bankAccountProj.entity.SubAccount;
import pl.bank.bankAccountProj.exception.ApiException;
import pl.bank.bankAccountProj.repository.AccountRepository;
import pl.bank.bankAccountProj.repository.SubAccountRepository;
import pl.bank.bankAccountProj.util.DateUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final NbpConnectionService nbpConnectionService;
    private final SubAccountRepository subAccountRepository;

    @Value("${availableCurrencies:PLN,USD}")
    private String availableCurrencies;
    @Value("${nbpApiUrl:https://api.nbp.pl/api/exchangerates/rates/a/}")
    private String nbpApiUrl;
    @Value("${USDspread:1}")
    private Integer USDspread;
    @Value("${PLNspread:1}")
    private Integer PLNspread;

    @Autowired
    public AccountService(AccountRepository accountRepository, NbpConnectionService nbpConnectionService, SubAccountRepository subAccountRepository) {
        this.accountRepository = accountRepository;
        this.nbpConnectionService = nbpConnectionService;
        this.subAccountRepository = subAccountRepository;
    }

    public GetAccountInfoDto getAccountInfo(String accountId) {
        try {
            Account account = accountRepository.getById(accountId);
            if (account == null) {
                return null;
            }
            return mapAccount(account);
        } catch (Exception e) {
            log.error("Exception caught at getting account info:", e);
            throw new ApiException("500", "(8777)");
        }
    }

    private GetAccountInfoDto mapAccount(Account account) {
        return GetAccountInfoDto.builder()
                .pesel(account.getBankUser().getPesel())
                .balances(getBalanceFromSubAccount(account))
                .lastOperationDate(account.getModifyDate())
                .build();
    }

    @Transactional
    public ExchangeCurrencyDto currencyExchange(String currencyFrom, String currencyTo, String accountId, BigDecimal amount) {
        try {
            Account account = accountRepository.getById(accountId);
            if (account == null) {
                //blad - nieznaleziono rachunku
            }
            List<SubAccount> subAccountList = (List<SubAccount>) account.getSubAccountCollection();
            validateCurrencyExchange(currencyFrom, currencyTo, account, amount);
            // zakladam, ze operujemy tylko na wymianie z PLN tak jak w polskich bankach
            Double plnTradeValue = nbpConnectionService.getTodaysTradePlnValue(currencyFrom == "PLN" ? currencyTo : currencyFrom, nbpApiUrl);
            if (plnTradeValue == 0) {
                //blad - blad pobierania danych z nbp
            }
            if (currencyFrom == "PLN") {
                doExchange(true, amount, account, subAccountList, plnTradeValue);
            } else {
                doExchange(false, amount, account, subAccountList, plnTradeValue);
            }
            return ExchangeCurrencyDto
                    .builder()
                    .accountId(accountId)
                    .finalBalanceMap(getBalanceFromSubAccount(account))
                    .build();
        } catch (Exception e) {
            log.error("Exception caught at currency exchange:", e);
            throw new ApiException("500", "(888)");
        }
    }

    private HashMap<String, BigDecimal> getBalanceFromSubAccount(Account account) {
        HashMap<String, BigDecimal> finalBalanceMap = new HashMap<>();
        account.getSubAccountCollection().forEach(
                subAcc -> finalBalanceMap.put(subAcc.getCurrency(), subAcc.getBalance())
        );
        return finalBalanceMap;
    }

    private void doExchange(boolean fromPLN, BigDecimal amount, Account account, List<SubAccount> subAccountList, Double nbpTradeValue) {
        SubAccount plnSubAccount = subAccountList.stream()
                .filter(sa -> Objects.equals(sa.getCurrency(), "PLN"))
                .findFirst().orElse(new SubAccount());
        SubAccount usdSubAccount = subAccountList.stream()
                .filter(sa -> Objects.equals(sa.getCurrency(), "USD"))
                .findFirst().orElse(new SubAccount());
        if (fromPLN) {
            plnSubAccount
                    .setBalance(plnSubAccount.getBalance().subtract(amount));
            usdSubAccount
                    .setBalance(amount.multiply(BigDecimal.valueOf(USDspread)).divide(BigDecimal.valueOf(nbpTradeValue)));
        } else {
            usdSubAccount
                    .setBalance(usdSubAccount.getBalance().subtract(amount));
            plnSubAccount
                    .setBalance(amount.multiply(BigDecimal.valueOf(PLNspread)).divide(BigDecimal.valueOf(nbpTradeValue)));
        }
        account.setModifyDate(DateUtils.getCurrTime());
        subAccountRepository.saveAll(subAccountList);
        accountRepository.save(account);
    }

    private void validateCurrencyExchange(String currencyFrom, String currencyTo, Account account, BigDecimal amount) {

        List<String> ac = availableCurrencies == null ? Collections.emptyList() : List.of(availableCurrencies.split(","));
        if (!ac.contains(currencyTo) || !ac.contains(currencyFrom)) {
            //blad - niemozliwa do zrealizowania transakcja
        }
        if (currencyFrom == "PLN" && currencyTo == "USD" && getBalanceOfCurrency(account, "PLN").compareTo(amount) == -1) {
            //blad - za malo srodkow na koncie
        }
        if (currencyFrom == "USD" && currencyTo == "PLN" && getBalanceOfCurrency(account, "USD").compareTo(amount) == -1) {
            //blad - za malo srodkow na koncie
        }

    }

    private BigDecimal getBalanceOfCurrency(Account account, String currency) {
        return account.getSubAccountCollection().stream()
                .filter(sa -> sa.getCurrency() == currency)
                .findFirst().orElseThrow()
                .getBalance();
    }
}
