package com.sep.cryptoservice;

import org.bitcoinj.store.BlockStoreException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.net.UnknownHostException;


@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
//@EnableEurekaClient
public class CryptoServiceApplication {

	public static void main(String[] args) throws BlockStoreException, UnknownHostException {

		SpringApplication.run(CryptoServiceApplication.class, args);
//		ECKey key = new ECKey();
//		// ... and look at the key pair
//		System.out.println("We created key:\n" + key);
//		NetworkParameters netParams = TestNet3Params.get();
//		Address addressFromKey = key.toAddress(netParams);
//		System.out.println("we can use this address:\n" + addressFromKey);
//
//		//create wallet
//		Wallet wallet = null;
//		File walletFile = new File("test.wallet");
//		wallet = new Wallet(netParams);
//		try
//		{
//			for (int i = 0; i < 5; i++) {
//			// create a key and add it to the wallet
//			wallet.addKey(new ECKey());
//		}
//		wallet.saveToFile(walletFile);
//		}catch (IOException e) {
//			System.out.println("Unable to create wallet file.");
//		}
		// fetch the first key in the wallet directly from the keychain ArrayList
//		ECKey firstKey = wallet.keychain.get(0);
//		// output key
//		System.out.println("First key in the wallet:\n" + firstKey);

		// and here is the whole wallet
//		System.out.println("Complete content of the wallet:\n" + wallet);
//
//		// we can use the hash of the public key
//		// to check whether the key pair is in this wallet
//		if (wallet.isPubKeyHashMine(firstKey.getPubKeyHash())) {
//			System.out.println("Yep, that's my key.");
//		} else {
//			System.out.println("Nope, that key didn't come from this wallet.");
//		}

//		BlockStore blockStore = new MemoryBlockStore(netParams);
//		BlockChain chain = new BlockChain(netParams, blockStore);
//		Peer peer =  new Peer(netParams,chain, new PeerAddress(InetAddress.getLocalHost()));




	}
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
