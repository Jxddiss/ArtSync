package com.artcorp.artsync.repos;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.List;

import com.artcorp.artsync.entity.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

    @DataJpaTest
    public class postReposTest {

        @Autowired
        private PostRepos postRepository;

        @Test
        public void testFindPostsByYear() {
            List<Post> posts = postRepository.findPostsByYear(2022); // Recherche pour l'année 2022 par exemple
            assertNotNull(posts);
            assertEquals(2, posts.size());
        }

        @Test
        public void testFindPostsByMonth() {
            List<Post> posts = postRepository.findPostsByMonth(3, 2022); // Recherche pour Mars 2022 par exemple
            assertNotNull(posts);
            assertEquals(3, posts.size());
        }

        @Test
        public void testFindPostsByDay() {
            List<Post> posts = postRepository.findPostsByDay(15, 3, 2022); // Recherche pour le 15 Mars 2022 par exemple
            assertNotNull(posts);
            assertEquals(1, posts.size());
        }
    }


