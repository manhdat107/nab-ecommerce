package com.vdc.ecommerce.reposirtory;

import com.vdc.ecommerce.common.RoleConstant;
import com.vdc.ecommerce.model.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleConstant roleName);
}
