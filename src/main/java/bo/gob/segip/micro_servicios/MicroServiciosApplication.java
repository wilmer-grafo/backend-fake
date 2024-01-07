package bo.gob.segip.micro_servicios;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@RequiredArgsConstructor
public class MicroServiciosApplication implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(MicroServiciosApplication.class, args);
    }

    //	Mapping between classes using ModelMapper class

    @Bean
    public ModelMapper modelMapper() {

        return new ModelMapper();

    }

    // Method from interface

    @Override
    public void run(String... args) throws Exception {
        System.out.println(this.passwordEncoder.encode("xyz"));
    }
}
