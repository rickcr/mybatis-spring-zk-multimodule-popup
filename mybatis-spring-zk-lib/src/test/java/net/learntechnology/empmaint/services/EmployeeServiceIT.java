package net.learntechnology.empmaint.services;

import net.learntechnology.empmaint.domain.Department;
import net.learntechnology.empmaint.domain.Employee;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

public class EmployeeServiceIT extends BaseIT {
	private final static Logger logger = LoggerFactory.getLogger(EmployeeServiceIT.class);

	@Resource
	private EmployeeService employeeService;

	@Test
	public void should_fetch_all_employees() {
		List<Employee> employees = employeeService.fetchAll();
		for(Employee emp: employees) {
			logger.debug("EMP: {}", emp);
		}
		Assert.assertTrue(employees.size() > 1);
	}

	@Test
	public void should_fetch_employee() {
		Employee emp = employeeService.fetch(1);
		logger.debug("Emp returned {}", emp);
		Assert.assertEquals("John", emp.getFirstName());
	}

	@Test
	@Transactional
	public void should_insert_employee() {
		Department d = new Department();
		d.setId(100);
		Employee emp = new Employee(null, "TestFirstName", "TestLastName", 43, d);
		employeeService.insert(emp);
		emp = employeeService.fetch(emp.getId());
		logger.debug("Emp returned {}", emp);
		Assert.assertEquals("TestFirstName", emp.getFirstName());
	}
}
