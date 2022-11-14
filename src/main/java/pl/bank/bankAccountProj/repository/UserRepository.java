package pl.bank.bankAccountProj.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bank.bankAccountProj.entity.BankUser;


@Repository
public interface UserRepository extends JpaRepository<BankUser, Long> {
    BankUser findByPesel(Long pesel);
}
