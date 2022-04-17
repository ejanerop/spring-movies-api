package com.janero.movies.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import com.janero.movies.domain.dto.UserDTO;
import com.janero.movies.domain.mapper.UserMapper;
import com.janero.movies.domain.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserMapperTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testExpectNullPointerNullUser() {
        assertThrows(NullPointerException.class, () -> {
            this.userMapper.mapToDTO(null);
        });
    }

    @Test
    public void testMaptoDTO() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");

        UserDTO userDTO = this.userMapper.mapToDTO(user);

        assertEquals(user.getId(), userDTO.getId());
        assertEquals(user.getUsername(), userDTO.getUsername());
    }

}
