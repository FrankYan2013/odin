package com.huifu.odin.biz.pegasus;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author anxiang.liu_c
 *
 */
@SpringBootConfiguration
@ComponentScan({"com.huifu.odin.biz","com.huifu.odin.dal","com.huifu.odin.integration"})
public class OdinBizTest {

	public static void run(String[] agrs){
		SpringApplication.run(OdinBizTest.class, agrs);
	}
}
