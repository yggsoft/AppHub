package com.angelo.logging.templete;

import junit.framework.Assert;

import org.junit.Test;

public class TempletesTest {

//	@Test
	public void test() {
		try {
			Templetes.getInstance().getTempletes();
		} catch (Exception e) {
			Assert.assertTrue(false);
		}
	}
	
	@Test
	public void importDBTest(){
		Templetes.getInstance().importDB();
	}

}
