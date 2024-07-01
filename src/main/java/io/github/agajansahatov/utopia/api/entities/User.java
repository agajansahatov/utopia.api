package io.github.agajansahatov.utopia.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
// Here we also need to implement the UserDetails interface
// Because the spring security authorization uses it
// We could also implement a CustomUserDetails model,
// but this seemed a cleaner approach
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 255)
    private String contact;

    @Column(nullable = false, length = 255)
    private String password;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Column(nullable = false, length = 255)
    private String firstname;

    @Column(length = 255)
    private String lastname;

    // It is set to Unsigned in database
    @Column
    private BigDecimal balance;

    @Column(nullable = false, length = 50)
    private String country;

    @Column(nullable = false, length = 50)
    private String province;

    @Column(nullable = false, length = 50)
    private String city;

    @Column(nullable = false, length = 500)
    private String address;

    // Here the authTime is set by database by default
    // Might need to update by yourself, if the user logout and login again.
    @Column(nullable = false)
    private Date authTime;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "favourites",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> likedProducts = new LinkedHashSet<>();

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "traces",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> visitedProducts = new LinkedHashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<Order> orders = new LinkedHashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<Comment> comments = new LinkedHashSet<>();

    // The rest is the all the methods of the
    // UserDetails interface that we need implement,
    // which is used by UserDetailsService
    // which is used by SecurityConfig
    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role.getName()));
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return contact;
    }

    @Override
    @JsonIgnore
    // In the future we can add a property for this and persist it on the db
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    // In the future we can add a property for this and persist it on the db
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    // In the future we can add a property for this and persist it on the db
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    // In the future we can add a property for this and persist it on the db
    public boolean isEnabled() {
        return true;
    }
}
