package com.uttara.project.lloyd;

import java.util.Comparator;

public class TaskNameBasedComparator implements Comparator<TaskBean> {

	@Override
	public int compare(TaskBean o1, TaskBean o2) {
		return o1.getTaskName().compareTo(o2.getTaskName());
	}

}
