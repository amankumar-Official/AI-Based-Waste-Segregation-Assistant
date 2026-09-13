package com.wastesegregation.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "waste_query")
public class WasteQuery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String item;
    private String category;

    @Column(length = 1000)
    private String disposalInstructions;

    @Column(length = 1000)
    private String reasoning;

    private LocalDateTime queriedAt;

    public WasteQuery() {}

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getItem() { return item; }
    public void setItem(String item) { this.item = item; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getDisposalInstructions() { return disposalInstructions; }
    public void setDisposalInstructions(String d) { this.disposalInstructions = d; }
    public String getReasoning() { return reasoning; }
    public void setReasoning(String reasoning) { this.reasoning = reasoning; }
    public LocalDateTime getQueriedAt() { return queriedAt; }
    public void setQueriedAt(LocalDateTime queriedAt) { this.queriedAt = queriedAt; }
}
