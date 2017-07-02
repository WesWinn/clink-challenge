package allocation;

import java.util.ArrayList;
import java.util.List;

public class Manager implements Employee {

	private List<Employee> subordinates;
	
	@Override
	public int getAllocation() {
		return 300 + getSubordinateAllocationTotal();
	}

	public int getSubordinateAllocationTotal() {
		if (subordinates == null) {
			return 0;
		}
		
		int allocationTotal = 0;
		for (Employee subordinate : subordinates) {
			allocationTotal += subordinate.getAllocation();
		}
		
		return allocationTotal;
	}
	
	// Simplest solution for this use case. Add/Remove by ID functionality, etc.
	// is easy to add without modifying existing code, but is out of scope.
	public void updateSubordinateList(List<Employee> subordinates) {
		if (subordinates != null) {
			this.subordinates = new ArrayList<Employee>(subordinates);
		} else {
			// Edge case, but if we're sent a null list, assume no subordinates
			this.subordinates = null;
		}
	}
}
