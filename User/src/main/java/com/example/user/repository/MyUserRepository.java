package com.example.user.repository;

import com.example.user.model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MyUserRepository extends JpaRepository<MyUser,Long> {

    Optional<MyUser> findByUsername(String username);

    Optional<MyUser> findByEmail(String email);

}
