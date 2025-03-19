package io.github.saifalhaider.nahrain.nahrain_central_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class NahrainCentralApiApplication {
  public static void main(String[] args) {
    SpringApplication.run(NahrainCentralApiApplication.class, args);
  }
}
