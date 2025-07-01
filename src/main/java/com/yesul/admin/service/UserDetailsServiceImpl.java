package com.yesul.admin.service;

import com.yesul.admin.model.dto.auth.LoginAdmin;
import com.yesul.admin.model.entity.Admin;
import com.yesul.admin.repository.AdminRepository;
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

    private final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Admin fetchedAdmin = adminRepository.findByLoginId(username)
                .orElseThrow(() -> new IllegalArgumentException("해당 관리자가 존재하지 않습니다."));

        List<GrantedAuthority> authorityList =
                List.of(new SimpleGrantedAuthority("ADMIN"));

        LoginAdmin test = new LoginAdmin(fetchedAdmin.getId(), fetchedAdmin.getLoginId(), fetchedAdmin.getLoginPwd(), authorityList);
        System.out.println(test);
        return test;
    }

}
