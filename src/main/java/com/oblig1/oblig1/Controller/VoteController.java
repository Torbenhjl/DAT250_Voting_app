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

@CrossOrigin(origins = "http://localhost:62608", allowCredentials = "true")
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
    public ResponseEntity<String> submitVote(@RequestBody Map<String, Object> voteData, HttpSession session) {
        Long optionId = ((Number) voteData.get("optionId")).longValue();
        Long pollId = ((Number) voteData.get("pollId")).longValue();
        boolean isUpvote = (boolean) voteData.get("isUpvote");  // Expect this from the frontend
        LocalDateTime now = LocalDateTime.now();
    
        // Check if poll exists
        Poll poll = pollService.findPollById(pollId);
        if (poll == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Poll not found");
        }
        for (VoteOption option : poll.getVoteOptions()) {
            option.setVoteCount(voteService.getVoteCountForOption(option.getId()));
        }
    
        // Check poll validity
        if (now.isBefore(poll.getPublishedAt()) || (poll.getValidUntil() != null && now.isAfter(poll.getValidUntil()))) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Voting is not allowed.");
        }
    
        String username = (String) session.getAttribute("user");
        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
        }
    
        Optional<User> optionalUser = userService.findByUsername(username);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        User user = optionalUser.get();
    
        VoteOption selectedOption = pollService.findOptionById(optionId);
        if (selectedOption == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vote option not found");
        }
    
        if (poll.isPrivate() && voteService.hasUserVoted(poll, user)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You have already voted in this poll");
        }
    
        if (selectedOption.getVoteCount() == null) {
            selectedOption.setVoteCount(0);  // Initialize vote count if null
        }
        
        if (isUpvote) {
            selectedOption.setVoteCount(selectedOption.getVoteCount() + 1);
        } else {
            selectedOption.setVoteCount(selectedOption.getVoteCount() - 1);
        }
        
    
        // Save the vote option and vote
        pollService.saveVoteOption(selectedOption);
    
        Vote vote = new Vote();
        vote.setPoll(poll);
        vote.setOption(selectedOption);
        vote.setVotedBy(user);
        vote.setVotedAt(LocalDateTime.now());
    
        voteService.saveVote(vote);
        return ResponseEntity.status(HttpStatus.CREATED).body("Vote submitted successfully");
    }
    

}
