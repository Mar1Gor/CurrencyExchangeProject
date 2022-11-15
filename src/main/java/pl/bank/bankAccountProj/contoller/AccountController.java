package pl.bank.bankAccountProj.contoller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.bank.bankAccountProj.dto.ExchangeCurrencyDto;
import pl.bank.bankAccountProj.dto.GetAccountInfoDto;
import pl.bank.bankAccountProj.service.AccountService;

import java.math.BigDecimal;

@Slf4j
@RestController
@CrossOrigin(value = "*", maxAge = 3600)
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/account/info")
    public ResponseEntity<GetAccountInfoDto> getAccountInfo(@RequestParam(name = "accId") String accountId) {//todo
        log.info("Start method getAccountInfo with param {}", accountId);
        GetAccountInfoDto accInfo = accountService.getAccountInfo(accountId);
        if (accInfo == null) {
            log.warn("End method getAccountInfo, account not found");
            return ResponseEntity.noContent().build();
        }
        log.info("End method getAccountInfo with params {}. Returning {}", accountId, accInfo);
        return ResponseEntity.ok(accInfo);
    }

    @GetMapping("/account/exchange/{from}/{to}")
    public ResponseEntity<ExchangeCurrencyDto> currencyExchange(@PathVariable("from") String currencyFrom,
                                                                @PathVariable("to") String currencyTo,
                                                                @RequestParam(name = "accId") String accountId,
                                                                @RequestParam(name = "amount") BigDecimal amount) {
        log.info("Start method getAccountInfo with params from = [{}], to = [{}], accId = [{}], amount = [{}]", currencyFrom,
                currencyTo, accountId, amount);
        ExchangeCurrencyDto returnMessage = accountService.currencyExchange(currencyFrom, currencyTo, accountId, amount);
        log.info("End method getAccountInfo for account = {}. Returning {}", accountId, returnMessage);
        return ResponseEntity.ok(returnMessage);
    }
}
