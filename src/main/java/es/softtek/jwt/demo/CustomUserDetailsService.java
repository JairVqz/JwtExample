package es.softtek.jwt.demo;

import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repo;

    public CustomUserDetailsService(UserRepository repo) {
        this.repo = repo;
    }

    // Método para cargar usuario validando username, email y curp
    public UserDetails loadUserByUsernameEmailCurp(String username, String email, String curp) throws UsernameNotFoundException {
        User user = repo.findByUsernameAndEmailAndCurp(username, email, curp)
                .orElseThrow(() -> new UsernameNotFoundException("No existe el usuario con esos datos"));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                "", // No usamos password, dejamos vacío
                Collections.singleton(new SimpleGrantedAuthority(user.getRole()))
        );
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'loadUserByUsername'");
    }

    /*@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repo.find(username)
                .orElseThrow(() -> new UsernameNotFoundException("No existe el usuario"));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(user.getRole()))
        );
    }*/
}
