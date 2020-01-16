package com.ksh.heart.common.jwt;

import com.ksh.heart.common.factory.impl.ConstantFactory;
import com.ksh.heart.system.entity.user.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * 用户认证、权限、使用security的表单登录时会被调用(自定义登录请忽略)
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    /**
     * 若使用security表单鉴权则需实现该方法，通过account获取用户信息（密码、权限等等）
     */
    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        User user = ConstantFactory.me().getUserByAccount(account);
        if(user == null){
            //仍需要细化处理
            throw new UsernameNotFoundException("该用户不存在");
        }
        return new JwtUserDetails(
                user.getPhone(),
                user.getPassword(),
                user.getSalt(),
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleName())));
    }
}
