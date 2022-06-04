package com.zdjavapol110.eventmanager.core.modules.user.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import javax.management.relation.Role;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
class UserRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepo;

    @Test
    public void testCreateUser() {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("sda@gmail.com");
        userEntity.setPassword("sda110");
        userEntity.setFirstName("SDA");
        userEntity.setLastName("Zadania");

        UserEntity savedUser = userRepository.save(userEntity);

        UserEntity existUser = entityManager.find(UserEntity.class, savedUser.getId());

        assertThat(userEntity.getEmail()).isEqualTo(existUser.getEmail());


        @Test
        public void testAddRoleToNewUser() {
            Role roleAdmin = roleRepos.findByName("Admin");

            UserEntity user = new UserEntity();
            user.setEmail("mikes.gates@gmail.com");
            user.setPassword("mike2020");
            user.setFirstName("Mike");
            user.setLastName("Gates");
            user.addRole(roleAdmin);

            UserEntity savedUser = userRepo.save(user);

            assertThat(savedUser.getRoles().size()).isEqualTo(1);
        }

    }
}