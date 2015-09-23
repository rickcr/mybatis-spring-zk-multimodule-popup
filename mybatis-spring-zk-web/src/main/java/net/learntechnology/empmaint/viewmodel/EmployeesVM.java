package net.learntechnology.empmaint.viewmodel;

import net.learntechnology.empmaint.domain.Employee;
import net.learntechnology.empmaint.services.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Window;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class EmployeesVM {
	private final static Logger logger = LoggerFactory.getLogger(EmployeesVM.class);

	@WireVariable
	private EmployeeService employeeService;

	private Employee employee;

	public static final String EMPLOYEES_MODIFIED = "employeesModified";

	private String EMPLOYEE_FORM = "/pages/employeeForm.zul";

	@Command
	public void createEmployee() {
		Window window = (Window) Executions.createComponents(EMPLOYEE_FORM, null, null);
		window.doModal();
	}

	@Command
	public void editEmployee() {
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("employee", employee);
		Window window = (Window) Executions.createComponents(EMPLOYEE_FORM, null, args);
		window.doModal();
	}

	@NotifyChange({"employees"})
	@GlobalCommand(EMPLOYEES_MODIFIED)
	public void employeesModified() {
		//notifyChange triggers call to getEmployees
	}

	public List<Employee> getEmployees() {
		return employeeService.fetchAll();
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
}
