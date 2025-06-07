package org.example.deokbook;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class DeokBookApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private PasswordEncoder passwordEncoder;

	public void testPasswordEncoder() {
		String rawPassword = "1234";
		String encodedPassword = "$2a$10$SGjBtDqPtYcqS9Sc7YThZOmSKN7bknwsBgYr4nIg1VX8X6YbcMiqq"; // DB 저장된 암호화 비밀번호

		boolean matches = passwordEncoder.matches(rawPassword, encodedPassword);
		System.out.println("비밀번호 매칭 결과: " + matches);
	}

}
