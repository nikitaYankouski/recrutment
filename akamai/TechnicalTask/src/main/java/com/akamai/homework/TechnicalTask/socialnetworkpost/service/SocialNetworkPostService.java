package com.akamai.homework.TechnicalTask.socialnetworkpost.service;

import com.akamai.homework.TechnicalTask.socialnetworkpost.dto.SocialNetworkPostDTO;
import com.akamai.homework.TechnicalTask.socialnetworkpost.entity.SocialNetworkPost;
import com.akamai.homework.TechnicalTask.socialnetworkpost.repository.SocialNetworkPostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class SocialNetworkPostService {

    private SocialNetworkPostRepository socialNetworkPostRepository;

    private final Logger log = LoggerFactory.getLogger(SocialNetworkPostService.class);

    SocialNetworkPostService(SocialNetworkPostRepository socialNetworkPostRepository) {
        this.socialNetworkPostRepository = socialNetworkPostRepository;
    }

    public Page<SocialNetworkPost> fetchAllPosts(Pageable pageable) {
        return socialNetworkPostRepository.findAll(pageable);
    }

    public List<SocialNetworkPost> fetchByDateAndAuthor(
            Collection<LocalDateTime> postDate, Collection<String> author
    ) {
        return socialNetworkPostRepository.findByPostDateInAndAuthorIn(postDate, author);
    }

    public List<SocialNetworkPost> fetchByDate(LocalDateTime postDate, Pageable pageable) {
        return socialNetworkPostRepository.findByPostDate(postDate, pageable);
    }

    public List<SocialNetworkPost> fetchByRangeDate(LocalDateTime startDate, LocalDateTime endDate) {
        return socialNetworkPostRepository.findByPostDateBetween(startDate, endDate);
    }

    public List<SocialNetworkPost> fetchByAuthor(String author, Pageable pageable) {
        return socialNetworkPostRepository.findByAuthorStartsWith(author, pageable);
    }

    public List<SocialNetworkPost> fetchViewHighestPosts() {
        return socialNetworkPostRepository.findTop10ByOrderByViewCountDesc();
    }

    public List<SocialNetworkPost> fetchViewCountByRange(int startCount, int endCount) {
        return socialNetworkPostRepository.findByViewCountBetween(startCount, endCount);
    }

    public List<SocialNetworkPost> fetchByContains(String contain, Pageable pageable) {
        return socialNetworkPostRepository.findByContentContains(contain, pageable);
    }

    public void saveAll(List<SocialNetworkPostDTO> socialNetworkPostsDTO) {
        List<SocialNetworkPost> preparedSocialNetworkPosts = SocialNetworkPostDTO.mapToEntity(socialNetworkPostsDTO);

        socialNetworkPostRepository.saveAll(preparedSocialNetworkPosts);
        log.info("Saved " + preparedSocialNetworkPosts.size() + " posts");
    }

    public void updateContentAndViewCount(List<SocialNetworkPostDTO> newPosts, List<SocialNetworkPost> oldPosts) {
        List<SocialNetworkPost> preparedPostsToSave = oldPosts.stream().map(oldPost -> {

            Optional<SocialNetworkPostDTO> dto = SocialNetworkPostDTO
                    .findFirstByAuthorAndPostDate(newPosts, oldPost.getPostDate(), oldPost.getAuthor());

            if (dto.isPresent()) {
                SocialNetworkPost newData = SocialNetworkPostDTO.mapToEntity(dto.get());
                oldPost.setContent(newData.getContent());
                oldPost.setViewCount(newData.getViewCount());
            }
            return oldPost;
        }).toList();

        socialNetworkPostRepository.saveAll(preparedPostsToSave);
        log.info("Updated " + preparedPostsToSave.size() + " posts");
    }

    public void deleteAll(List<SocialNetworkPost> postToRemove) {
        socialNetworkPostRepository.deleteAll(postToRemove);
        log.info("Removed " + postToRemove.size() + " posts");
    }
}
