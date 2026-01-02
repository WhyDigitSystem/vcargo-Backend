package com.efit.savaari;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.efit.savaari.test.Calculation;
@SpringBootTest
class SavaariApplicationTests {

	@Test
	void addition() {
		
		Calculation clc= new  Calculation();
		
		int result=clc.addition(5, 10);
		
		assertEquals(15,result);
	}

}
