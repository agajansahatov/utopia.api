package io.github.agajansahatov.utopia.api.repositories;

import io.github.agajansahatov.utopia.api.entities.Role;
import io.github.agajansahatov.utopia.api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByContact(String contact);
}
