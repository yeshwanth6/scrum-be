package com.example.exercise.service;

import com.example.exercise.Exception.RetrospectiveValidationException;
import com.example.exercise.model.Retrospective;
import com.example.exercise.repository.RetrospectiveRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RetrospectiveService {
  private final RetrospectiveRepository retrospectiveRepository;

  @Autowired
  public RetrospectiveService(RetrospectiveRepository retrospectiveRepository) {
    this.retrospectiveRepository = retrospectiveRepository;
  }

  public Retrospective createRetrospective(Retrospective retrospective) {
    validateRetrospective(retrospective);
    return retrospectiveRepository.save(retrospective);
  }

  public Page<Retrospective> getAllRetrospectives(int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    return retrospectiveRepository.findAll(pageable);
  }

  public List<Retrospective> searchRetrospectivesByDate(LocalDate date, int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    return retrospectiveRepository.findByDate(date, pageable).getContent();
  }

  void validateRetrospective(Retrospective retrospective) {
    if (retrospective.getDate() == null || retrospective.getParticipants().isEmpty()) {
      throw new RetrospectiveValidationException("Date and participants are required.");
    }
  }
}
