package com.example.exercise.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import com.example.exercise.model.FeedbackItem;
import com.example.exercise.model.FeedbackType;
import com.example.exercise.model.Retrospective;
import com.example.exercise.repository.FeedbackItemRepository;
import com.example.exercise.repository.RetrospectiveRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class FeedbackItemServiceTest {

  @Mock private FeedbackItemRepository feedbackItemRepository;

  @Mock private RetrospectiveRepository retrospectiveRepository;

  @InjectMocks private FeedbackItemService feedbackItemService;

  private Retrospective retrospective;
  private FeedbackItem feedbackItem;

  @BeforeEach
  public void setUp() {
    retrospective = new Retrospective();
    retrospective.setId(1L);
    retrospective.setFeedbackItems(new ArrayList<>());

    feedbackItem = new FeedbackItem();
    feedbackItem.setId(1L);
    feedbackItem.setBody("Sample Body");
    feedbackItem.setFeedbackType(FeedbackType.POSITIVE);
  }

  @Test
  public void addFeedbackToRetrospective_Success() {
    when(retrospectiveRepository.findById(anyLong())).thenReturn(Optional.of(retrospective));
    when(feedbackItemRepository.save(any(FeedbackItem.class))).thenReturn(feedbackItem);

    FeedbackItem result = feedbackItemService.addFeedbackToRetrospective(1L, feedbackItem);

    verify(retrospectiveRepository, times(1)).findById(anyLong());
    verify(feedbackItemRepository, times(1)).save(any(FeedbackItem.class));

    assertEquals(feedbackItem.getBody(), result.getBody());
    assertEquals(feedbackItem.getFeedbackType(), result.getFeedbackType());
  }

  @Test
  public void updateFeedbackItem_Success() {
    retrospective.getFeedbackItems().add(feedbackItem);
    when(retrospectiveRepository.findById(anyLong())).thenReturn(Optional.of(retrospective));
    when(feedbackItemRepository.save(any(FeedbackItem.class))).thenReturn(feedbackItem);

    FeedbackItem updatedFeedback = new FeedbackItem();
    updatedFeedback.setBody("Updated Body");
    updatedFeedback.setFeedbackType(FeedbackType.NEGATIVE);

    FeedbackItem result = feedbackItemService.updateFeedbackItem(1L, 1L, updatedFeedback);

    verify(retrospectiveRepository, times(1)).findById(anyLong());
    verify(feedbackItemRepository, times(1)).save(any(FeedbackItem.class));

    assertEquals(updatedFeedback.getBody(), result.getBody());
    assertEquals(updatedFeedback.getFeedbackType(), result.getFeedbackType());
  }

  @Test
  public void getFeedbackItemsByRetrospectiveId_Success() {
    retrospective.getFeedbackItems().add(feedbackItem);
    when(retrospectiveRepository.findById(anyLong())).thenReturn(Optional.of(retrospective));

    List<FeedbackItem> result = feedbackItemService.getFeedbackItemsByRetrospectiveId(1L);

    verify(retrospectiveRepository, times(1)).findById(anyLong());

    assertEquals(1, result.size());
    assertEquals(feedbackItem.getId(), result.get(0).getId());
  }
}
