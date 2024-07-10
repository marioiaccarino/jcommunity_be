package it.jpanik.services.servicesImp;

import it.jpanik.entities.User;
import it.jpanik.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    private final UserRepository repository;

    // FIXME mancano i final e anche l'autowired. Non ti va in null pointer???
    public UserDetailsServiceImp(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // FIXME è buona cosa mettere un logger di ritorno con l'oggetto risultato

        return repository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }


}
