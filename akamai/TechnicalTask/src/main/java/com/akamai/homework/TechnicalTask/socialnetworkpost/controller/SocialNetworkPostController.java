package com.akamai.homework.TechnicalTask.socialnetworkpost.controller;

import com.akamai.homework.TechnicalTask.socialnetworkpost.dto.SocialNetworkPostDTO;
import com.akamai.homework.TechnicalTask.socialnetworkpost.entity.SocialNetworkPost;
import com.akamai.homework.TechnicalTask.socialnetworkpost.service.SocialNetworkPostService;
import com.akamai.homework.TechnicalTask.socialnetworkpost.util.Time;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping("socialnetworkpost")
public class SocialNetworkPostController {

    SocialNetworkPostService socialNetworkPostServiceService;

    SocialNetworkPostController(SocialNetworkPostService socialNetworkPostServiceService) {
        this.socialNetworkPostServiceService = socialNetworkPostServiceService;
    }
    @GetMapping(produces = "application/json")
    public List<SocialNetworkPostDTO> getAll(Pageable pageable) {
        Page<SocialNetworkPost> response = socialNetworkPostServiceService.fetchAllPosts(pageable);

        if (response.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return SocialNetworkPostDTO.mapToDto(response.stream().toList());
    }

    @GetMapping(produces = "application/json", value = "unique")
    public List<SocialNetworkPostDTO> getPostsByDateAndAuthor(
            @RequestParam("postDate") String postDate,
            @RequestParam("author") String author
    ) {
        if (postDate.isEmpty() || author.isEmpty()) {
            final String msg = "Param like postDate and author must not be empty";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);
        }

        LocalDateTime preparedPostDate = checkLocalDateTime(postDate);
        List<SocialNetworkPost> response = socialNetworkPostServiceService
                .fetchByDateAndAuthor(List.of(preparedPostDate), List.of(author));

        if (response.isEmpty()) {
            final String msg = "Entities by provided params do not exist";
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, msg);
        }

        return SocialNetworkPostDTO.mapToDto(response);
    }

    @GetMapping(produces = "application/json", value = "postdate")
    public List<SocialNetworkPostDTO> getPostsByDate(@RequestParam("postDate") String postDate, Pageable pageable) {
        if (postDate.isEmpty()) {
            final String msg = "Param like postDate must not be empty";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);
        }

        LocalDateTime preparedPostDate = checkLocalDateTime(postDate);
        List<SocialNetworkPost> response = socialNetworkPostServiceService.fetchByDate(preparedPostDate, pageable);

        if (response.isEmpty()) {
            final String msg = "Entities by provided param do not exist";
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, msg);
        }

        return SocialNetworkPostDTO.mapToDto(response);
    }

    @GetMapping(produces = "application/json", value = "range/postdate")
    public List<SocialNetworkPostDTO> getPostsByRangeDate(
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate
    ) {
        if (startDate.isEmpty() || endDate.isEmpty()) {
            final String msg = "Params like startDate and endDate must not be empty";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);
        }

        LocalDateTime preparedStartDate = checkLocalDateTime(startDate);
        LocalDateTime preparedEndDate = checkLocalDateTime(endDate);

        List<SocialNetworkPost> response = socialNetworkPostServiceService
                .fetchByRangeDate(preparedStartDate, preparedEndDate);

        if (response.isEmpty()) {
            final String msg = "Entities by provided params do not exist";
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, msg);
        }

        return SocialNetworkPostDTO.mapToDto(response);
    }

    @GetMapping(produces = "application/json", value = "author")
    public List<SocialNetworkPostDTO> getPostsByAuthor(@RequestParam("author") String author, Pageable pageable) {
        if (author.isEmpty()) {
            final String msg = "Param like author must not be empty";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);
        }

        List<SocialNetworkPost> response = socialNetworkPostServiceService.fetchByAuthor(author, pageable);

        if (response.isEmpty()) {
            final String msg = "Entities by provided param do not exist";
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, msg);
        }

        return SocialNetworkPostDTO.mapToDto(response);
    }

    @GetMapping(produces = "application/json", value = "highestviewcount")
    public List<SocialNetworkPostDTO> getViewHighestPosts() {
        List<SocialNetworkPost> response = socialNetworkPostServiceService.fetchViewHighestPosts();

        if (response.isEmpty()) {
            final String msg = "Entities do not exist";
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, msg);
        }

        return SocialNetworkPostDTO.mapToDto(response);
    }

    @GetMapping(produces = "application/json", value = "range/viewcount")
    public List<SocialNetworkPostDTO> getViewCountByRange(
            @RequestParam("startCount") String startCount,
            @RequestParam("endCount") String endCount
    ) {
        if (startCount.isEmpty() || endCount.isEmpty()) {
            final String msg = "Param like startCount and endCount must not be empty";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);
        }

        int preparedStartCount = Integer.parseInt(startCount);
        int preparedEndCount = Integer.parseInt(endCount);

        if (preparedEndCount == 0) {
            final String msg = "Param like endCount must not be 0";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);
        }

        List<SocialNetworkPost> response = socialNetworkPostServiceService
                .fetchViewCountByRange(preparedStartCount, preparedEndCount);

        if (response.isEmpty()) {
            final String msg = "Entities by provided params do not exist";
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, msg);
        }

        return SocialNetworkPostDTO.mapToDto(response);
    }

    @GetMapping(produces = "application/json", value = "content")
    public List<SocialNetworkPostDTO> getByContent(@RequestParam("content") String content, Pageable pageable) {
        if (content.isEmpty()) {
            final String msg = "Param like content must not be empty";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);
        }

        List<SocialNetworkPost> response = socialNetworkPostServiceService.fetchByContains(content, pageable);

        if (response.isEmpty()) {
            final String msg = "Entities by provided param do not exist";
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, msg);
        }

        return SocialNetworkPostDTO.mapToDto(response);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<String> saveAll(@RequestBody List<SocialNetworkPostDTO> socialNetworkPostsDTO) {
        if (socialNetworkPostsDTO == null || socialNetworkPostsDTO.isEmpty()) {
            final String msg = "SocialNetworkPostDTO must be provided";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);
        }

        socialNetworkPostServiceService.saveAll(socialNetworkPostsDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping(consumes = "application/json")
    public ResponseEntity<String> updateAll(@RequestBody List<SocialNetworkPostDTO> postToUpdate) {
        if (postToUpdate == null || postToUpdate.isEmpty()) {
            final String msg = "SocialNetworkPostDTO must be provided";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);
        }

        List<SocialNetworkPost> oldPost = socialNetworkPostServiceService.fetchByDateAndAuthor(
                postToUpdate.stream().map(dto -> Time.map(dto.getPostDate())).toList(),
                postToUpdate.stream().map(SocialNetworkPostDTO::getAuthor).toList()
        );

        if (oldPost.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        socialNetworkPostServiceService.updateContentAndViewCount(postToUpdate, oldPost);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping(consumes = "application/json")
    public ResponseEntity<String> deleteAll(@RequestBody List<SocialNetworkPostDTO> dataToRemove) {
        if (dataToRemove == null || dataToRemove.isEmpty()) {
            final String msg = "provided parameter must not be null or empty";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);
        }

        List<SocialNetworkPost> postToRemove = socialNetworkPostServiceService.fetchByDateAndAuthor(
                dataToRemove.stream().map(dto -> Time.map(dto.getPostDate())).toList(),
                dataToRemove.stream().map(SocialNetworkPostDTO::getAuthor).toList()
        );

        if (postToRemove.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        socialNetworkPostServiceService.deleteAll(postToRemove);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    private LocalDateTime checkLocalDateTime(String localDateTime) {
        try {
            return Time.map(localDateTime);
        } catch (DateTimeParseException e) {
            final String msg = "param post date doesn't match yyyy-MM-dd HH:mm:ss pattern";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);
        }
    }
}
