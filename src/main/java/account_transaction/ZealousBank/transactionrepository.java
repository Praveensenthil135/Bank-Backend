package account_transaction.ZealousBank;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface transactionrepository extends JpaRepository<TransactionEntity, Long> {

    public List<TransactionEntity> findAllByAccount(AccountEntity accountdetails);

}
