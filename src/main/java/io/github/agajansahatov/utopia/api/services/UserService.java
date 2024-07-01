    package io.github.agajansahatov.utopia.api.services;

    import io.github.agajansahatov.utopia.api.entities.User;
    import io.github.agajansahatov.utopia.api.repositories.UserRepository;
    import lombok.RequiredArgsConstructor;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.security.core.userdetails.UserDetailsService;
    import org.springframework.security.core.userdetails.UsernameNotFoundException;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.stereotype.Service;

    @Service
    @RequiredArgsConstructor
    public class UserService implements UserDetailsService {
        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;

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
    }
