package br.com.leonardo.processor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import br.com.leonardo.CommonsConfig;
import br.com.leonardo.RabbitMQConfig;

@Import(value = {CommonsConfig.class, RabbitMQConfig.class})
@SpringBootApplication
public class Application {
	
	public final static String QUEUE_NAME = "product-queue";
	public final static String EXCHANGE_NAME = "product-exchange";
	public final static String ROUTING_KEY = "product-routing-key";
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}

}
