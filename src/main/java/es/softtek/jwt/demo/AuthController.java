package es.softtek.jwt.demo;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public AuthController(JwtUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username,
            @RequestParam String email,
            @RequestParam String curp) {

        Optional<User> optionalUser = userRepository.findByUsernameAndEmailAndCurp(username, email, curp);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Usuario, email o CURP incorrectos");
        }

        User user = optionalUser.get();

        // Generar JWT con username, email y curp como claims personalizados
        String token = jwtUtil.generateTokenWithClaims(user.getUsername(), user.getEmail(), user.getCurp());

        return ResponseEntity.ok(new AuthResponse(token));
    }

    public class AuthResponse {
        private String token;

        public AuthResponse(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }

}
