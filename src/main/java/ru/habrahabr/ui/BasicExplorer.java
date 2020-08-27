package ru.habrahabr.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javafx.scene.control.TreeView;
import ru.habrahabr.entity.Employee;
import ru.habrahabr.repository.EmployeeRepository;

@Component
public class BasicExplorer {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private LazyTreeItem rootTreeNode;

	public TreeView<Employee> getView() {
		TreeView<Employee> employeeTree = new TreeView<>();
		employeeTree.setRoot(rootTreeNode);

		return employeeTree;
	}

	@Bean
	public Employee getEmployee() {
		Employee employee = new Employee();
		employee.setFullName("Hans");
		Employee subject = new Employee();
		subject.setFullName("subject");
		employeeRepository.save(employee);
		subject.setParentEmployee(employee);
		employeeRepository.save(subject);

		Employee subjectSecond = new Employee();
		subjectSecond.setFullName("subject");
		subjectSecond.setParentEmployee(employee);
		employeeRepository.save(subjectSecond);

		for (int i = 0; i < 100; i++) {
			Employee newEmp = new Employee();
			newEmp.setFullName("svsf" + i);
			newEmp.setParentEmployee(subject);
			employeeRepository.save(newEmp);
		}

		for (int i = 0; i < 50; i++) {
			Employee newEmp = new Employee();
			newEmp.setFullName("second" + i);
			newEmp.setParentEmployee(subjectSecond);
			employeeRepository.save(newEmp);
		}

		return employee;
	}

}