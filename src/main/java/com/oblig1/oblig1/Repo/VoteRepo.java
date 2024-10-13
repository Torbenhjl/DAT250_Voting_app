package com.oblig1.oblig1.Repo;

import com.oblig1.oblig1.Model.Poll;
import com.oblig1.oblig1.Model.User;
import com.oblig1.oblig1.Model.Vote;
import com.oblig1.oblig1.Model.VoteOption;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepo extends JpaRepository<Vote, Long> {

    // Check if a user has already voted in a specific poll
    boolean existsByPollAndVotedBy(Poll poll, User user);

    // Count the number of votes for a specific option
    Integer countByOption(VoteOption option);

    // Count the number of votes in a specific poll
    long countByPoll(Poll poll);
    
    // Find all votes by poll
    List<Vote> findByPoll(Poll poll);

    // Count the number of votes for a specific option by its id
    Integer countByOptionId(Long optionId);  // You can change 'int' to 'long' for consistency
}

