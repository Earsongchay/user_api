package com.example.user_api.Interface;

import com.example.user_api.Model.PaginateRequest;
import com.example.user_api.Model.PaginateResponse;
import com.example.user_api.Model.User;
import java.util.List;

public interface UserImpl {
  User getUserById(Long id);

  List<User> getUsers();

  User addUser(User user);

  String removeUser(Long id);

  User partialUpdateUser(Long id, User user);

  User updateUser(Long id, User user);

  PaginateResponse<User> userPaginate(PaginateRequest request);
}
