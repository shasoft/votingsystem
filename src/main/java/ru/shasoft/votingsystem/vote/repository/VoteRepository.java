package ru.shasoft.votingsystem.vote.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.shasoft.votingsystem.common.BaseRepository;
import ru.shasoft.votingsystem.vote.model.Vote;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {

    @Query("SELECT v FROM Vote v WHERE v.createAt = :createAt and v.userId = :userId")
    Optional<Vote> findVote(LocalDate createAt, int userId);

    @Query("SELECT v FROM Vote v WHERE v.userId = :userId ORDER BY v.createAt DESC LIMIT :limit")
    List<Vote> getByUser(int userId,int limit);
    /*
    @Modifying
    @Transactional
    @Query("UPDATE Menu m SET m.votes = m.votes + :votes WHERE m.restaurant.id = :restaurantId and m.cookingAt = :createAt")
    void updateVotes(int restaurantId, LocalDate createAt, int votes);

    @Transactional
    default Vote like(int restaurantId, LocalDate createAt, int userId) {
        Vote ret = null;
        Optional<Vote> optVote = findVote(restaurantId, createAt, userId);
        if (optVote.isEmpty()) {
            ret = save(new Vote(null, restaurantId, createAt, userId));
            updateVotes(restaurantId, createAt, 1);
        }
        return ret;
    }

    @Transactional
    default void unlike(int restaurantId, LocalDate createAt, int userId) {
        final int updates = deleteVote(restaurantId, createAt, userId);
        if (updates != 0) {
            updateVotes(restaurantId, createAt, -1);
        }
    }
    */
}