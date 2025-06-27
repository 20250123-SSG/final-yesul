package com.yesul.admin.service;

import com.yesul.admin.model.dto.userInfo.UserInfoListAdminDto;
import com.yesul.admin.repository.UserInfoAdminRepository;
import com.yesul.user.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminService {

    private final UserInfoAdminRepository userInfoAdminRepository;
    private final ModelMapper modelMapper;

    public List<UserInfoListAdminDto> getAllUsersInfo() {
        List<UserInfoListAdminDto> userList = userInfoAdminRepository.findAllUserInfoDto();

        return userList;
    }
}
