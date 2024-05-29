package org.example.backend.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailTemplate {

    @Id
    @GeneratedValue
    private Long id;

    @Column(columnDefinition = "LONGTEXT")
    private String markup;

    public EmailTemplate(String markup) {
        this.markup = markup;
    }

}
