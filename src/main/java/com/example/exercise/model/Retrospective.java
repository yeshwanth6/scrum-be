package com.example.exercise.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

@Entity
@Table(name = "retrospective")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Retrospective {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String summary;
  @NonNull private LocalDate date;
  @NonNull @ElementCollection private List<String> participants = new ArrayList<>();

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "retrospective")
  private List<FeedbackItem> feedbackItems = new ArrayList<>();
}
