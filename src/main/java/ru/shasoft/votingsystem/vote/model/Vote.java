package ru.shasoft.votingsystem.vote.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.shasoft.votingsystem.common.HasId;
import ru.shasoft.votingsystem.common.model.BaseEntity;

import java.time.LocalDate;

@Entity
@Table(name = "vote",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id", "create_at"}, name = "uk_vote_restaurant_id_create_at")},
        indexes = @Index(name = "ik_vote_create_at_restaurant_id", columnList = "create_at, restaurant_id")
)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Vote extends BaseEntity implements HasId {
// No session, no needs Serializable

    @Setter(AccessLevel.NONE)
    @Column(name = "restaurant_id", nullable = false)
    private Integer restaurantId;

    @Column(name = "create_at", nullable = false)
    private LocalDate createAt;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    public Vote(Integer id, Integer restaurantId, LocalDate createAt, Integer userId) {
        super(id);
        this.restaurantId = restaurantId;
        this.createAt = createAt;
        this.userId = userId;
    }

    public Vote(Vote v) {
        this(v.id, v.restaurantId, v.createAt, v.userId);
    }

    @Override
    public String toString() {
        return "Vote(" + id + "):" + restaurantId + ", " + createAt.toString() + ", " + userId;
    }
}