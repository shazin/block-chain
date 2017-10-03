package lk.techtalks.blockchain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@SpringBootApplication
public class Main {

    //https://hackernoon.com/learn-blockchains-by-building-one-117428612f46

    public static void main(String... args) {
        if (System.getProperty("blockchain.node.id") == null) {
            System.setProperty("blockchain.node.id", UUID.randomUUID().toString().replace("-", ""));
        }
        SpringApplication.run(Main.class, args);
    }
}
