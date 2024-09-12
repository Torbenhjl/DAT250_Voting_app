package com.oblig1.oblig1.Service;

import com.oblig1.oblig1.Model.Poll;
import com.oblig1.oblig1.Model.VoteOption;
import com.oblig1.oblig1.Repo.PollRepo;
import com.oblig1.oblig1.Repo.VoteOptionRepo;
import com.oblig1.oblig1.Repo.VoteRepo;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PollService {

    @Autowired
    private PollRepo pollRepo;

    @Autowired
    private VoteOptionRepo voteOptionRepo;

    @Autowired
    private  VoteRepo voteRepo;
    // Create and save a poll
    public Poll createPoll(Poll poll) {
        return pollRepo.save(poll);
    }

    // Save or update a poll
    public Poll savePoll(Poll poll) {
        return pollRepo.save(poll);
    }

    // Get a poll by ID
    public Optional<Poll> getPollById(Long pollId) {
        return pollRepo.findById(pollId);
    }

    // Find a poll by ID or throw an EntityNotFoundException
    public Poll findPollById(Long pollId) {
        return pollRepo.findById(pollId).orElseThrow(() -> 
            new EntityNotFoundException("Poll with ID " + pollId + " not found"));
    }

    // Get all polls
    public List<Poll> getAllPolls() {
        return pollRepo.findAll();
    }

    // Find a vote option by its ID
    public VoteOption findOptionById(Long optionId) {
        return voteOptionRepo.findById(optionId).orElseThrow(() -> 
            new EntityNotFoundException("Option with ID " + optionId + " not found"));
    }

    // Delete a poll by ID
    public void deletePoll(Long pollId) {
        if (!pollRepo.existsById(pollId)) {
            throw new EntityNotFoundException("Poll with ID " + pollId + " not found");
        }
        pollRepo.deleteById(pollId);
    }


 
    public Poll getPollWithVotes(Long pollId) {
        Poll poll = pollRepo.findById(pollId).orElseThrow(() -> new RuntimeException("Poll not found"));

        // Count votes for each vote option
        for (VoteOption option : poll.getVoteOptions()) {
            Integer count = voteRepo.countByOption(option);
            option.setVoteCount(count);  // Set the vote count for this option
        }

        return poll;
    }
    public void incrementVoteCount(Long optionId) {
        VoteOption option = voteOptionRepo.findById(optionId)
                .orElseThrow(() -> new EntityNotFoundException("Option not found"));
        
        int newCount = option.getVoteCount() + 1;
        option.setVoteCount(newCount);
    
        voteOptionRepo.save(option);  // Ensure the updated count is saved to the DB
    }
    

    // Additional poll management methods can be added if needed
}
