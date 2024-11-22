package ru.shasoft.votingsystem.vote.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.shasoft.votingsystem.common.BaseRepository;
import ru.shasoft.votingsystem.vote.model.Vote;
import ru.shasoft.votingsystem.vote.object.VoteCount;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {

    @Query("SELECT v FROM Vote v WHERE v.createAt = :createAt and v.userId = :userId")
    Optional<Vote> findVote(LocalDate createAt, int userId);

    @Query("SELECT v FROM Vote v WHERE v.userId = :userId ORDER BY v.createAt DESC LIMIT :limit")
    List<Vote> getByUser(int userId, int limit);

    @Query("SELECT new ru.shasoft.votingsystem.vote.object.VoteCount(v.createAt, v.restaurantId, COUNT(v)) FROM Vote v WHERE v.restaurantId = :restaurantId GROUP BY v.createAt ORDER BY v.createAt DESC")
    List<VoteCount> getByRestaurant(int restaurantId);

    @Query("SELECT new ru.shasoft.votingsystem.vote.object.VoteCount(v.createAt, v.restaurantId, COUNT(v)) FROM Vote v WHERE v.createAt = :date GROUP BY v.restaurantId ORDER BY COUNT(v) DESC")
    List<VoteCount> getByDate(LocalDate date);
}