package pl.bank.bankAccountProj.dto;

import lombok.Data;

import java.util.List;

@Data
public class NBPExchangeDto {

    private String table;
    private String currency;
    private String code;
    private List<NBPExchangeRatesDto> rates;

    public NBPExchangeDto(String table, String currency, String code, List<NBPExchangeRatesDto> rates) {
        this.table = table;
        this.currency = currency;
        this.code = code;
        this.rates = rates;
    }


}
