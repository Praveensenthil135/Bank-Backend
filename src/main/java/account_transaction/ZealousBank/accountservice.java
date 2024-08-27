package account_transaction.ZealousBank;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class accountservice implements UserDetailsService {
    @Autowired
    accountrepository repo;

    public AccountEntity creation(AccountEntity accountdetails) {
        return repo.save(accountdetails);
    }

    public List<AccountEntity> allaccountholders() {
        return repo.findAll();
    }

    public AccountEntity findbyaccount(Long accountno) {
        return repo.findById(accountno).orElse(new AccountEntity());
    }

    public String deletebyaccountno(Long accountno) {
        AccountEntity accountdetail = repo.findById(accountno).orElse(new AccountEntity());

        repo.deleteById(accountno);

        return accountdetail.getAccountHoldername() + " has been deleted successfully";
    }

    public List<AccountEntity> getallvaluesbyplace(String place) {
        return repo.findByAccountHolderplace(place);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AccountEntity acc = repo.findByAccountHoldername(username);
        if (acc == null) {
            throw new UsernameNotFoundException(username);
        }
        return acc;
    }

}
