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
    List<Vote> getByUser(int userId, int limit);
}