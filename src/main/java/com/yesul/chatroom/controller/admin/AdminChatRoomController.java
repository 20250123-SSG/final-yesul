package com.yesul.chatroom.controller.admin;

import com.yesul.admin.model.entity.Admin;
import com.yesul.chatroom.model.dto.AdminChatRoomsResponse;
import com.yesul.chatroom.model.dto.ChatRoomSummaryResponse;
import com.yesul.chatroom.service.ChatRoomService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("/admin/chatroom")
@RequiredArgsConstructor
public class AdminChatRoomController {

    private final ChatRoomService chatRoomService;

    @GetMapping
    public String getAdminChatRoom(
            @AuthenticationPrincipal Admin admin,
            @RequestParam(required = false) Long cursor,
            @RequestParam(defaultValue = "8") int size,
            Model model) {
        AdminChatRoomsResponse response = chatRoomService.getAdminChatRooms(cursor, size);

        model.addAttribute("chatRooms", response.getChatRooms());
        model.addAttribute("totalUnreadCount", response.getTotalUnreadCount());
        model.addAttribute("nextCursor", response.getNextCursor());
        model.addAttribute("hasNext", response.isHasNext());

        return "admin/admin-chat";
    }

    @GetMapping("/search")
    public String searchChatRoom(
            @RequestParam String keyword,
            Model model
    ) {
        List<ChatRoomSummaryResponse> chatRooms = chatRoomService.searchChatRoom(keyword);
        model.addAttribute("chatRooms", chatRooms);
        return "admin/admin-chat :: searchResultFragment";
    }
}
