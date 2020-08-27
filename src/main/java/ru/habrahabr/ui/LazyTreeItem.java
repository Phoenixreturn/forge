package ru.habrahabr.ui;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import ru.habrahabr.entity.Employee;
import ru.habrahabr.repository.EmployeeRepository;

@Component
public class LazyTreeItem extends TreeItem<Employee> {
	private boolean childrenLoaded = false;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	public LazyTreeItem(Employee value) {
		super(value);
		expandedProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue) {
				flush();
			}
		});
	}

	public LazyTreeItem(Employee value, EmployeeRepository employeeRepository) {
		super(value);
		this.employeeRepository = employeeRepository;
		expandedProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue) {
				flush();
			}
		});
	}

	@Override
	public ObservableList<TreeItem<Employee>> getChildren() {
		if (childrenLoaded || !isExpanded()) {
			return super.getChildren();
		}
		if (super.getChildren().size() == 0) {
			// Filler node (will translate into loading icon in the
			// TreeCell factory)
			super.getChildren().add(new TreeItem<>(null));
		}

		loadItems();

		return super.getChildren();
	}

	public void loadItems() {
		Platform.runLater(() -> {
			List<Employee> downloadSet = employeeRepository.findEmployees(this.getValue().getId());
			List<LazyTreeItem> treeNodes = new ArrayList<>();
			for (Employee download : downloadSet) {
				treeNodes.add(new LazyTreeItem(download, employeeRepository));
			}
			super.getChildren().clear();
			super.getChildren().addAll(treeNodes);
			childrenLoaded = true;
		});
	}

	private void flush() {
		childrenLoaded = false;
		super.getChildren().clear();
	}

	@Override
	public boolean isLeaf() {
		if (childrenLoaded) {
			return getChildren().isEmpty();
		}
		return false;
	}
}