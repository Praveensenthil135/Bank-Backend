package account_transaction.ZealousBank;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class transactionservice {
    @Autowired
    transactionrepository trepo;

    public TransactionEntity createtransaction(TransactionEntity transactiondetails) {
        return trepo.save(transactiondetails);
    }

    public List<TransactionEntity> listalltransaction() {
        return trepo.findAll();
    }

    public Optional<TransactionEntity> findonetransaction(long transactionNumber) {
        return trepo.findById(transactionNumber);
    }

    public void deleteTransaction(Long id) {
        trepo.deleteById(id);
    }

    public List<TransactionEntity> gettransactionbyoneuser(AccountEntity accountdetails) {
        return trepo.findAllByAccount(accountdetails);
    }
}
