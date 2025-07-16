package com.yesul.like.controller;

import java.util.HashMap;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.yesul.user.service.PrincipalDetails;
import com.yesul.like.service.PostLikeService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/likes")
public class PostLikeController {

    private final PostLikeService likeService;

    @PostMapping("/{postId}")
    @ResponseBody
    public Map<String, Object> toggleLike(@PathVariable Long postId,
                                          @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Long userId = principalDetails.getUser().getId();
        boolean liked = likeService.toggleLike(postId, userId);
        int likeCount = likeService.getLikeCount(postId);

        Map<String, Object> result = new HashMap<>();
        result.put("liked", liked);
        result.put("likeCount", likeCount);
        return result;
    }
}