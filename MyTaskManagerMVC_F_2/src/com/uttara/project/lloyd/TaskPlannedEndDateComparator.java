package com.uttara.project.lloyd;

import java.util.Comparator;

public class TaskPlannedEndDateComparator implements Comparator<TaskBean> {

	@Override
	public int compare(TaskBean o1, TaskBean o2) {
		return o1.getPlannedEndDate().compareTo(o2.getPlannedEndDate());
	}

}
