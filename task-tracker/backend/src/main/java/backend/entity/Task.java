package backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "tasks")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Task {
    @Id
    @GeneratedValue(generator = "UUID")
    @org.hibernate.annotations.GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;
    @Column(nullable = false)
    private String title;
    @Column(length = 1000)
    private String description;
    @UpdateTimestamp
    private Instant updatedAt = Instant.now();

    @Column(nullable = false)
    private boolean completed = Boolean.FALSE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
