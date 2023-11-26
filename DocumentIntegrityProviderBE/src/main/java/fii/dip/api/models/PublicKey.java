package fii.dip.api.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import static jakarta.persistence.TemporalType.TIMESTAMP;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "public_keys")
@EntityListeners(AuditingEntityListener.class)
public class PublicKey {

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid2")
    private String id;

    @Column(columnDefinition = "TEXT")
    private String publicKey;

    @ManyToOne
    @JoinColumn(name = "app_users_id", nullable = false)
    private User user;

    @Version
    private int version;

    @CreatedDate
    @Temporal(TIMESTAMP)
    private LocalDateTime createdAt;

    public PublicKey(String publicKey, User user) {
        this.publicKey = publicKey;
        this.user = user;
    }
}
