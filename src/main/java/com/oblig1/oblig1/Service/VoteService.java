package com.oblig1.oblig1.Service;

import com.oblig1.oblig1.Model.Poll;
import com.oblig1.oblig1.Model.User;
import com.oblig1.oblig1.Model.Vote;
import com.oblig1.oblig1.Model.VoteOption;
import com.oblig1.oblig1.Repo.VoteRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoteService {

    @Autowired
    private VoteRepo voteRepo;

    // Save a vote
    public Vote saveVote(Vote vote) {
        return voteRepo.save(vote);
    }

    // Check if the user has already voted in the given poll
    public boolean hasUserVoted(Poll poll, User user) {
        return voteRepo.existsByPollAndVotedBy(poll, user);
    }
    public List<Vote> getVotesByPoll(Poll poll) {
        return voteRepo.findByPoll(poll);
    }
    

    // Count the number of votes for a specific option
    public long countVotesByOption(VoteOption option) {
        return voteRepo.countByOption(option);
    }

    // Count the number of votes in a specific poll
    public long countVotesByPoll(Poll poll) {
        return voteRepo.countByPoll(poll);
    }

    // Retrieve all votes
    public List<Vote> getAllVotes() {
        return voteRepo.findAll();
    }

    // Additional vote-related methods can be added as needed
}
