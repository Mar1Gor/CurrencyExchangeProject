package pl.bank.bankAccountProj.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bank.bankAccountProj.dto.CreateUserDto;
import pl.bank.bankAccountProj.entity.Account;
import pl.bank.bankAccountProj.entity.BankUser;
import pl.bank.bankAccountProj.entity.SubAccount;
import pl.bank.bankAccountProj.exception.ApiException;
import pl.bank.bankAccountProj.repository.AccountRepository;
import pl.bank.bankAccountProj.repository.SubAccountRepository;
import pl.bank.bankAccountProj.repository.UserRepository;
import pl.bank.bankAccountProj.util.DateUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final SubAccountRepository subAccountRepository;

    @Autowired
    public UserService(UserRepository userRepository, AccountRepository accountRepository, SubAccountRepository subAccountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.subAccountRepository = subAccountRepository;
    }

    public BankUser createUser(CreateUserDto userData) {
        try {
            validateUserData(userData);
        } catch (ApiException ae) {
            throw ae;
        } catch (Exception e) {
            log.error("Exception caught at validateUserData in createUser:", e);
            throw new ApiException("500", "internalError");
        }
        BankUser newBankUser = new BankUser(userData.getPesel(), userData.getName(), userData.getSurname(), DateUtils.getCurrTime());
        Account newAccount = new Account(DateUtils.getCurrTime(), DateUtils.getCurrTime(), newBankUser);
        SubAccount newSubAccountPln = new SubAccount(userData.getStartBalance(), "PLN", DateUtils.getCurrTime(), newAccount);
        SubAccount newSubAccountUsd = new SubAccount(BigDecimal.ZERO, "USD", DateUtils.getCurrTime(), newAccount);
        try {
            userRepository.save(newBankUser);
            accountRepository.save(newAccount);
            subAccountRepository.save(newSubAccountPln);
            subAccountRepository.save(newSubAccountUsd);
        } catch (Exception e) {
            log.error("Exception caught at saving data in createUser:", e);
            throw new ApiException("500", "(4123)");
        }
        return newBankUser;
    }

    private void validateUserData(CreateUserDto accountData) {
        List<String> errorFields = new ArrayList<>();
        if (accountData.getName() == null || accountData.getName().trim() == "") {
            errorFields.add(" name : " + accountData.getName());
        }
        if (accountData.getSurname() == null || accountData.getSurname().trim() == "") {
            errorFields.add(" surname : " + accountData.getSurname());
        }
        if (accountData.getPesel() == null || !isUserOver18(accountData.getPesel()) || isUserAlreadyRegistered(accountData.getPesel())) {
            errorFields.add(" pesel : " + accountData.getPesel());
        }

        if (!errorFields.isEmpty()) {
            log.error("validateUserData, error fields: {}", errorFields);
            throw new ApiException("500", "validateAccountError", errorFields);
        }
    }

    private boolean isUserAlreadyRegistered(Long pesel) {
        return userRepository.findByPesel(pesel).orElse(null) != null;
    }

    private boolean isUserOver18(Long pesel) {
        String peselAsString = Long.toString(pesel);
        if (peselAsString.length() != 11) {
            log.error("WRONG PESEL LENGTH");
            return false;
        }
        int yearNumber = Integer.parseInt(String.valueOf(peselAsString.charAt(0)) + String.valueOf(peselAsString.charAt(1)));
        int monthNumber = Integer.parseInt(String.valueOf(peselAsString.charAt(2)) + String.valueOf(peselAsString.charAt(3)));
        int dayNumber = Integer.parseInt(String.valueOf(peselAsString.charAt(4)) + String.valueOf(peselAsString.charAt(5)));
        if (monthNumber > 12) {
            monthNumber = monthNumber - 20;
            yearNumber = 100 + yearNumber;
        }
        Date userBirthday = new Date(yearNumber, monthNumber - 1, dayNumber);
        Calendar todaysDateMinus18yr = Calendar.getInstance();
        todaysDateMinus18yr.add(Calendar.YEAR, -18);
        return userBirthday.before(todaysDateMinus18yr.getTime());


    }
}
