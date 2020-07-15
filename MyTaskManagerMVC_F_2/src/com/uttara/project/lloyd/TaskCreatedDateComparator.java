package com.uttara.project.lloyd;

import java.util.Comparator;

public class TaskCreatedDateComparator implements Comparator<TaskBean> {

	@Override
	public int compare(TaskBean o1, TaskBean o2) {

		return o1.getCreatedDate().compareTo(o2.getCreatedDate());
	}

}
