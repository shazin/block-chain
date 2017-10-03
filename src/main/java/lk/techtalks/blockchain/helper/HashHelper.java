package lk.techtalks.blockchain.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.hash.Hashing;
import lk.techtalks.blockchain.domain.Block;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class HashHelper {

    private ObjectMapper objectMapper = new ObjectMapper();

    public String hashBlock(Block block) {
        try {
            return hash(objectMapper.writeValueAsString(block));
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public String hash(String content) {
        String hash = Hashing
                    .sha256()
                    .hashString(content, StandardCharsets.UTF_8)
                    .toString();

        return hash;

    }

}
