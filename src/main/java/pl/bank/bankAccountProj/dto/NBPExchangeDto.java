package pl.bank.bankAccountProj.dto;

import java.util.List;

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

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<NBPExchangeRatesDto> getRates() {
        return rates;
    }

    public void setRates(List<NBPExchangeRatesDto> rates) {
        this.rates = rates;
    }
}
