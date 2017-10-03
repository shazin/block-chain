package lk.techtalks.blockchain.domain;

import com.google.common.io.BaseEncoding;
import lk.techtalks.blockchain.dto.BlockChainResponseDto;
import lk.techtalks.blockchain.helper.HashHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;

@Component
public class BlockChain implements Serializable {

    private HashHelper hashHelper;

    private List<Transaction> currentTransactions;

    private List<Block> chain;

    private Set<URL> nodes;

    private RestTemplate restTemplate;

    @Autowired
    public BlockChain(HashHelper hashHelper) {
        this.currentTransactions = Collections.synchronizedList(new LinkedList<>());
        this.chain = Collections.synchronizedList(new LinkedList<>());
        this.nodes = Collections.synchronizedSet(new HashSet<>());
        this.hashHelper = hashHelper;
        this.restTemplate = new RestTemplate();

        createBlock(new Block(100, "1"));
    }

    public Block createBlock(Block block) {
        block.setIndex(chain.size() + 1);
        block.setTimestamp(System.currentTimeMillis());
        block.setTransactions(this.currentTransactions);
        if (block.getPreviousHash() == null) {
            block.setPreviousHash(hashHelper.hashBlock(block));
        }

        this.currentTransactions = Collections.synchronizedList(new LinkedList<>());

        this.chain.add(block);

        return block;
    }

    public Block getLastBlock() {
        return this.chain.get(this.chain.size() - 1);
    }

    public long createTransaction(Transaction transaction) {
        this.currentTransactions.add(transaction);

        return this.chain.get(this.chain.size() - 1).getIndex() + 1;
    }

    public long proofOfWork(long lastProof) {
        long proof = 0;
        while(!validProof(lastProof, proof)) {
            proof++;
        }
        return proof;
    }

    public boolean validProof(long lastProof, long proof) {
        String guess = BaseEncoding.base64().encode(String.format("{%d}{%d}", lastProof, proof).getBytes(Charset.forName("UTF8")));
        String guessHash = hashHelper.hash(guess);
        return guessHash.substring(0, 4).equals("0000");
    }

    public List<Block> getChain() {
        return Collections.unmodifiableList(this.chain);
    }

    public void registerNode(URL url) {
        this.nodes.add(url);
    }

    public boolean validChain(List<Block> chain) {
        Block lastBlock = chain.get(0);
        Block block = null;
        int currentIndex = 1;

        while (currentIndex < chain.size()) {
            block = chain.get(currentIndex);

            if (!block.getPreviousHash().equals(hashHelper.hashBlock(lastBlock))) {
                return false;
            }

            if (!validProof(lastBlock.getProof(), block.getProof())) {
                return false;
            }

            currentIndex++;
        }

        return true;
    }

    public boolean resolveConflicts() {
        Set<URL> neighbours = this.nodes;
        List<Block> newChain = null;

        int maxLength = chain.size();

        for (URL node:neighbours) {
            BlockChainResponseDto response = restTemplate.getForObject(node.toString() + "/chain", BlockChainResponseDto.class);

            int length = response.getLength();
            List<Block> chain = response.getChain();

            if (length > maxLength && validChain(chain)) {
                maxLength = length;
                newChain = chain;
            }
        }

        if (newChain != null) {
            this.chain = newChain;
            return true;
        }

        return false;
    }

    public Set<URL> getNodes() {
        return Collections.unmodifiableSet(this.nodes);
    }
}
