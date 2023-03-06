package com.akamai.homework.TechnicalTask.socialnetworkpost.repository;

import com.akamai.homework.TechnicalTask.socialnetworkpost.entity.SocialNetworkPost;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Repository
public interface SocialNetworkPostRepository extends JpaRepository<SocialNetworkPost, String> {
    List<SocialNetworkPost> findByPostDateInAndAuthorIn(
            Collection<LocalDateTime> postDate,
            Collection<String> author
    );

    List<SocialNetworkPost> findByPostDate(LocalDateTime postDate, Pageable pageable);

    List<SocialNetworkPost> findByPostDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<SocialNetworkPost> findByAuthorStartsWith(String author, Pageable pageable);

    List<SocialNetworkPost> findByViewCountBetween(int startCount, int endCount);

    List<SocialNetworkPost> findTop10ByOrderByViewCountDesc();

    List<SocialNetworkPost> findByContentContains(String content, Pageable pageable);
}
