package allocationtest;

import org.junit.Assert;
import org.junit.Test;

import allocation.QATester;


public class QATesterTest {
	
	private static final int qaTesterAllocation = 500;

	@Test
	public void invokeGetAllocationTest() {
		QATester qaTester = new QATester();
		Assert.assertEquals(qaTesterAllocation, qaTester.getAllocation());
	}

}
