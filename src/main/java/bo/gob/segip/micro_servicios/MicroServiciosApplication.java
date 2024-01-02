package bo.gob.segip.micro_servicios;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MicroServiciosApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroServiciosApplication.class, args);
    }

    //	Mapping between classes using ModelMapper class

    @Bean
    public ModelMapper modelMapper() {

        return new ModelMapper();

    }

}
