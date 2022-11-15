package pl.bank.bankAccountProj.contoller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.bank.bankAccountProj.dto.CreateUserDto;
import pl.bank.bankAccountProj.entity.BankUser;
import pl.bank.bankAccountProj.service.UserService;

@Slf4j
@RestController
@CrossOrigin(value = "*", maxAge = 3600)
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/create")
    public ResponseEntity<BankUser> createUser(@RequestBody CreateUserDto userData) {
        log.debug("Start method createUser using: {}", userData);
        BankUser user = userService.createUser(userData);
        if (user == null) {
            log.warn("End method createUser with data: {}, not created", userData);
            return ResponseEntity.noContent().build();
        }
        log.info("End method createUser with data: {}.", userData);
        return ResponseEntity.ok(user);
    }
}
