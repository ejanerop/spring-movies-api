package com.janero.movies.domain.mapper;

import com.janero.movies.domain.dto.UserDTO;
import com.janero.movies.domain.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapper implements Mapper<User, UserDTO> {

    @Override
    public UserDTO mapToDTO(User source) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(source.getId());
        userDTO.setUsername(source.getUsername());
        return userDTO;
    }

}
