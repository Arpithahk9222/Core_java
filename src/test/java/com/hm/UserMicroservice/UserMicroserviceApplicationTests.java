package com.hm.UserMicroservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserMicroserviceApplicationTests {

	/**@SpringBootTest → Starts the entire Spring Boot application context

contextLoads() → Checks:
All beans load correctly
No missing dependencies
No configuration errors
   If this test passes:Your application can start successfully 
   This is a smoke test (mandatory in real projects) */
	@Test
	void contextLoads() {
	}

}
