package com.akamai.homework.TechnicalTask.socialnetworkpost.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "social_network_post")
@AllArgsConstructor
public class SocialNetworkPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "post_date")
    private LocalDateTime postDate;

    @Column(name = "author")
    private String author;

    @Column(name = "content")
    private String content;

    @Column(name = "view_count")
    private int viewCount;

    public SocialNetworkPost(LocalDateTime postDate, String author, String content, int viewCount) {
        this.postDate = postDate;
        this.author = author;
        this.content = content;
        this.viewCount = viewCount;
    }

    public SocialNetworkPost() {

    }
}
