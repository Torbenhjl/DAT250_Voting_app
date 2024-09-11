package com.oblig1.oblig1.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oblig1.oblig1.Model.Poll;

public interface PollRepo extends JpaRepository<Poll, Long> {
    void deleteById(long id);
}
