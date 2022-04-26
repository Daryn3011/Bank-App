package com.example.testproject.Repository;

import com.example.testproject.Entity.UserEntity;
import com.example.testproject.models.Money;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
    UserEntity getByLogin(String login);
    UserEntity getById(Long id);
    void deleteByLogin(String login);
    void deleteById(Long id);
    boolean existsUserEntitiesByLogin(String login);
    boolean existsUserEntitiesById(Long id);
}
