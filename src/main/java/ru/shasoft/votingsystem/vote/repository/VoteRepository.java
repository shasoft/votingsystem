package ru.shasoft.votingsystem.vote.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.shasoft.votingsystem.common.BaseRepository;
import ru.shasoft.votingsystem.vote.model.Vote;

import java.time.LocalDate;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {

    @Query("SELECT v FROM Vote v WHERE v.restaurantId = :restaurantId and v.createAt = :create_at and v.userId = :user_id")
    Optional<Vote> findVote(int restaurantId, LocalDate create_at, int user_id);

    @Query("DELETE FROM Vote v WHERE v.restaurantId = :restaurantId and v.createAt = :create_at and v.userId = :user_id")
    void deleteVote(int restaurantId, LocalDate create_at, int user_id);

    @Transactional
    default void like(Vote vote, int sign) {
        if (sign > 0) {
            Optional<Vote> optVote = findVote(vote.getRestaurantId(), vote.getCreateAt(), vote.getUserId());
            if (optVote.isEmpty()) {
                save(vote);
            }
        } else {
            deleteVote(vote.getRestaurantId(), vote.getCreateAt(), vote.getUserId());
        }
    }
}