package com.boum.frecipe.service.user;

import java.util.Collections;
import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.boum.frecipe.domain.user.User;
import com.boum.frecipe.dto.user.UserDTO;
import com.boum.frecipe.repository.user.RoleRepository;
import com.boum.frecipe.repository.user.UserRepository;
import com.boum.frecipe.security.JwtTokenProvider;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{
	
	private final UserRepository userRepo;
	private final JwtTokenProvider jwtUtils;
	private final PasswordEncoder encoder;
	
	// 회원가입
	@Override
	public User signUp(UserDTO userDto) {
		User user = User.builder()
				.username(userDto.getUsername())
				.password(encoder.encode(userDto.getPassword()))
				.nickname(userDto.getNickname())
				.phone(userDto.getPhone())
				.roles(Collections.singletonList("ROLE_USER"))
				.build();
		
		userRepo.save(user);
		return user;
	}
	
	// 로그인
	@Override
	public String signIn(String username, String password) {
		User user = userRepo.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("아이디를 확인 해주세요."));
		if(!encoder.matches(password, user.getPassword())) {
			throw new IllegalArgumentException("비밀번호를 확인 해주세요.");
		}
		System.out.println(jwtUtils.createToken(String.valueOf(user.getUserNo()), user.getRoles()));
		return jwtUtils.createToken(String.valueOf(user.getUserNo()), user.getRoles());
	}

	// 전체 회원 조회
	@Override
	public List<User> retrieveAllUser() {
		return userRepo.findAll();
	}
	
	
	// 회원 정보 조회
	@Override
	public User retrieveUser(String userNo) {
		return userRepo.findByUserNo(Long.valueOf(userNo)).orElseThrow(() -> new IllegalArgumentException("아이디를 확인 해주세요."));
	}
	
}
