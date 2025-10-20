package ru.kata.spring.boot_security.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u JOIN FETCH u.roles WHERE u.username = :username")
    User findByUsername(@Param("username") String username);
    boolean existsByUsername(String username);

    //JpaRepository - предоставляет CRUD операции
    //@Query - кастомный JPQL запрос
    //JOIN FETCH - загружает роли сразу (жадная загрузка)
    //@Param связывает параметр метода с параметром запроса
}
