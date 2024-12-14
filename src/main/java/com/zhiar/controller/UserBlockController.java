package com.zhiar.controller;

import com.zhiar.dto.UserBlockDto;
import com.zhiar.entity.UserBlock;
import com.zhiar.service.UserBlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-block")
public class UserBlockController {

    @Autowired
    private UserBlockService userBlockService;

    @PostMapping("/block")
    public String blockUser(@RequestBody UserBlockDto userBlockDto) {
        return userBlockService.blockUser(userBlockDto.getBlockerId(), userBlockDto.getBlockedId());
    }

    @DeleteMapping("/unblock")
    public ResponseEntity<String> unblockUser(
            @RequestParam Integer blockerId,
            @RequestParam Integer blockedId) {

        userBlockService.unblockUser(blockerId, blockedId);
        return ResponseEntity.ok("User successfully unblocked.");
    }

    @GetMapping("/blocked/{userId}")
    public List<UserBlock> getBlockedUsers(@PathVariable Integer userId) {
        return userBlockService.getBlockedUsers(userId);
    }

    @GetMapping("/blocking/{userId}")
    public List<UserBlock> getBlockingUsers(@PathVariable Integer userId) {
        return userBlockService.getBlockingUsers(userId);
    }
}
