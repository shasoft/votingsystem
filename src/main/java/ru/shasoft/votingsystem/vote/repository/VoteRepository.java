package ru.shasoft.votingsystem.vote.repository;

import org.springframework.data.jpa.repository.Modifying;
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

    @Modifying
    @Transactional
    @Query("DELETE FROM Vote v WHERE v.restaurantId = :restaurantId and v.createAt = :create_at and v.userId = :user_id")
    int deleteVote(int restaurantId, LocalDate create_at, int user_id);

    @Modifying
    @Transactional
    @Query("UPDATE Menu m SET m.votes = m.votes + :votes WHERE m.restaurant.id = :restaurantId and m.createAt = :create_at")
    void updateVotes(int restaurantId, LocalDate create_at, int votes);

    @Transactional
    default Vote like(int restaurantId, LocalDate create_at, int user_id) {
        Vote ret = null;
        Optional<Vote> optVote = findVote(restaurantId, create_at, user_id);
        if (optVote.isEmpty()) {
            ret = save(new Vote(null, restaurantId, create_at, user_id));
            updateVotes(restaurantId, create_at, 1);
        }
        return ret;
    }

    @Transactional
    default void unlike(int restaurantId, LocalDate create_at, int user_id) {
        final int updates = deleteVote(restaurantId, create_at, user_id);
        if (updates != 0) {
            updateVotes(restaurantId, create_at, -1);
        }
    }
}