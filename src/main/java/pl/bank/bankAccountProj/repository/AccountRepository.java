package pl.bank.bankAccountProj.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bank.bankAccountProj.entity.Account;


@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

}
