package com.kkuk.japtest.kkukboard.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	//회원(유저) 등록 서비스
	public SiteUser create(String username, String email, String password) {
		SiteUser user = new SiteUser();
		user.setUsername(username);
		user.setEmail(email);
		
		//BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String cryptPassword = passwordEncoder.encode(password); // 패스워드 암호화
		user.setPassword(cryptPassword); // 비밀번호를 암호화한후 암호문을 DB에 저장
		
		userRepository.save(user);
		
		return user;
	}
}
