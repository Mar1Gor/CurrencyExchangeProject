package pl.bank.bankAccountProj.dto;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class CreateUserDto {

    private String name;
    private String surname;
    private Long pesel;
    private BigDecimal startBalance;


    public CreateUserDto(String name, String surname, Long pesel) {
        this.name = name;
        this.surname = surname;
        this.pesel = pesel;
        this.startBalance = BigDecimal.valueOf(0);
    }

    public CreateUserDto(String name, String surname, Long pesel, BigDecimal startBalance) {
        this.name = name;
        this.surname = surname;
        this.pesel = pesel;
        this.startBalance = startBalance;
    }

    public CreateUserDto() {
    }
}
