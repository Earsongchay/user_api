package com.example.user_api.Controller;

import com.example.user_api.Model.PaginateRequest;
import com.example.user_api.Model.PaginateResponse;
import com.example.user_api.Model.User;
import com.example.user_api.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @Operation(summary = "Get user by ID", description = "Fetch a user by their unique ID")
  @GetMapping("/{id}")
  public ResponseEntity<User> getUserById(@PathVariable Long id) {
    User user = userService.getUserById(id);
    return ResponseEntity.ok(user);
  }

  //  @Operation(summary = "Get all users", description = "Fetch all users in the system")
  //  @GetMapping("/all")
  //  public ResponseEntity<List<User>> getAllUsers() {
  //    List<User> users = userService.getUsers();
  //    return ResponseEntity.ok(users);
  //  }

  @Operation(summary = "Add a new user", description = "Add a new user to the system")
  @PostMapping
  public ResponseEntity<User> addUser(@RequestBody User user) {
    return ResponseEntity.status(201).body(userService.addUser(user));
  }

  @Operation(summary = "Remove a user", description = "Remove a user from the system by ID")
  @DeleteMapping("/{id}")
  public ResponseEntity<String> removeUser(@PathVariable Long id) {
    String msg = userService.removeUser(id);
    return ResponseEntity.ok(msg);
  }

  @Operation(summary = "Partially update a user", description = "Update certain fields of a user")
  @PatchMapping("/{id}")
  public ResponseEntity<User> partialUpdateUser(@PathVariable Long id, @RequestBody User user) {
    return ResponseEntity.ok(userService.partialUpdateUser(id, user));
  }

  @Operation(summary = "Update a user", description = "Update a user's information completely")
  @PutMapping("/{id}")
  public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
    return ResponseEntity.ok(this.userService.updateUser(id, user));
  }

  @Operation(
      summary = "Get paginated users",
      description = "Fetch a paginated list of users with pagination parameters")
  @GetMapping
  public PaginateResponse<User> userPaginate(
      @RequestParam(defaultValue = "1") @Parameter(description = "Page number") int page,
      @RequestParam(defaultValue = "10") @Parameter(description = "Number of users per page")
          int size,
      @RequestParam(defaultValue = "") @Parameter(description = "Search") String search) {
    var request = PaginateRequest.builder().page(page).size(size).search(search).build();
    return userService.userPaginate(request);
  }
}
