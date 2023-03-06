package com.akamai.homework.TechnicalTask.socialnetworkpost.dto;

import com.akamai.homework.TechnicalTask.socialnetworkpost.entity.SocialNetworkPost;
import com.akamai.homework.TechnicalTask.socialnetworkpost.util.Time;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SocialNetworkPostDTO {
    private String postDate;
    private String author;
    private String content;
    private String viewCount;

    public SocialNetworkPostDTO(LocalDateTime postDate, String author, String content, int viewCount) {
        this.postDate = Time.map(postDate);
        this.author = author;
        this.content = content;
        this.viewCount = String.valueOf(viewCount);
    }

    public static List<SocialNetworkPostDTO> mapToDto(List<SocialNetworkPost> socialNetworkPosts) {
        return socialNetworkPosts
                .stream()
                .map(post ->
                        new SocialNetworkPostDTO(
                                post.getPostDate(), post.getAuthor(), post.getContent(), post.getViewCount()
                        )
                )
                .toList();
    }

    public static List<SocialNetworkPost> mapToEntity(List<SocialNetworkPostDTO> socialNetworkPostsDTO) {
        return socialNetworkPostsDTO
                .stream()
                .map(dto ->
                        new SocialNetworkPost(
                                Time.map(dto.getPostDate()),
                                dto.author,
                                dto.content,
                                Integer.parseInt(dto.getViewCount())
                        )
                )
                .toList();
    }

    public static SocialNetworkPost mapToEntity(SocialNetworkPostDTO dto) {
        return new SocialNetworkPost(
                Time.map(dto.postDate),
                dto.getAuthor(),
                dto.getContent(),
                Integer.parseInt(dto.getViewCount())
        );
    }

    public static Optional<SocialNetworkPostDTO> findFirstByAuthorAndPostDate(
            List<SocialNetworkPostDTO> list, LocalDateTime postDate, String author
    ) {
        String date = postDate.toString().replace("T", " ");
        return list.stream().filter(post -> post.postDate.equals(date) && post.author.equals(author)).findFirst();
    }
}
