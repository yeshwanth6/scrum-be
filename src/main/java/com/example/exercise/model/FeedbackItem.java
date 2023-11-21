package com.example.exercise.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "feedback-item")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String body;

  @Enumerated(EnumType.STRING)
  private FeedbackType feedbackType;

  @ManyToOne
  @JoinColumn(name = "retrospective_id")
  @JsonIgnore
  private Retrospective retrospective;
}
