package org.example.jelog.repository;

import org.example.jelog.domain.user.UserRole;
import org.example.jelog.domain.user.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {
}
