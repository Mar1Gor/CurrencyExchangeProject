package pl.bank.bankAccountProj.dto;

import lombok.Data;

@Data
public class NBPExchangeRatesDto {

    private String no;
    private String effectiveDate;
    private Double mid;

    public NBPExchangeRatesDto(String no, String effectiveDate, Double mid) {
        this.no = no;
        this.effectiveDate = effectiveDate;
        this.mid = mid;
    }


}
