package com.kkuk.japtest.kkukboard.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "siteuser")
@SequenceGenerator(
		name = "USER_SEQ_GENERATOR", // JPA 내부 시퀀스 이름
		sequenceName = "USER_SEQ", // 실제 DB시퀀스 이름
		initialValue = 1, // 시퀀스 시작 값
		allocationSize = 1 // 시퀀스 증가 값
		)
public class SiteUser {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ_GENERATOR")
	private Long id; // 유저번호 기본키
	
	@Column(unique = true) // 아이디는 중복 불가
	private String username; // 유저 아이디(이름)
	
	private String password;
	
	@Column(unique = true) // 이메일도 중복 불가
	private String email;
	
	
}
