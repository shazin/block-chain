package lk.techtalks.blockchain;

import lk.techtalks.blockchain.domain.Block;
import lk.techtalks.blockchain.domain.BlockChain;
import lk.techtalks.blockchain.domain.Transaction;
import lk.techtalks.blockchain.helper.HashHelper;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class BlockChainTest {

    private BlockChain blockChain = new BlockChain(new HashHelper());

    @Test
    public void createTransaction() {
        Transaction transaction = new Transaction(UUID.randomUUID().toString(), UUID.randomUUID().toString(), BigDecimal.valueOf(1000.00));

        assertThat(blockChain.createTransaction(transaction)).isGreaterThan(0);
    }

    @Test
    public void createBlock() {
        Block block = new Block(100, null);

        Block createdBlock = blockChain.createBlock(block);

        assertThat(createdBlock).isNotNull();
        assertThat(createdBlock.getIndex()).isEqualTo(2);
        assertThat(createdBlock.getProof()).isEqualTo(100l);
        assertThat(createdBlock.getTimestamp()).isLessThan(System.currentTimeMillis());
        assertThat(createdBlock.getPreviousHash()).isNotBlank();
    }
}
