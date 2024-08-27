package account_transaction.ZealousBank;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.swing.Spring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
// @RunWith(Spring.class)
@ExtendWith(MockitoExtension.class)
class ZealousBankApplicationTests {

	@Mock
	private accountrepository repo;

	@InjectMocks
	private accountservice service;

	private AccountEntity account;

	@Mock
	private transactionrepository trepo;

	@InjectMocks
	private transactionservice tservice;

	private TransactionEntity transaction1;
	private TransactionEntity transaction2;

	@BeforeEach
	public void setUp() {
		account = new AccountEntity();
		account.setAccountNumber(38938478783l);
		account.setAccountHoldercontactno(9789355930l);
		account.setAccountBalance(BigDecimal.valueOf(89000));
		account.setAccountHoldername("Manojkumar_M");
		account.setAccountIfsccode("IDIB000E014");
		account.setAccountHolderplace("Elachipalayam");
		Date transactionDate = new Date(); // Initialize a Date object
		transaction1 = new TransactionEntity(8867756579l, "credit", account.getAccountBalance(),
				BigDecimal.valueOf(4500), "783978378345", transactionDate, account);
		transaction2 = new TransactionEntity(79837499487l, "debit", account.getAccountBalance(),
				BigDecimal.valueOf(7800), "47873864764738l", transactionDate, account);
	}

	// @Test
	// public void testCreation() {
	// when(repo.save(any(AccountEntity.class))).thenReturn(account);

	// AccountEntity result = service.creation(account);

	// // assertNotNull(result);

	// // assertNotEquals(account, result);

	// assertEquals(BigDecimal.valueOf(89000), result.getAccountBalance());

	// }

	// @Test
	// public void testGetTransactionByOneUser() {
	// List<TransactionEntity> transactions = Arrays.asList(transaction1,
	// transaction2);

	// when(trepo.findAllByAccount(account)).thenReturn(transactions);

	// List<TransactionEntity> result = tservice.gettransactionbyoneuser(account);

	// // assertNotNull(result);
	// assertEquals(5, result.size());
	// // assertEquals(transactions, result);
	// // verify(trepo, times(1)).findAllByAccount(account);
	// }

	@Test
	void testDeleteTransaction() {
		Long id = transaction1.getTransactionNumber();

		tservice.deleteTransaction(id);

		verify(trepo).deleteById(id);
	}

	// @Test
	// public void testListAllTransaction() {
	// // Given
	// TransactionEntity transaction1 = new TransactionEntity();
	// TransactionEntity transaction2 = new TransactionEntity();
	// List<TransactionEntity> expectedTransactions = Arrays.asList(transaction1,
	// transaction2);

	// when(trepo.findAll()).thenReturn(expectedTransactions);

	// // When
	// List<TransactionEntity> actualTransactions = tservice.listalltransaction();

	// // Then
	// assertThat(actualTransactions).isEqualTo(expectedTransactions);
	// }
}
