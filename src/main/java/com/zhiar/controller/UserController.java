package com.zhiar.controller;

import com.zhiar.dao.UserDao;
import com.zhiar.dto.ChangePasswordDto;
import com.zhiar.dto.UserDto;
import com.zhiar.entity.FriendRequest;
import com.zhiar.entity.Post;
import com.zhiar.entity.User;
import com.zhiar.filter.ApiResponse;
import com.zhiar.service.PostService;
import com.zhiar.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserDao userDao;
    private final PostService postService;

    @PostMapping("/register")
    public UserDto registerUser(@RequestBody UserDto userDto) {
        return userService.registerUser(userDto);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Integer userId) {
        userService.deleteUser(userId);
    }

    @PutMapping("/update")
    public UserDto updateUser(@RequestBody UserDto userDto) {
        return userService.updateUser(userDto);
    }
    @PostMapping("/{userId}/add-friend/{friendId}")
    public void addFriend(@PathVariable Integer userId, @PathVariable Integer friendId) {
        System.out.println("Adding friend: " + friendId + " to user: " + userId);
        userService.addFriend(userId, friendId);
    }
    @GetMapping("/{userId}/friend-requests")
    public List<FriendRequest> getPendingRequests(@PathVariable Integer userId) {
        return userService.getPendingRequests(userId);
    }

    @PostMapping("/friend-request/accept/{requestId}")
    public void acceptFriendRequest(@PathVariable Integer requestId) {
        userService.acceptFriendRequest(requestId);
    }

    @PostMapping("/friend-request/decline/{requestId}")
    public void declineFriendRequest(@PathVariable Integer requestId) {
        userService.declineFriendRequest(requestId);
    }

    @PostMapping("/{userId}/remove-friend/{friendId}")
    public void removeFriend(@PathVariable Integer userId, @PathVariable Integer friendId) {
        userService.removeFriend(userId, friendId);
    }


    @PostMapping("/forgot-password")
    public void sendForgotPasswordEmail(@RequestParam String email) {
        userService.sendForgotPasswordEmail(email);
    }
    @GetMapping("/{userId}/friends")
    public List<String> getUserFriends(@PathVariable Integer userId) {
        return userService.getUserFriends(userId);
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordDto changePasswordDto, @AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        userService.changePassword(email, changePasswordDto);
        return ResponseEntity.ok("Password changed successfully");
    }

    @DeleteMapping("/me")
    public ResponseEntity<String> deleteOwnAccount(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        userService.deleteUserByEmail(email);
        return ResponseEntity.ok("Account deleted successfully");
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchUsersAndPosts(@RequestParam String name) {
        if (name.isEmpty()) {
            return ResponseEntity.badRequest().body(new ApiResponse("Search parameter cannot be empty."));
        }
        List<User> users = userService.searchUsersByUsername(name);

        List<Post> posts = postService.searchPostsByUsernameOrContent(name);

        List<Map<String, Object>> userResponses = users.stream()
                .map(user -> {
                    Map<String, Object> userMap = new HashMap<>();
                    userMap.put("username", user.getName());
                    userMap.put("id", user.getId());
                    userMap.put("friendsCount", user.getFriends().size());
                    userMap.put("Post" , postService.getUserPosts(user.getId()));
                    return userMap;
                })
                .collect(Collectors.toList());

        List<Map<String, Object>> postResponses = posts.stream()
                .map(post -> {
                    Map<String, Object> postMap = new HashMap<>();
                    postMap.put("Username", post.getUser().getName());
                    postMap.put("userId", post.getUser().getId());
                    postMap.put("Friends", post.getUser().getFriends().size());
                    postMap.put("Post",post);
                    return postMap;
                })
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("users", userResponses);
        response.put("posts", postResponses);


        if (userResponses.isEmpty() && postResponses.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("No results found for the search term: " + name));
        }

        return ResponseEntity.ok(response);
    }
}
