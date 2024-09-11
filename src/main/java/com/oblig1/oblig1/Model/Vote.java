package com.oblig1.oblig1.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User votedBy;  // The user who voted

    @ManyToOne
    private VoteOption option;  // The option voted for

    @ManyToOne
    private Poll poll;  // The poll the vote is associated with

    // Getters and setters for votedBy, option, and poll

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getVotedBy() {
        return votedBy;
    }

    public void setVotedBy(User votedBy) {
        this.votedBy = votedBy;
    }

    public VoteOption getOption() {
        return option;
    }

    public void setOption(VoteOption option) {
        this.option = option;
    }

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }
}
