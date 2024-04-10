package com.jansouza.springboot.lab.test;

import com.jansouza.springboot.lab.LabApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class LabApplicationTests {

	@Test
   public void main() {
    LabApplication.main(new String[] {});
   }

}