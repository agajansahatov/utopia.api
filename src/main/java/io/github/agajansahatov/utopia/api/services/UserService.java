    package io.github.agajansahatov.utopia.api.services;

    import io.github.agajansahatov.utopia.api.entities.User;
    import io.github.agajansahatov.utopia.api.models.requestDTOs.AuthRequest;
    import io.github.agajansahatov.utopia.api.repositories.UserRepository;
    import lombok.RequiredArgsConstructor;
    import org.springframework.context.ApplicationContext;
    import org.springframework.context.annotation.Lazy;
    import org.springframework.security.authentication.AuthenticationManager;
    import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
    import org.springframework.security.core.Authentication;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.security.core.userdetails.UserDetailsService;
    import org.springframework.security.core.userdetails.UsernameNotFoundException;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.security.oauth2.jwt.JwtClaimsSet;
    import org.springframework.security.oauth2.jwt.JwtEncoder;
    import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
    import org.springframework.stereotype.Service;

    import java.time.Instant;
    import java.time.temporal.ChronoUnit;

    @Service
    @RequiredArgsConstructor
    public class UserService implements UserDetailsService {
        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;
        private final JwtEncoder jwtEncoder;
        private final ApplicationContext applicationContext;

        // This method is a method of UserDetailsService,
        // which is used by the SecurityConfig
        // to do the auth
        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            // Here we used findByContact,
            // because the username of the interface UserDetailsService
            // is compatible with the contact of my User entity
            return userRepository.findByContact(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with contact: " + username));
        }

        public User registerUser(User user) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        }

        public boolean userExists(String contact) {
            return userRepository.findByContact(contact).isPresent();
        }

        public String generateToken(String userContact, String userPassword, boolean isTest) {
            AuthenticationManager authenticationManager = applicationContext.getBean(AuthenticationManager.class);
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userContact, userPassword)
            );

            Instant now = Instant.now();

            // default value is false, which is set in the overloaded method
            if(isTest) {
                now = now.minusSeconds(5L);
            }

            // Assuming that getAuthorities() returns a collection with exactly one element (role)
            String role = authentication.getAuthorities().iterator().next().getAuthority();

            User user = (User) authentication.getPrincipal();
            String fullName = user.getFirstname() + " " + user.getLastname();

            JwtClaimsSet claims = JwtClaimsSet.builder()
                    .issuer("utopia.api")
                    .issuedAt(now)
                    .expiresAt(now.plus(10, ChronoUnit.DAYS))
                    .claim("id", user.getId())
                    .subject(fullName)
                    .claim("role", role)
                    .build();

            return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        }

        public String generateToken(String userContact, String userPassword) {
            return this.generateToken(userContact, userPassword, false);
        }

    }
