package com.example.exercise.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.exercise.model.Retrospective;
import com.example.exercise.repository.RetrospectiveRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
public class RetrospectiveServiceTest {

  @Mock private RetrospectiveRepository retrospectiveRepository;

  @InjectMocks private RetrospectiveService retrospectiveService;

  @Test
  public void createRetrospective_ValidInput_ReturnsRetrospective() {
    // Create a sample Retrospective object
    Retrospective retrospective = new Retrospective();
    retrospective.setDate(LocalDate.now());
    List<String> participants = new ArrayList<>();
    participants.add("Participant 1");
    retrospective.setParticipants(participants);

    // Mock the repository save method
    when(retrospectiveRepository.save(any())).thenReturn(retrospective);

    // Call the service method
    Retrospective result = retrospectiveService.createRetrospective(retrospective);

    // Verify that the repository save method was called and returned the correct result
    verify(retrospectiveRepository, times(1)).save(any());
    assertNotNull(result);
    assertEquals(retrospective.getDate(), result.getDate());
    assertEquals(retrospective.getParticipants(), result.getParticipants());
  }

  @Test
  public void getAllRetrospectives_ReturnsRetrospectivePage() {
    // Create a sample Page object
    List<Retrospective> retrospectives = new ArrayList<>();
    Page<Retrospective> page = new PageImpl<>(retrospectives);
    PageRequest pageable = PageRequest.of(0, 10);

    // Mock the repository findAll method
    when(retrospectiveRepository.findAll(pageable)).thenReturn(page);

    // Call the service method
    Page<Retrospective> result = retrospectiveService.getAllRetrospectives(0, 10);

    // Verify that the repository findAll method was called and returned the correct result
    verify(retrospectiveRepository, times(1)).findAll(pageable);
    assertNotNull(result);
    assertEquals(retrospectives.size(), result.getContent().size());
  }

  @Test
  public void searchRetrospectivesByDate_ReturnsRetrospectiveList() {
    // Create a sample LocalDate and Page object
    LocalDate date = LocalDate.now();
    List<Retrospective> retrospectives = new ArrayList<>();
    Page<Retrospective> page = new PageImpl<>(retrospectives);
    PageRequest pageable = PageRequest.of(0, 10);

    // Mock the repository findByDate method
    when(retrospectiveRepository.findByDate(eq(date), eq(pageable))).thenReturn(page);

    // Call the service method
    List<Retrospective> result = retrospectiveService.searchRetrospectivesByDate(date, 0, 10);

    // Verify that the repository findByDate method was called and returned the correct result
    verify(retrospectiveRepository, times(1)).findByDate(eq(date), eq(pageable));
    assertNotNull(result);
    assertEquals(retrospectives.size(), result.size());
  }

  @Test
  public void validateRetrospective_ValidRetrospective_NoExceptionThrown() {
    Retrospective retrospective = new Retrospective();
    retrospective.setDate(LocalDate.now());
    List<String> participants = new ArrayList<>();
    participants.add("Participant 1");
    retrospective.setParticipants(participants);

    assertDoesNotThrow(() -> retrospectiveService.validateRetrospective(retrospective));
  }

  @Test
  public void validateRetrospective_NullDate_ThrowsIllegalArgumentException() {
    Retrospective retrospective = new Retrospective();
    List<String> participants = new ArrayList<>();
    participants.add("Participant 1");
    retrospective.setParticipants(participants);

    assertThrows(
        IllegalArgumentException.class,
        () -> retrospectiveService.validateRetrospective(retrospective));
  }

  @Test
  public void validateRetrospective_EmptyParticipants_ThrowsIllegalArgumentException() {
    Retrospective retrospective = new Retrospective();
    retrospective.setDate(LocalDate.now());

    assertThrows(
        IllegalArgumentException.class,
        () -> retrospectiveService.validateRetrospective(retrospective));
  }
}
