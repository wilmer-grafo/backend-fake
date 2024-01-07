package bo.gob.segip.micro_servicios.security;

import bo.gob.segip.micro_servicios.entities.UserEntity;
import bo.gob.segip.micro_servicios.exceptions.ResourceNotFoundException;
import bo.gob.segip.micro_servicios.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity user = this.userRepository.findByEmail(username).orElseThrow(() ->
                new ResourceNotFoundException("User", "Email id" + username, 0L));

        return user;

    }
}
