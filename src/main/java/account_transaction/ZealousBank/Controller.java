package account_transaction.ZealousBank;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/zealousbank")
@CrossOrigin(origins = "http://localhost:3000")
public class Controller {
    @Autowired
    accountservice service;

    public PasswordEncoder coder() {
        return new BCryptPasswordEncoder();
    }

    @GetMapping("/")
    public void run() {
        System.out.println(" welcome to online zealous bank registration project...!");
    }

    @GetMapping("/{user}")
    public AccountEntity purpose(@PathVariable("user") String user) {
        AccountEntity account1 = (AccountEntity) service.loadUserByUsername(user);
        return account1;
    }

    @PostMapping("/accountcreate")
    public String accountcreate(@RequestBody AccountEntity accoundetails) {
        accoundetails.setPassword(coder().encode(accoundetails.getPassword()));
        return service.creation(accoundetails).getAccountHoldername() + " has been created successfully";
    }

    @GetMapping("/list")
    public List<AccountEntity> alldetails() {
        return service.allaccountholders();
    }

    @GetMapping("/findbyaccount/{accno}")
    public AccountEntity getaccdetail(@PathVariable("accno") long accno) {
        return service.findbyaccount(accno);
    }

    @PutMapping("/updateaccountdetails")
    public String updateaccount(@RequestBody AccountEntity accountdetails) {
        AccountEntity account1 = service.creation(accountdetails);

        return account1.getAccountNumber() + " your account has been updated successfully";
    }

    @DeleteMapping("/deletebyid/{accno}")
    public String deleteaccount(@PathVariable("accno") long accno) {
        deleteall(accno);
        return service.deletebyaccountno(accno) + " ..!";
    }

    @GetMapping("/findbyplace/{place}")
    public List<AccountEntity> findbyplace(@PathVariable("place") String place) {
        return service.getallvaluesbyplace(place);
    }

    @GetMapping("/just")
    public void sample() {
        System.out.println("welcome to zealous tech corp");
    }

    @Autowired
    transactionservice tservice;

    @PostMapping("/createtransaction")
    public TransactionEntity transactioncreate(@RequestBody TransactionEntity transactiondetails) {

        transactiondetails.setCurrentBalance(transactiondetails.getAccount().getAccountBalance());

        if (transactiondetails.getTransactionType().equalsIgnoreCase("credit")) {
            transactiondetails.setCurrentBalance(
                    transactiondetails.getCurrentBalance().add(transactiondetails.getTransactionAmount()));

            BigDecimal currentBalance1 = transactiondetails.getCurrentBalance();

            AccountEntity acc1 = service.findbyaccount(transactiondetails.getAccount().getAccountNumber());

            acc1.setAccountBalance(currentBalance1);

        } else if (transactiondetails.getTransactionType().equalsIgnoreCase("debit")) {
            if (transactiondetails.getAccount().getAccountBalance()
                    .compareTo(transactiondetails.getTransactionAmount()) > 0) {
                transactiondetails.setCurrentBalance(
                        transactiondetails.getCurrentBalance().subtract(transactiondetails.getTransactionAmount()));

                BigDecimal currentBalance1 = transactiondetails.getCurrentBalance();

                AccountEntity acc1 = service.findbyaccount(transactiondetails.getAccount().getAccountNumber());

                acc1.setAccountBalance(currentBalance1);

            }
        }

        return tservice.createtransaction(transactiondetails);

    }

    @GetMapping("/getalltransaction")
    public List<TransactionEntity> getAllTransactions() {
        return tservice.listalltransaction();
    }

    @GetMapping("/transactionbyid/{id}")
    public Optional<TransactionEntity> getTransactionById(@PathVariable long id) {
        return tservice.findonetransaction(id);
    }

    // Delete a transaction
    @DeleteMapping("/deletetransaction/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        tservice.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deletealltransaction/{accno}")
    public void deleteall(@PathVariable Long accno) {
        tservice.trepo.deleteAll();
    }

    // list url by one user

    @GetMapping("/gettransactionbyoneaccount/{accno}")
    public List<TransactionEntity> listdatas(@PathVariable("accno") Long accno) {
        AccountEntity account = service.findbyaccount(accno);

        return tservice.gettransactionbyoneuser(account);
    }
}
