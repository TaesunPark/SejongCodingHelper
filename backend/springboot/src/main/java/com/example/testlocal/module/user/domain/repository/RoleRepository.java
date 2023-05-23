package com.example.testlocal.module.user.domain.repository;

import com.example.testlocal.module.user.domain.entity.Role;
import com.example.testlocal.module.user.domain.entity.RoleName;
import com.example.testlocal.module.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRole(RoleName role);
}
