package fii.dip.api.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.TemporalType.TIMESTAMP;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "app_users")
@EntityListeners(AuditingEntityListener.class)
@Builder
public class User {

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid2")
    @Column(length = 36, nullable = false, updatable = false, insertable = false)
    private String id;

    @Column(unique = true)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PublicKey> publicKeys;

    @Column(columnDefinition="boolean default TRUE")
    private boolean isAccountNonLocked = true;

    @Version
    private int version;

    @CreatedDate
    @Temporal(TIMESTAMP)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Temporal(TIMESTAMP)
    private LocalDateTime updatedAt;

    public User(String email, String password, Role role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

}
