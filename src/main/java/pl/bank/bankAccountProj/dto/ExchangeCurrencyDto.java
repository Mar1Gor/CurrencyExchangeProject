package pl.bank.bankAccountProj.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ExchangeCurrencyDto {
    private String accountId;
    private BigDecimal finalBalancePln;
    private BigDecimal finalBalanceUsd;

    @Builder
    public ExchangeCurrencyDto(String accountId, BigDecimal finalBalancePln, BigDecimal finalBalanceUsd) {
        this.accountId = accountId;
        this.finalBalancePln = finalBalancePln;
        this.finalBalanceUsd = finalBalanceUsd;
    }


}
