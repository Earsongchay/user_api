package com.example.user_api.Service;

import com.example.user_api.Exception.BadRequestException;
import com.example.user_api.Interface.UserImpl;
import com.example.user_api.Model.PaginateRequest;
import com.example.user_api.Model.PaginateResponse;
import com.example.user_api.Model.User;
import com.example.user_api.Repository.UserRepository;
import com.example.user_api.Specification.UserSpecification;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
public class UserService implements UserImpl {
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public User getUserById(Long id) {
    return userRepository
        .findById(id)
        .orElseThrow(() -> new BadRequestException("User not found: " + id));
  }

  @Override
  public List<User> getUsers() {
    log.warn(userRepository.findAll().toString());
    return userRepository.findAll();
  }

  @Override
  public User addUser(User user) {
    return userRepository.save(user);
  }

  @Override
  public String removeUser(Long id) {
    User user =
        userRepository
            .findById(id)
            .orElseThrow(() -> new BadRequestException("User not found: " + id));
    userRepository.delete(user);
    return "User with ID " + id + " has been successfully removed.";
  }

  @Override
  public User partialUpdateUser(Long id, User updatedUser) {
    User existingUser =
        userRepository
            .findById(id)
            .orElseThrow(() -> new BadRequestException("User not found: " + id));

    if (updatedUser.getFirstName() != null) {
      existingUser.setFirstName(updatedUser.getFirstName());
    }
    if (updatedUser.getLastName() != null) {
      existingUser.setLastName(updatedUser.getLastName());
    }
    if (updatedUser.getEmail() != null) {
      existingUser.setEmail(updatedUser.getEmail());
    }
    if (updatedUser.getAddress() != null) {
      existingUser.setAddress(updatedUser.getAddress());
    }
    if (updatedUser.getPhoneNumber() != null) {
      existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
    }
    if (updatedUser.getGender() != null) {
      existingUser.setGender(updatedUser.getGender());
    }

    return userRepository.save(existingUser);
  }

  @Override
  public User updateUser(Long id, User updatedUser) {
    User existingUser =
        userRepository
            .findById(id)
            .orElseThrow(() -> new BadRequestException("User not found: " + id));

    existingUser.setFirstName(updatedUser.getFirstName());
    existingUser.setLastName(updatedUser.getLastName());
    existingUser.setEmail(updatedUser.getEmail());
    existingUser.setGender(updatedUser.getGender());
    existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
    existingUser.setAddress(updatedUser.getAddress());

    return userRepository.save(existingUser);
  }

  @Override
  public PaginateResponse<User> userPaginate(PaginateRequest request) {
    Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize());

    Specification<User> spec = null;

    if (StringUtils.hasText(request.getSearch())) {
      spec = UserSpecification.search(request.getSearch());
    }

    Page<User> page = userRepository.findAll(spec, pageable);

    // Construct and return the PaginateResponse
    return PaginateResponse.<User>builder()
        .content(page.getContent())
        .page(page.getNumber() + 1)
        .size(page.getSize())
        .total(page.getTotalElements())
        .build();
  }
}
