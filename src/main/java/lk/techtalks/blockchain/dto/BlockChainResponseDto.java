package lk.techtalks.blockchain.dto;

import lk.techtalks.blockchain.domain.Block;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shazi on 10/3/2017.
 */
public class BlockChainResponseDto implements Serializable {

    private List<Block> chain;

    private int length;

    public BlockChainResponseDto() {

    }

    public BlockChainResponseDto(List<Block> chain) {
        this.chain = chain;
        this.length = chain.size();
    }

    public List<Block> getChain() {
        return chain;
    }

    public int getLength() {
        return length;
    }

    public void setChain(List<Block> chain) {
        this.chain = chain;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
