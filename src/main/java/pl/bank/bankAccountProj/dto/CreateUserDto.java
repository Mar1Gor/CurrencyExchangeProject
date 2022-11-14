package pl.bank.bankAccountProj.dto;

import java.math.BigDecimal;

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

    public BigDecimal getStartBalance() {
        return startBalance;
    }

    public void setStartBalance(BigDecimal startBalance) {
        this.startBalance = startBalance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Long getPesel() {
        return pesel;
    }

    public void setPesel(Long pesel) {
        this.pesel = pesel;
    }
}
