package lk.techtalks.blockchain.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class Transaction implements Serializable {

    private String sender;

    private String recipient;

    private BigDecimal amount;

    public Transaction() {}

    public Transaction(String sender, String recipient, BigDecimal amount) {
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
