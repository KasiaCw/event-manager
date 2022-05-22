package com.zdjavapol110.eventmanager.core.modules.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends
        JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {

    UserEntity getByUuid(String uuid);

}
