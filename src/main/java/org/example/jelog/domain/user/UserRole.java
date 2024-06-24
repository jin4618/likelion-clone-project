package org.example.jelog.domain.user;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;

import java.time.LocalDate;

@Entity
@Table(name = "user_roles")
@NoArgsConstructor
@Data
public class UserRole implements Persistable<UserRoleId> {
    @EmbeddedId
    private UserRoleId id;

    @MapsId("userId")   // 식별자 클래스의 ID와 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @MapsId("roleId")   // 식별자 클래스의 ID와 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    @CreatedDate
    private LocalDate created;


    @Override
    public UserRoleId getId() {
        return null;
    }

    @Override
    public boolean isNew() {
        return created == null;
    }
}
