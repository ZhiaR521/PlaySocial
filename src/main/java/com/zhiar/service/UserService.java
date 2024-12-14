package com.zhiar.service;

import com.zhiar.dto.ChangePasswordDto;
import com.zhiar.dto.UserDto;
import com.zhiar.entity.FriendRequest;
import com.zhiar.entity.User;

import java.util.List;

public interface UserService {
    UserDto registerUser(UserDto userDto);
    void deleteUser(Integer userId);
    UserDto updateUser(UserDto userDto);
    void addFriend(Integer userId, Integer friendId);
    void acceptFriendRequest(Integer requestId);
    void declineFriendRequest(Integer requestId);
    List<FriendRequest> getPendingRequests(Integer userId);
    void removeFriend(Integer userId, Integer friendId);
    UserDto findUserByEmail(String email);
    void changePassword(String email, ChangePasswordDto changePasswordDto);
    void sendForgotPasswordEmail(String email);
    void deleteUserByEmail(String email);
    Integer findUserIdByEmail(String email);
    User findById(Integer userId);
    List<User> findAllUsers();
    User findByEmail(String email);
    List<User> searchUsersByUsername(String name);
    User getUserByUsername(String username);
    List<String> getUserFriends(Integer userId);



}
