package com.zhiar.Impl;


import com.zhiar.dao.FriendRequestDao;
import com.zhiar.dao.PostLikeDao;
import com.zhiar.dao.UserDao;
import com.zhiar.dto.ChangePasswordDto;
import com.zhiar.dto.UserDto;
import com.zhiar.entity.FriendRequest;
import com.zhiar.entity.PostLike;
import com.zhiar.entity.User;
import com.zhiar.mapper.UserMapper;
import com.zhiar.service.CommentLikeService;
import com.zhiar.service.NotificationService;
import com.zhiar.service.UserService;
import com.zhiar.util.EmailService;
import com.zhiar.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final EmailService emailService;
    private final JwtUtil jwtUtil;
    private final UserMapper userMapper;
    private final PostLikeDao postLikeDao;
    private final CommentLikeService commentLikeService;
    private final NotificationService notificationService;
    private final FriendRequestDao friendRequestDao;

    @Override
    public UserDto registerUser(UserDto userDto) {
        User existingUser = userDao.findByEmail(userDto.getEmail())
                .orElse(null);

        if (existingUser != null) {
            throw new RuntimeException("An account already exists with this email: " + userDto.getEmail());
        }

        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword("{noop}" + userDto.getPassword());
        user.setName(userDto.getName());
        userDao.save(user);
        return userDto;
    }

    @Override
    public void deleteUser(Integer userId) {
        userDao.deleteById(userId);
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        User existingUser = userDao.findById(userDto.getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (userDto.getName() != null) {
            existingUser.setName(userDto.getName());
        }
        if (userDto.getEmail() != null) {
            existingUser.setEmail(userDto.getEmail());
        }
        if (userDto.getPassword() != null) {
            existingUser.setPassword("{noop}" + userDto.getPassword());
        }

        userDao.save(existingUser);
        return userMapper.toDto(existingUser);
    }

    @Override
    public void addFriend(Integer userId, Integer friendId) {
        User user = userDao.findById(userId)
                .orElseThrow(() -> new RuntimeException("User  not found"));
        User friend = userDao.findById(friendId)
                .orElseThrow(() -> new RuntimeException("Friend not found"));

        if (user.getFriends().contains(friendId)) {
            throw new RuntimeException("You are already friends with this user.");
        }

        List<FriendRequest> existingRequests = friendRequestDao.findByReceiverIdAndSenderId(friendId, userId);
        if (!existingRequests.isEmpty()) {
            throw new RuntimeException("Friend request already sent.");
        }

        FriendRequest friendRequest = new FriendRequest(userId, friendId);
        friendRequestDao.save(friendRequest);

        String message = "User  " + user.getUsername() + " has sent you a friend request!";
        notificationService.sendNotification(friendId, message);
    }

    @Override
    public void acceptFriendRequest(Integer requestId) {
        FriendRequest request = friendRequestDao.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Friend request not found"));

        User sender = userDao.findById(request.getSenderId())
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        User receiver = userDao.findById(request.getReceiverId())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        sender.getFriends().add(receiver.getId());
        receiver.getFriends().add(sender.getId());

        userDao.save(sender);
        userDao.save(receiver);

        friendRequestDao.delete(request);
    }

    @Override
    public void declineFriendRequest(Integer requestId) {
        friendRequestDao.deleteById(requestId);
    }

    @Override
    public List<FriendRequest> getPendingRequests(Integer userId) {
        return friendRequestDao.findByReceiverIdAndStatus(userId, "PENDING");
    }
    @Override
    public void removeFriend(Integer userId, Integer friendId) {
        User user = userDao.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.getFriends().remove(friendId);
        userDao.save(user);
    }

    public List<String> getUserFriends(Integer userId) {
        User user = userDao.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return user.getFriends().stream()
                .map(friendId -> userDao.findById(friendId)
                        .map(User::getName)
                        .orElse("Unknown"))
                .collect(Collectors.toList());
    }


    @Override
    public User getUserByUsername(String username) {
        return userDao.findByUsername(username).orElseThrow(() ->
                new RuntimeException("User not found"));
    }

    @Override
    public UserDto findUserByEmail(String email) {
        User user = userDao.findByEmail(email)
                .stream()
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        UserDto userDto = userMapper.toDto(user);
        userDto.setFriends(new ArrayList<>(Integer.parseInt(user.getName())));

        return userDto;
    }

    @Override
    public void changePassword(String email, ChangePasswordDto changePasswordDto) {
        User user = userDao.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getPassword().equals("{noop}" + changePasswordDto.getOldPassword())) {
            throw new RuntimeException("Old password is incorrect");
        }

        if (!changePasswordDto.getNewPassword().equals(changePasswordDto.getConfirmNewPassword())) {
            throw new RuntimeException("New passwords do not match");
        }

        user.setPassword("{noop}" + changePasswordDto.getNewPassword());
        userDao.save(user);
    }

    @Override
    public void sendForgotPasswordEmail(String email) {
        String token = jwtUtil.generateToken(email);
        emailService.sendEmail(email, "Password Reset", "Reset your password using this link: http://localhost:8080/reset-password?token=" + token);
    }

    @Override
    public void deleteUserByEmail(String email) {
        User user = userDao.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userDao.delete(user);
    }

    public void likePost(Integer postId, Integer userId) {
        Optional<PostLike> existingLike = postLikeDao.findByPostIdAndUserId(postId, userId);
        if (existingLike.isPresent()) {
            throw new RuntimeException("User has already liked this post.");
        }

        PostLike postLike = new PostLike();
        postLike.setPostId(postId);
        postLike.setUserId(userId);
        postLikeDao.save(postLike);
    }

    public void unlikePost(Integer postId, Integer userId) {
        postLikeDao.deleteByPostIdAndUserId(postId, userId);
    }

    public void likeComment(Integer commentId, Integer userId) {
        commentLikeService.likeComment(commentId, userId);
    }

    public void unlikeComment(Integer commentId, Integer userId) {
        commentLikeService.unlikeComment(commentId, userId);
    }

    public boolean isCommentLiked(Integer commentId, Integer userId) {
        return commentLikeService.isCommentLiked(commentId, userId);
    }

    @Override
    public Integer findUserIdByEmail(String email) {
        User user = userDao.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return user.getId();
    }

    @Override
    public User findById(Integer userId) {
        return userDao.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public List<User> findAllUsers() {
        return userDao.findAll();
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    @Override
    public List<User> searchUsersByUsername(String name) {
        List<User> users = userDao.findByUsernameContainingIgnoreCase(name);
        System.out.println("Searched Username: " + name);
        System.out.println("Found Users: " + users);
        return users;
    }
}