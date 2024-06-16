package io.github.agajansahatov.utopia.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Byte id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "role")
    private Set<User> users = new LinkedHashSet<>();
}
