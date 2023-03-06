package com.akamai.homework.TechnicalTask.dto;

import com.akamai.homework.TechnicalTask.socialnetworkpost.dto.SocialNetworkPostDTO;
import com.akamai.homework.TechnicalTask.socialnetworkpost.entity.SocialNetworkPost;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class SocialNetworkPostDtoTest {


    @Test
    @DisplayName("Test mapOfDto")
    public void test_001() {
        // when
        List<SocialNetworkPostDTO> actual = SocialNetworkPostDTO.mapToDto(preparePosts());

        // then
        assertEquals("2022-04-08 12:30:22", actual.get(0).getPostDate());
        assertEquals("5", actual.get(1).getViewCount());
    }

    @Test
    @DisplayName("Test mapOfEntity")
    public void test_002() {
        // when
        List<SocialNetworkPost> actual = SocialNetworkPostDTO.mapToEntity(preparePostsDto());

        // then
        assertEquals(LocalDateTime.parse("2022-04-08T12:30:22"), actual.get(0).getPostDate());
        assertEquals(5, actual.get(1).getViewCount());
    }

    @Test
    @DisplayName("Test findFirstByAuthorAndPostDate")
    public void test_003() {
        // when
        Optional<SocialNetworkPostDTO> actual_1 = SocialNetworkPostDTO
                .findFirstByAuthorAndPostDate(
                        preparePostsDto(),
                        LocalDateTime.parse("2018-05-08T06:12:22"),
                        "Mikita"
                );

        Optional<SocialNetworkPostDTO> actual_2 = SocialNetworkPostDTO
                .findFirstByAuthorAndPostDate(
                        preparePostsDto(),
                        LocalDateTime.parse("2018-05-08T06:12:22"),
                        "Lagata"
                );

        // then
        assertTrue(actual_1.isPresent());
        assertFalse(actual_2.isPresent());
    }

    public List<SocialNetworkPost> preparePosts() {
        return List.of(
                new SocialNetworkPost(LocalDateTime.parse("2022-04-08T12:30:22"), "Agata", "bla bla", 3),
                new SocialNetworkPost(LocalDateTime.parse("2018-05-08T06:12:22"), "Mikita", "test test", 5)
        );
    }

    public List<SocialNetworkPostDTO> preparePostsDto() {
        return List.of(
                new SocialNetworkPostDTO("2022-04-08 12:30:22", "Agata", "bla bla", "3"),
                new SocialNetworkPostDTO("2018-05-08 06:12:22", "Mikita", "test test", "5"),
                new SocialNetworkPostDTO("2019-06-08 06:12:22", "Mike", "java git", "10"),
                new SocialNetworkPostDTO("2021-09-08 08:12:22", "Andzej", "selenium car", "51")
        );
    }
}
