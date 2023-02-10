package cn.whu.wy.osgov;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

/**
 * @author WangYong
 * Date 2022/05/05
 * Time 18:13
 */
@Configuration
public class BeanConfig {

    @Bean("qaxRestTemplate")
    public RestTemplate restTemplate() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        HttpComponentsClientHttpRequestFactory factory = genHttpRequestFactory();

        String qaxToken = "Private-Token eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJxYXhfdXNlciIsImV4cCI6NDEwMjQxNTk5OSwidXNlcm5hbWUiOiJ3YW5neW9uZ0BjaGluYWNsZWFyLmNuIiwiZXhwaXJhdGlvbkRhdGUiOiJUaHUgRGVjIDMxIDIzOjU5OjU5IENTVCAyMDk5IiwidHMiOiIxNjY4NzU2MzAzIn0.PX4JCGmzcmJMjsmTPx5ufTgFi-44zY-xcNC1N5HGwIo";
        return new RestTemplateBuilder().requestFactory(() -> factory).defaultHeader(HttpHeaders.AUTHORIZATION, qaxToken).build();
    }

    @Bean
    public HttpComponentsClientHttpRequestFactory genHttpRequestFactory() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        TrustStrategy acceptingTrustStrategy = (X509Certificate, authType) -> true;
        SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
        SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());

        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        httpClientBuilder.setSSLSocketFactory(sslConnectionSocketFactory);
        CloseableHttpClient httpClient = httpClientBuilder.build();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setHttpClient(httpClient);
        factory.setConnectTimeout(2000);
        factory.setReadTimeout(10000);
        return factory;
    }

}
