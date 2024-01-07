package bo.gob.segip.micro_servicios.payloads;

import lombok.Data;

@Data
public class JwtAuthRequest {

    private String userName;
    private String password;

}
