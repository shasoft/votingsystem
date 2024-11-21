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
        uniqueConstraints = {@UniqueConstraint(columnNames = {"create_at", "user_id"}, name = "uk_vote_create_at_user_id")},
        indexes = @Index(name = "ik_vote_create_at_restaurant_id_user_id", columnList = "create_at, restaurant_id, user_id")
)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Vote extends BaseEntity implements HasId {

    @Column(name = "create_at", nullable = false)
    private LocalDate createAt;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "restaurant_id", nullable = false)
    private Integer restaurantId;

    public Vote(Integer id, LocalDate createAt, Integer userId, Integer restaurantId) {
        super(id);
        this.createAt = createAt;
        this.userId = userId;
        this.restaurantId = restaurantId;
    }

    public Vote(Vote v) {
        this(v.id, v.createAt, v.userId, v.restaurantId);
    }

    @Override
    public String toString() {
        return "Vote(" + id + "): " + createAt.toString() + ", " + userId + ", " + restaurantId;
    }
}