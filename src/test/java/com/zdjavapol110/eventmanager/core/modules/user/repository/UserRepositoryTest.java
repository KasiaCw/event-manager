package com.zdjavapol110.eventmanager.core.modules.user.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
class UserRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testCreateUser() {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("ravikumar@gmail.com");
        userEntity.setPassword("ravi2020");
        userEntity.setFirstName("Ravi");
        userEntity.setLastName("Kumar");

        UserEntity savedUser = userRepository.save(userEntity);

        UserEntity existUser = entityManager.find(UserEntity.class, savedUser.getId());

        assertThat(userEntity.getEmail()).isEqualTo(existUser.getEmail());

    }
}