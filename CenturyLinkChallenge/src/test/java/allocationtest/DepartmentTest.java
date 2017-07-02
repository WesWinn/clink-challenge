package allocationtest;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import allocation.Department;
import allocation.Developer;
import allocation.Employee;
import allocation.Manager;
import allocation.QATester;

public class DepartmentTest {
	
	// It is feasible that allocation totals can change, so in the tests, we should have
	// one easy place to update these to prevent combing through dozens of lines
	private static final int developerAllocation = 1000;
	private static final int qaTesterAllocation = 500;
	private static final int managerAllocation = 300;

	@Test
	public void invokeGetAllocationTest() {
		Department department = new Department();
		
		// An empty department should simply return 0
		Assert.assertEquals(0, department.getAllocation());
		
		List<Employee> departmentEmployees = new ArrayList<Employee>();
		
		// Test base cases with each employee type alone in department
		departmentEmployees.add(new Manager());
		department.updateEmployeeList(departmentEmployees);
		Assert.assertEquals(managerAllocation, department.getAllocation());
		
		departmentEmployees.clear();
		departmentEmployees.add(new QATester());
		department.updateEmployeeList(departmentEmployees);
		Assert.assertEquals(qaTesterAllocation, department.getAllocation());
		
		departmentEmployees.clear();
		departmentEmployees.add(new Developer());
		department.updateEmployeeList(departmentEmployees);
		Assert.assertEquals(developerAllocation, department.getAllocation());
		
		departmentEmployees.clear();
		
		// Add a more complex chain to department.
		Manager leadManager = new Manager();
		List<Employee> leadsSubordinates = new ArrayList<Employee>();
		
		Manager subordinateManager = new Manager();
		List<Employee> subManagerSubordinates = new ArrayList<Employee>();
		subManagerSubordinates.add(new Developer());
		subManagerSubordinates.add(new QATester());
		// Add new employees to manager
		subordinateManager.updateSubordinateList(subManagerSubordinates);
		
		leadsSubordinates.add(subordinateManager);
		leadsSubordinates.add(new Developer());
		// Add subordinate manager and dev direct report to lead manager
		leadManager.updateSubordinateList(leadsSubordinates);
		
		// Here, we add the lead manager direct dev report, and subordinate manager and its reports
		departmentEmployees.add(leadManager);
		department.updateEmployeeList(departmentEmployees);
		
		// Allocations: lead manager, dev report, manager report and its dev + qa reports
		int managerTotal = managerAllocation + developerAllocation + managerAllocation
				+ developerAllocation + qaTesterAllocation;
		Assert.assertEquals(managerTotal, department.getAllocation());
		
		// Add a top level developer to the department (with no manager)
		departmentEmployees.add(new Developer());
		department.updateEmployeeList(departmentEmployees);
		// We now have the manager, subordinates, plus a new dev
		int departmentTotal = managerAllocation + developerAllocation + managerAllocation
				+ developerAllocation + qaTesterAllocation + developerAllocation;
		Assert.assertEquals(departmentTotal, department.getAllocation());
		
		// Managers are "top level" to a department, so any inner-workings of their org should not
		// require the department to be updated to reflect changes in allocations. For example, if
		// we remove the subordinate manager from the lead manager, the allocation will just update
		leadsSubordinates.remove(subordinateManager);
		leadManager.updateSubordinateList(leadsSubordinates);
		
		// We removed the subordinateManager and all employees under it, leaving just the Developer
		// at the department top level, the lead manager, and the lead manager's developer
		departmentTotal = managerAllocation + developerAllocation + developerAllocation;
		
		// Note no update to department, it has no knowledge of manager subordinates
		Assert.assertEquals(departmentTotal, department.getAllocation());
	}

}
