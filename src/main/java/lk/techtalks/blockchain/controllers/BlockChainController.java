package lk.techtalks.blockchain.controllers;

import lk.techtalks.blockchain.domain.Block;
import lk.techtalks.blockchain.domain.BlockChain;
import lk.techtalks.blockchain.domain.Transaction;
import lk.techtalks.blockchain.dto.BlockChainResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
public class BlockChainController {

    @Autowired
    private BlockChain blockChain;

    @Value("${blockchain.node.id}")
    private String blockChainNodeId;

    @RequestMapping(value = "/transaction", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, String> createTransaction(@RequestBody Transaction transaction) {
        long index = blockChain.createTransaction(transaction);

        return Collections.singletonMap("message", String.format("Transaction will be added to Block {%d}", index));
    }

    @RequestMapping("/mine")
    public Map<String, Object> mine() {
        Block lastBlock = blockChain.getLastBlock();
        long lastProof = lastBlock.getProof();

        long proof = blockChain.proofOfWork(lastProof);

        blockChain.createTransaction(new Transaction("0", blockChainNodeId, BigDecimal.valueOf(1)));

        Block block = blockChain.createBlock(new Block(proof, null));

        Map<String, Object> response = new HashMap<>();
        response.put("message", "New Block Forged");
        response.put("index", block.getIndex());
        response.put("transactions", block.getTransactions());
        response.put("proof", block.getProof());
        response.put("previous_hash", block.getPreviousHash());

        return response;
    }

    @RequestMapping("/chain")
    public BlockChainResponseDto chain() {
        return new BlockChainResponseDto(blockChain.getChain());
    }




}
