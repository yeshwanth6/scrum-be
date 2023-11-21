package com.example.exercise.controller;

import com.example.exercise.model.FeedbackItem;
import com.example.exercise.model.Retrospective;
import com.example.exercise.service.FeedbackItemService;
import com.example.exercise.service.RetrospectiveService;
import java.time.LocalDate;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/retrospectives")
public class RetrospectiveController {

  private final RetrospectiveService retrospectiveService;
  private final FeedbackItemService feedbackItemService;

  @Autowired
  public RetrospectiveController(
      RetrospectiveService retrospectiveService, FeedbackItemService feedbackItemService) {
    this.retrospectiveService = retrospectiveService;
    this.feedbackItemService = feedbackItemService;
  }

  @PostMapping
  public ResponseEntity<Retrospective> createRetrospective(
      @RequestBody Retrospective retrospective) {

    Retrospective createdRetrospective = retrospectiveService.createRetrospective(retrospective);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdRetrospective);
  }

  @PostMapping("/{id}/feedback")
  public ResponseEntity<FeedbackItem> addFeedbackToRetrospective(
      @PathVariable Long id, @RequestBody FeedbackItem feedbackItem) {

    FeedbackItem createdFeedbackItem =
        feedbackItemService.addFeedbackToRetrospective(id, feedbackItem);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdFeedbackItem);
  }

  @PutMapping("/{retroId}/feedback/{feedbackId}")
  public ResponseEntity<FeedbackItem> updateFeedbackItem(
      @PathVariable("retroId") Long retrospectiveId,
      @PathVariable("feedbackId") Long feedbackId,
      @RequestBody FeedbackItem updatedFeedback) {
    FeedbackItem updatedItem =
        feedbackItemService.updateFeedbackItem(retrospectiveId, feedbackId, updatedFeedback);
    return ResponseEntity.ok(updatedItem);
  }

  @GetMapping
  public ResponseEntity<Page<Retrospective>> getAllRetrospectives(
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size) {
    Page<Retrospective> retrospectives = retrospectiveService.getAllRetrospectives(page, size);
    return ResponseEntity.ok(retrospectives);
  }

  @GetMapping(
      value = "/searchByDate",
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  public ResponseEntity<List<Retrospective>> searchRetrospectivesByDate(
      @RequestParam("date") String dateString,
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size,
      @RequestHeader(value = "Accept") String acceptHeader) {

    LocalDate searchDate = LocalDate.parse(dateString);
    List<Retrospective> retrospectives =
        retrospectiveService.searchRetrospectivesByDate(searchDate, page, size);

    MediaType mediaType;
    if (acceptHeader.contains(MediaType.APPLICATION_XML_VALUE)) {
      mediaType = MediaType.APPLICATION_XML;
    } else {
      mediaType = MediaType.APPLICATION_JSON;
    }

    return ResponseEntity.ok().contentType(mediaType).body(retrospectives);
  }

  @GetMapping("/{id}/feedback")
  public ResponseEntity<List<FeedbackItem>> getFeedbackItemsByRetrospectiveId(
      @PathVariable Long id) {

    List<FeedbackItem> feedbackItems = feedbackItemService.getFeedbackItemsByRetrospectiveId(id);
    return ResponseEntity.ok(feedbackItems);
  }
}
