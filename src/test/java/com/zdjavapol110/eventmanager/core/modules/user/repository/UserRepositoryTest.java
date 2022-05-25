package com.zdjavapol110.eventmanager.core.modules.user.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import javax.annotation.Resource;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
class UserRepositoryTest  {

    @Autowired
    private UserRepository userRepository;

    @Resource
    private TestEntityManager entityManager;

    @Test
    public void testCreateUser() {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("abc@sda.pl");
        userEntity.setPassword("eventmanager");
        userEntity.setFirstName("Przemek");
        userEntity.setLastName("Wyzryn");

        UserEntity savedUser = userRepository.save(userEntity);

        UserEntity existUser = entityManager.find(UserEntity.class, savedUser.getId());
        assertThat(existUser.getEmail()).isEqualTo(userEntity.getEmail());
    }

}