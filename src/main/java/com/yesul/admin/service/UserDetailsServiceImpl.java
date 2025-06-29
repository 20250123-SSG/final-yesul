package com.yesul.admin.service;

import com.yesul.admin.model.dto.auth.LoginAdmin;

import com.yesul.admin.model.entity.Admin;
import com.yesul.admin.repository.LoginAdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final LoginAdminRepository loginAdminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Admin fetchedAdmin = loginAdminRepository.findByLoginId(username);

        if(fetchedAdmin == null){
            throw new UsernameNotFoundException("아이디 또는 비밀번호가 잘못되었습니다.");
        }

        List<GrantedAuthority> authorityList =
                List.of(new SimpleGrantedAuthority("ADMIN"));

        LoginAdmin loginadmin = new LoginAdmin(fetchedAdmin.getId(), fetchedAdmin.getLoginId(), fetchedAdmin.getLoginPwd(), authorityList);

        return loginadmin; // Security 인증에 필요한 데이터를 UserDetail타입의 객체로 담음
    }

}
