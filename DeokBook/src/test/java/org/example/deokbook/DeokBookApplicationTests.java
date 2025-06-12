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

	@Test
	public void testPasswordEncoder() {
		String rawPassword = "1234";
		String encodedPassword = "$2a$10$65TBBu3kqxa69xUVBlo1g.Erd0.vHVCzRiQ4syU.CBOCW74kbKy3C"; // DB 저장된 암호화 비밀번호

		boolean matches = passwordEncoder.matches(rawPassword, encodedPassword);
		System.out.println("비밀번호 매칭 결과: " + matches);
	}



}
