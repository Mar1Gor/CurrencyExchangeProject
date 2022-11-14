package pl.bank.bankAccountProj.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bank.bankAccountProj.dto.CreateUserDto;
import pl.bank.bankAccountProj.entity.Account;
import pl.bank.bankAccountProj.entity.BankUser;
import pl.bank.bankAccountProj.exception.ApiException;
import pl.bank.bankAccountProj.repository.AccountRepository;
import pl.bank.bankAccountProj.repository.UserRepository;
import pl.bank.bankAccountProj.util.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;



@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public UserService(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    public BankUser createUser(CreateUserDto userData) {
        validateUserData(userData);
        BankUser newBankUser = new BankUser(userData.getPesel(), userData.getName(), userData.getSurname(), DateUtils.getCurrTime());
        Account newAccount = new Account(userData.getStartBalance(), DateUtils.getCurrTime(), DateUtils.getCurrTime(), newBankUser);
        userRepository.save(newBankUser);
        accountRepository.save(newAccount);
        return newBankUser;
    }



    private void validateUserData(CreateUserDto accountData) {
        List<String> errorFields = new ArrayList<>();
        if (accountData.getName() == null || accountData.getName().trim() == "") //isblank
        {
            errorFields.add("name");
        }
        if (accountData.getSurname() == null || accountData.getSurname().trim() == "") //isblank
        {
            errorFields.add("surname");
        }
        if (accountData.getPesel() == null || !isUserOver18(accountData.getPesel())) //isblank check if walid
        {
            errorFields.add("surname");
        }
        if (!errorFields.isEmpty()) {
            throw new ApiException("500", "validateAccountError", errorFields);
        }
    }

    private boolean isUserOver18(Long pesel) {
        String peselAsString = Long.toString(pesel);
        Integer yearNumber = peselAsString.charAt(0) + peselAsString.charAt(1);
        Integer monthNumber = peselAsString.charAt(2) + peselAsString.charAt(3);
        Integer dayNumber = peselAsString.charAt(4) + peselAsString.charAt(5);
        if (monthNumber > 12) {
            monthNumber = monthNumber - 20;
        }
        Date userBirthday = new Date(yearNumber, monthNumber - 1, dayNumber);
        Calendar todaysDateMinus18yr = Calendar.getInstance();
        todaysDateMinus18yr.add(Calendar.YEAR, -18);
        return userBirthday.before(todaysDateMinus18yr.getTime());


    }
}
