package pl.bank.bankAccountProj.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.bank.bankAccountProj.entity.Account;
import pl.bank.bankAccountProj.entity.SubAccount;

import java.util.List;


@Repository
public interface SubAccountRepository extends JpaRepository<SubAccount, String> {

    @Query("SELECT sa FROM SubAccount sa WHERE sa.account = :account")
    List<SubAccount> findAllByAccounts(Account account);

}
