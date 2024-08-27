package account_transaction.ZealousBank;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transactiondetails")
public class TransactionEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "my_sequence_gens")
  @SequenceGenerator(name = "my_sequence_gens", sequenceName = "my_sequenceses", allocationSize = 1)
  private Long transactionNumber;
  private String transactionType;
  private BigDecimal currentBalance;
  private BigDecimal transactionAmount;
  private String transactionHolderNumber;
  @Column(name = "transaction_date")
  private Date transactionDate;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "accountNumber", nullable = false)
  @JsonBackReference
  private AccountEntity account;

}
