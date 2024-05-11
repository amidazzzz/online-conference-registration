package org.amida.onlineconferenceregistration.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@Data
@Entity
@Table(name = "conferences")
public class Conference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, name = "title")
    private String title;

    @Column(name = "details")
    private String details;

    @Column(nullable = false, name = "creation_date")
    private LocalDate creationDate;

    @Column(nullable = false, name = "end_date")
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "created_by_id")
    private User createdBy;

    @ManyToMany(mappedBy = "conferences", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<User> users;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Conference conference)) return false;
        return Objects.equals(id, conference.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
