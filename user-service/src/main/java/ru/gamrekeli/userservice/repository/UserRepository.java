package ru.gamrekeli.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gamrekeli.userservice.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
