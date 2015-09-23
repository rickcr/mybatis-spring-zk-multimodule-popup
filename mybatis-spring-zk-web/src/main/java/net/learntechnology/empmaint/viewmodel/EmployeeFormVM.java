package net.learntechnology.empmaint.viewmodel;

import net.learntechnology.empmaint.domain.Department;
import net.learntechnology.empmaint.domain.Employee;
import net.learntechnology.empmaint.services.DepartmentService;
import net.learntechnology.empmaint.services.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Window;

import java.util.List;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class EmployeeFormVM {
	private final static Logger logger = LoggerFactory.getLogger(EmployeeFormVM.class);

	@WireVariable
	private EmployeeService employeeService;
	@WireVariable
	private DepartmentService departmentService;

	@Wire("#employeeForm")
	protected Window employeeForm;

	private Employee employee;

	@Init
	public void init(@ExecutionArgParam("employee") Employee employee) {
		logger.debug("in init");
		if (employee == null) {
			this.employee = new Employee();
		} else {
			this.employee = employee;
		}
	}

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) org.zkoss.zk.ui.Component view){
		Selectors.wireComponents(view, this, false);
	}

	@Command
	public void add() {
		employeeService.insert(employee);
		notifyAndDetatch();
	}

	@Command
	public void update() {
		employeeService.update(employee);
		notifyAndDetatch();
	}

	@Command
	public void delete() {
		employeeService.delete(employee.getId());
		notifyAndDetatch();
	}

	private void notifyAndDetatch() {
		BindUtils.postGlobalCommand(null, null, EmployeesVM.EMPLOYEES_MODIFIED, null);
		employeeForm.detach();
	}

	public List<Department> getDepartments() {
		return departmentService.fetchAll();
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

}
