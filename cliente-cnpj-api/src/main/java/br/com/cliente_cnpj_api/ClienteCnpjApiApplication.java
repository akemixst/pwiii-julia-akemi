package br.com.cliente_cnpj_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ClienteCnpjApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClienteCnpjApiApplication.class, args);
    }

}
