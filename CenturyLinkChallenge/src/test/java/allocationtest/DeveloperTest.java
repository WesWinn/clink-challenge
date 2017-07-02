package allocationtest;

import org.junit.Assert;
import org.junit.Test;

import allocation.Developer;


public class DeveloperTest {
	
	private static final int developerAllocation = 1000;
	
	@Test
	public void invokeGetAllocationTest() {
		Developer developer = new Developer();
		Assert.assertEquals(developerAllocation, developer.getAllocation());
	}

}
