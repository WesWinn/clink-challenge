package allocationtest;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import allocation.Developer;
import allocation.Employee;
import allocation.Manager;
import allocation.QATester;



public class ManagerTest {
	
	// It is feasible that allocation totals can change, so in the tests, we should have
	// one easy place to update these to prevent combing through dozens of lines
	private static final int developerAllocation = 1000;
	private static final int qaTesterAllocation = 500;
	private static final int managerAllocation = 300;

	@Test
	public void invokeGetAllocationTest() {
		Manager manager = new Manager();
		Assert.assertEquals(managerAllocation, manager.getAllocation());
		
		List<Employee> subordinates = new ArrayList<Employee>();
		subordinates.add(new Developer());
		subordinates.add(new QATester());
		manager.updateSubordinateList(subordinates);
		
		int allocationTotal = managerAllocation + developerAllocation + qaTesterAllocation;
		Assert.assertEquals(allocationTotal, manager.getAllocation());
	}
	
	@Test
	public void invokeGetSubordinateAllocationTotalTest() {
		Manager manager = new Manager();
		List<Employee> subordinates = null;
		
		// Ensure that a null list is handled appropriately
		manager.updateSubordinateList(subordinates);
		Assert.assertEquals(0, manager.getSubordinateAllocationTotal());
		
		subordinates = new ArrayList<Employee>();
		
		// This test and the following also ensure developer awareness that modifying 
		// Developer or QA Tester will affect any Manager instances
		subordinates.add(new Developer());
		manager.updateSubordinateList(subordinates);
		Assert.assertEquals(developerAllocation, manager.getSubordinateAllocationTotal());
		
		subordinates.clear();
		subordinates.add(new QATester());
		manager.updateSubordinateList(subordinates);
		Assert.assertEquals(qaTesterAllocation, manager.getSubordinateAllocationTotal());
		
		subordinates.clear();
		
		// Set up a new manager as an employee of the top level manager
		Manager subManager = new Manager();
		List<Employee> subManagerSubordinates = new ArrayList<Employee>();
		subManagerSubordinates.add(new Developer());
		subManagerSubordinates.add(new QATester());
		subManager.updateSubordinateList(subManagerSubordinates);
		
		// Add the new manager with its subordinates as a subordinate to head manager
		subordinates.add(subManager);
		manager.updateSubordinateList(subordinates);
		int subManagerTotal = managerAllocation + qaTesterAllocation + developerAllocation;
		Assert.assertEquals(subManagerTotal, manager.getSubordinateAllocationTotal());
		
		// Add a couple subordinates to the head manager to check all types of employees
		subordinates.add(new Developer());
		subordinates.add(new QATester());
		manager.updateSubordinateList(subordinates);
		Assert.assertEquals((subManagerTotal + developerAllocation + qaTesterAllocation),
				manager.getSubordinateAllocationTotal());
		
		// One last sanity check to make sure nullifying the list removes subordinates
		manager.updateSubordinateList(null);
		Assert.assertEquals(0, manager.getSubordinateAllocationTotal());
	}
}
