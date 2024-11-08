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

    @Transactional
    default Vote like(int restaurantId, LocalDate create_at, int user_id) {
        Optional<Vote> optVote = findVote(restaurantId, create_at, user_id);
        if (optVote.isEmpty()) {
            return save(new Vote(null, restaurantId, create_at, user_id));
        }
        return null;
    }

    @Transactional
    default void unlike(int restaurantId, LocalDate create_at, int user_id) {
        Optional<Vote> optVote = findVote(restaurantId, create_at, user_id);
        optVote.ifPresent(vote -> delete(vote.id()));
    }
}