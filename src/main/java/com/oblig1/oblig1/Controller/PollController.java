package com.oblig1.oblig1.Controller;

import com.oblig1.oblig1.Model.Poll;
import com.oblig1.oblig1.Model.Vote;
import com.oblig1.oblig1.Model.VoteOption;
import com.oblig1.oblig1.Service.PollService;
import com.oblig1.oblig1.Service.VoteService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:57030/", allowCredentials = "true")
@RestController
@RequestMapping("/api/polls")
public class PollController {

    @Autowired
    private PollService pollService;

    @Autowired
    private VoteService voteService;

    // Get all polls
    @GetMapping
    public List<Poll> getAllPolls() {
        List<Poll> polls = pollService.getAllPolls();
        for (Poll poll : polls) {
            pollService.getPollWithVotes(poll.getId());
        }
        return polls;
    }

    // Get a single poll by its ID
    @GetMapping("/{pollId}")
    public ResponseEntity<Poll> getPollById(@PathVariable Long pollId) {
        Optional<Poll> optPoll = pollService.getPollById(pollId);
        if (optPoll.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(optPoll.get());
    }

    // Create a new poll
    @PostMapping
    public ResponseEntity<Poll> createPoll(@RequestBody Poll poll) {
        // Set the current timestamp as publishedAt
        System.out.println("Received Poll: " + poll.isPrivate());  // Log the value of isPrivate
        poll.setPublishedAt(LocalDateTime.now());
    
        List<VoteOption> options = poll.getVoteOptions();
    
        // Set the order of each option and assign them to the poll
        if (options != null) {
            int order = 1;
            for (VoteOption option : options) {
                option.setPoll(poll);
                option.setPresentationOrder(order++);
            }
        }
    
        // Log the values to make sure they're correct before saving
        System.out.println("Poll Data: " + poll);
    
        // Save the poll along with the validUntil and isPrivate flag
        Poll savedPoll = pollService.savePoll(poll);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPoll);
    }
    

    // Update an existing poll
    @PutMapping("/{pollId}")
    public ResponseEntity<Poll> updatePoll(@PathVariable Long pollId, @RequestBody Poll pollDetails) {
        Poll existingPoll = pollService.findPollById(pollId);
        if (existingPoll == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // Update poll details, including validUntil and isPrivate
        existingPoll.setQuestion(pollDetails.getQuestion());
        existingPoll.setValidUntil(pollDetails.getValidUntil()); // Update the validUntil timestamp
        existingPoll.setPrivate(pollDetails.isPrivate());   // Update the isPrivate flag

        Poll updatedPoll = pollService.savePoll(existingPoll);
        return ResponseEntity.ok(updatedPoll);
    }

    // Delete a poll
    @DeleteMapping("/{pollId}")
    public ResponseEntity<String> deletePoll(@PathVariable Long pollId) {
        Optional<Poll> optPoll = pollService.getPollById(pollId);
        if (optPoll.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Poll not found");
        }

        pollService.deletePoll(pollId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Poll deleted successfully");
    }

    // Get votes for a poll
    @GetMapping("/polls/{pollId}/votes")
    public List<Vote> getVotesForPoll(@PathVariable Long pollId) {
        Poll poll = pollService.findPollById(pollId);
        return voteService.getVotesByPoll(poll);
    }
}
