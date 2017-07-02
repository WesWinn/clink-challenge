package allocation;

import java.util.ArrayList;
import java.util.List;

public class Department {
	
	private List<Employee> employees;
	
	public int getAllocation() {
		// If the department has no employees, it has no allocation
		if (employees == null || employees.isEmpty()) {
			return 0;
		}
		
		int departmentAllocation = 0;
		for (Employee employee : employees) {
			departmentAllocation += employee.getAllocation();
		}
		
		return departmentAllocation;
	}
	
	public void updateEmployeeList(List<Employee> employees) {
		if (employees != null) {
			this.employees = new ArrayList<Employee>(employees);
		} else {
			// Edge case, but if we're sent a null list, assume no employees
			this.employees = null;
		}
	}
}
