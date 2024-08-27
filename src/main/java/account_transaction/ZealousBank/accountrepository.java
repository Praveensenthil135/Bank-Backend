package account_transaction.ZealousBank;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface accountrepository extends JpaRepository<AccountEntity, Long> {

    public List<AccountEntity> findByAccountHolderplace(String place);

    public AccountEntity findByAccountHoldername(String username);

}
