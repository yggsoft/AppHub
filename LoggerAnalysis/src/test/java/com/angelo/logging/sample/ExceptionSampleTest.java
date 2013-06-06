package com.angelo.logging.sample;

import java.io.File;

import org.junit.Test;

import com.angelo.logging.templete.Templete;

public class ExceptionSampleTest {

	@Test
	public void test() {
		ExceptionSample sample = new ExceptionSample(new File(Thread
				.currentThread().getContextClassLoader()
				.getResource("SamplesLib/IllegalRequest.sample")
				.getPath()));
		Templete temp = sample.getTemplete();
		temp.setTitle("Duplicate records to DB");
		temp.setRCA("Duplicate records to DB");
		temp.setReProduceSteps("");
		
		System.out.println(temp.getTemplete());
	}

}
