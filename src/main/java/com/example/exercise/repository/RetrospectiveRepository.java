package com.example.exercise.repository;

import com.example.exercise.model.Retrospective;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RetrospectiveRepository extends JpaRepository<Retrospective, Long> {
  Page<Retrospective> findByDate(LocalDate date, Pageable pageable);
}
