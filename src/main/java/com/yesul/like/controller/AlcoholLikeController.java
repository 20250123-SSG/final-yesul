package com.yesul.like.controller;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.yesul.user.service.PrincipalDetails;
import com.yesul.like.service.AlcoholLikeService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/alcohol-likes")
public class AlcoholLikeController {

    private final AlcoholLikeService alcoholLikeService;

    @PostMapping("/{alcoholId}")
    @ResponseBody
    public Map<String,Object> toggleLike(@PathVariable Long alcoholId,
                                         @AuthenticationPrincipal PrincipalDetails principal) {
        Long userId = principal.getUser().getId();
        boolean liked = alcoholLikeService.toggleLike(alcoholId, userId);
        int count   = alcoholLikeService.getLikeCount(alcoholId);
        Map<String,Object> result = new HashMap<>();
        result.put("liked", liked);
        result.put("likeCount", count);
        return result;
    }
}
