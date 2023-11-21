package com.example.exercise.service;

import com.example.exercise.Exception.FeedbackItemNotFoundException;
import com.example.exercise.Exception.RetrospectiveNotFoundException;
import com.example.exercise.model.FeedbackItem;
import com.example.exercise.model.Retrospective;
import com.example.exercise.repository.FeedbackItemRepository;
import com.example.exercise.repository.RetrospectiveRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FeedbackItemService {
  private final FeedbackItemRepository feedbackItemRepository;
  private final RetrospectiveRepository retrospectiveRepository;

  @Autowired
  public FeedbackItemService(
      FeedbackItemRepository feedbackItemRepository,
      RetrospectiveRepository retrospectiveRepository) {
    this.feedbackItemRepository = feedbackItemRepository;
    this.retrospectiveRepository = retrospectiveRepository;
  }

  public FeedbackItem addFeedbackToRetrospective(Long retrospectiveId, FeedbackItem feedbackItem) {
    Retrospective retrospective =
        retrospectiveRepository
            .findById(retrospectiveId)
            .orElseThrow(() -> new RetrospectiveNotFoundException("Retrospective not found."));

    feedbackItem.setRetrospective(retrospective);
    retrospective.getFeedbackItems().add(feedbackItem);
    log.info("Adding feedback item to retrospective {} ", retrospective.getName());

    return feedbackItemRepository.save(feedbackItem);
  }

  public FeedbackItem updateFeedbackItem(
      Long retrospectiveId, Long feedbackId, FeedbackItem updatedFeedback) {
    Retrospective retrospective =
        retrospectiveRepository
            .findById(retrospectiveId)
            .orElseThrow(() -> new RetrospectiveNotFoundException("Retrospective not found."));

    FeedbackItem feedbackItem =
        retrospective.getFeedbackItems().stream()
            .filter(item -> item.getId().equals(feedbackId))
            .findFirst()
            .orElseThrow(() -> new FeedbackItemNotFoundException("Feedback item not found."));

    feedbackItem.setBody(updatedFeedback.getBody());
    feedbackItem.setFeedbackType(updatedFeedback.getFeedbackType());
    log.info("Updating feedback item to retrospective {} ", retrospective.getName());

    return feedbackItemRepository.save(feedbackItem);
  }

  public List<FeedbackItem> getFeedbackItemsByRetrospectiveId(Long retrospectiveId) {
    Retrospective retrospective =
        retrospectiveRepository
            .findById(retrospectiveId)
            .orElseThrow(() -> new RetrospectiveNotFoundException("Retrospective not found."));
    return retrospective.getFeedbackItems();
  }
}
