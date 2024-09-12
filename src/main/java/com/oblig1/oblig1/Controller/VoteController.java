package com.oblig1.oblig1.Controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oblig1.oblig1.Model.Poll;
import com.oblig1.oblig1.Model.User;
import com.oblig1.oblig1.Model.Vote;
import com.oblig1.oblig1.Model.VoteOption;
import com.oblig1.oblig1.Service.PollService;
import com.oblig1.oblig1.Service.UserService;
import com.oblig1.oblig1.Service.VoteService;

import jakarta.servlet.http.HttpSession;

@CrossOrigin(origins = "http://localhost:57030/", allowCredentials = "true")
@RestController
@RequestMapping("/api/votes")
public class VoteController {

    @Autowired
    private VoteService voteService;

    @Autowired
    private PollService pollService;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<Vote> getAllVotes() {
        return voteService.getAllVotes();
    }

    // Submit a new vote
    @PostMapping
public ResponseEntity<String> submitVote(@RequestBody Map<String, Long> voteData, HttpSession session) {
    Long optionId = voteData.get("optionId");
    Long pollId = voteData.get("pollId");
    LocalDateTime now = LocalDateTime.now();

    // Check if poll exists
    Poll poll = pollService.findPollById(pollId);
    if (poll == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Poll not found");
    }

    // Check if voting is allowed based on poll's time
    if (now.isBefore(poll.getPublishedAt()) || (poll.getValidUntil() != null && now.isAfter(poll.getValidUntil()))) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Voting is not allowed. Poll is either not published yet or has expired.");
    }

    // Assuming that you're storing the logged-in user in the session
    String username = (String) session.getAttribute("user");
    if (username == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
    }

    Optional<User> optionalUser = userService.findByUsername(username);
    if (optionalUser.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }
    User user = optionalUser.get();

    // Find the selected option
    VoteOption selectedOption = pollService.findOptionById(optionId);
    if (selectedOption == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vote option not found");
    }

    // Check if the user has already voted in this poll
    if (poll.isPrivate() && voteService.hasUserVoted(poll, user)) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You have already voted in this poll");
    }

    // Create and save the vote
    Vote vote = new Vote();
    vote.setPoll(poll);
    vote.setOption(selectedOption);
    vote.setVotedBy(user);
    vote.setVotedAt(LocalDateTime.now());  // Set votedAt here

    voteService.saveVote(vote);
    return ResponseEntity.status(HttpStatus.CREATED).body("Vote submitted successfully");
}

}
