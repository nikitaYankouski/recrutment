package com.hudman.codingtask.csv.enity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "csv")
@NoArgsConstructor
@AllArgsConstructor
public class Csv {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "primary_key")
    private String primaryKey;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "updated_timestamp")
    private LocalDateTime updatedTimestamp;

    public Csv(String primaryKey, String name, String description, LocalDateTime updatedTimestamp) {
        this.primaryKey = primaryKey;
        this.name = name;
        this.description = description;
        this.updatedTimestamp = updatedTimestamp;
    }

}
