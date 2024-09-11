package com.oblig1.oblig1.Controller;

import com.oblig1.oblig1.Model.Poll;
import com.oblig1.oblig1.Model.User;
import com.oblig1.oblig1.Model.Vote;
import com.oblig1.oblig1.Model.VoteOption;
import com.oblig1.oblig1.Service.PollService;
import com.oblig1.oblig1.Service.UserService;
import com.oblig1.oblig1.Service.VoteService;

import jakarta.servlet.http.HttpSession;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:54751/")
@RestController
@RequestMapping("/api/polls")
public class PollController {

    @Autowired
    private PollService pollService;

    // Get all polls
    @GetMapping
    public List<Poll> getAllPolls() {
        return pollService.getAllPolls();
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
        poll.setPublishedAt(LocalDateTime.now());
        List<VoteOption> options = poll.getVoteOptions();

        if (options != null) {
            int order = 1;  // Start with order 1
            for (VoteOption option : options) {
                option.setPoll(poll);
                option.setPresentationOrder(order++);
            }
        }

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

        existingPoll.setQuestion(pollDetails.getQuestion());
        existingPoll.setValidUntil(pollDetails.getValidUntil());

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
}
