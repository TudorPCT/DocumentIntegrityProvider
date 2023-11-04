package fii.dip.api.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import static jakarta.persistence.TemporalType.TIMESTAMP;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "app_users")
@EntityListeners(AuditingEntityListener.class)
public class PublicKey {

    @Id
    private String publicKey;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Version
    private int version;

    @CreatedDate
    @Temporal(TIMESTAMP)
    private LocalDateTime createdAt;
}
