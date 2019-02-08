package com.sep.cryptoservice;

import com.ulisesbocchio.jasyptspringboot.annotation.EncryptablePropertySource;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.bitcoinj.store.BlockStoreException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;


@SpringBootApplication
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
//@ComponentScan
@Configuration
@EncryptablePropertySource("application.properties")
public class CryptoServiceApplication {

	public static void main(String[] args) throws BlockStoreException, UnknownHostException {
		System.setProperty("jasypt.encryptor.password", "supersecretz");
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
	public RestTemplate restTemplate() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
		TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

		SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
				.loadTrustMaterial(null, acceptingTrustStrategy)
				.build();

		SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

		CloseableHttpClient httpClient = HttpClients.custom()
				.setSSLSocketFactory(csf)
				.build();

		HttpComponentsClientHttpRequestFactory requestFactory =
				new HttpComponentsClientHttpRequestFactory();

		requestFactory.setHttpClient(httpClient);
		return new RestTemplate(requestFactory);
	}

}
