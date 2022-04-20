package com.janero.movies.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import javax.validation.ValidationException;
import com.janero.movies.domain.model.User;
import com.janero.movies.domain.request.CreateUserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureTestDatabase
public class UserServiceTests {

    @Autowired
    private UserService userService;

    @Test
    public void contextLoads() {}

    @Test
    public void testSaveNullUser() {
        assertThrows(ValidationException.class, () -> userService.create(null));
    }

    @Test
    public void testSaveEmptyUser() {
        assertThrows(ValidationException.class, () -> userService.create(new CreateUserRequest()));
    }

    @Test
    public void testSaveExistingUser() {
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("test");
        request.setPassword("test");
        request.setRePassword("test");
        userService.create(request);
        assertThrows(ValidationException.class, () -> userService.create(request));
    }

    @Test
    public void testSaveUserNotMatchingPassword() {
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("test");
        request.setPassword("test");
        request.setRePassword("test2");
        assertThrows(ValidationException.class, () -> userService.create(request));
    }

    @Test
    public void testSaveUserEmptyPassword() {
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("test");
        request.setPassword("");
        request.setRePassword("");
        assertThrows(ValidationException.class, () -> userService.create(request));
    }

    @Test
    public void testSaveUserNullPassword() {
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("test");
        assertThrows(ValidationException.class, () -> userService.create(request));
    }

    @Test
    public void testSaveUser() {
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("testUser");
        request.setPassword("test");
        request.setRePassword("test");
        User created = userService.create(request);

        assertAll(() -> assertThat(created).isNotNull(),
                () -> assertThat(created.getId()).isPositive(),
                () -> assertThat(created.getUsername()).isEqualTo(request.getUsername()));
    }
}
