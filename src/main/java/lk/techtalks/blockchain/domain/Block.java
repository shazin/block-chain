package lk.techtalks.blockchain.domain;

import java.io.Serializable;
import java.util.List;

public class Block implements Serializable {

    private long index;

    private long timestamp;

    private List<Transaction> transactions;

    private long proof;

    private String previousHash;

    public Block() {}

    public Block(long proof, String previousHash) {
        this.proof = proof;
        this.previousHash = previousHash;
    }

    public long getIndex() {
        return index;
    }

    public void setIndex(long index) {
        this.index = index;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public long getProof() {
        return proof;
    }

    public void setProof(long proof) {
        this.proof = proof;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }
}
