package com.example.exercise.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.exercise.model.FeedbackItem;
import com.example.exercise.model.Retrospective;
import com.example.exercise.service.FeedbackItemService;
import com.example.exercise.service.RetrospectiveService;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class RetrospectiveControllerTest {

  @Mock private RetrospectiveService retrospectiveService;

  @Mock private FeedbackItemService feedbackItemService;

  @InjectMocks private RetrospectiveController retrospectiveController;

  @Test
  void createRetrospective_ValidInput_ReturnsCreatedResponse() {
    Retrospective createdRetrospective = new Retrospective();
    when(retrospectiveService.createRetrospective(any())).thenReturn(createdRetrospective);

    Retrospective retrospective = new Retrospective();
    ResponseEntity<Retrospective> response =
        retrospectiveController.createRetrospective(retrospective);

    assertEquals(createdRetrospective, response.getBody());
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
  }

  @Test
  void addFeedbackToRetrospective_ValidInput_ReturnsCreatedResponse() {
    FeedbackItem createdFeedbackItem = new FeedbackItem();
    when(feedbackItemService.addFeedbackToRetrospective(anyLong(), any()))
        .thenReturn(createdFeedbackItem);

    FeedbackItem feedbackItem = new FeedbackItem();
    ResponseEntity<FeedbackItem> response =
        retrospectiveController.addFeedbackToRetrospective(1L, feedbackItem);

    assertEquals(createdFeedbackItem, response.getBody());
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
  }

  @Test
  void updateFeedbackItem_ValidInput_ReturnsOkResponse() {
    FeedbackItem updatedFeedbackItem = new FeedbackItem();
    when(feedbackItemService.updateFeedbackItem(anyLong(), anyLong(), any()))
        .thenReturn(updatedFeedbackItem);

    FeedbackItem updatedFeedback = new FeedbackItem();
    ResponseEntity<FeedbackItem> response =
        retrospectiveController.updateFeedbackItem(1L, 1L, updatedFeedback);

    assertEquals(updatedFeedbackItem, response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  void getAllRetrospectives_ReturnsRetrospectivePage() {
    Page<Retrospective> retrospectivePage = mock(Page.class);
    when(retrospectiveService.getAllRetrospectives(anyInt(), anyInt()))
        .thenReturn(retrospectivePage);

    ResponseEntity<Page<Retrospective>> response =
        retrospectiveController.getAllRetrospectives(0, 10);

    assertEquals(retrospectivePage, response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  void searchRetrospectivesByDate_ValidInput_ReturnsRetrospectives() {
    LocalDate searchDate = LocalDate.now();
    List<Retrospective> retrospectives = Arrays.asList(new Retrospective(), new Retrospective());
    when(retrospectiveService.searchRetrospectivesByDate(any(LocalDate.class), anyInt(), anyInt()))
        .thenReturn(retrospectives);

    ResponseEntity<List<Retrospective>> response =
        retrospectiveController.searchRetrospectivesByDate(
            searchDate.toString(), 0, 10, MediaType.APPLICATION_JSON_VALUE);

    assertEquals(retrospectives, response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  void getFeedbackItemsByRetrospectiveId_ValidInput_ReturnsFeedbackItems() {
    List<FeedbackItem> feedbackItems = Arrays.asList(new FeedbackItem(), new FeedbackItem());
    when(feedbackItemService.getFeedbackItemsByRetrospectiveId(anyLong()))
        .thenReturn(feedbackItems);

    ResponseEntity<List<FeedbackItem>> response =
        retrospectiveController.getFeedbackItemsByRetrospectiveId(1L);

    assertEquals(feedbackItems, response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }
}
