package com.uttara.project.lloyd;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskBean implements Comparable<Object>,Serializable {

	private String taskName;
	private String description;
	private String tags;
	private String createdDate;
	private String plannedEndDate;
	private String status;
	private String priority;

	public TaskBean() {

	}

	public TaskBean(String taskName, String description, String tags, String plannedEndDate, String priority,
			String createdDate, String status) {
		super();
		this.taskName = taskName;
		this.description = description;
		this.tags = tags;
		this.plannedEndDate = plannedEndDate;
		this.priority = priority;
		this.createdDate = createdDate;
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getPlannedEndDate() {
		return plannedEndDate;
	}

	public void setPlannedEndDate(String plannedEndDate) {
		this.plannedEndDate = plannedEndDate;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof TaskBean) {
			TaskBean tb = (TaskBean) o;
			return this.plannedEndDate.compareTo(tb.plannedEndDate);
		} else
			throw new IllegalArgumentException("pass an instance of task bean.....");
	}

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((plannedEndDate == null) ? 0 : plannedEndDate.hashCode());
		result = prime * result + ((priority == null) ? 0 : priority.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((tags == null) ? 0 : tags.hashCode());
		result = prime * result + ((taskName == null) ? 0 : taskName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaskBean other = (TaskBean) obj;
		if (createdDate == null) {
			if (other.createdDate != null)
				return false;
		} else if (!createdDate.equals(other.createdDate))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (plannedEndDate == null) {
			if (other.plannedEndDate != null)
				return false;
		} else if (!plannedEndDate.equals(other.plannedEndDate))
			return false;
		if (priority == null) {
			if (other.priority != null)
				return false;
		} else if (!priority.equals(other.priority))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (tags == null) {
			if (other.tags != null)
				return false;
		} else if (!tags.equals(other.tags))
			return false;
		if (taskName == null) {
			if (other.taskName != null)
				return false;
		} else if (!taskName.equals(other.taskName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TaskBean [taskName=" + taskName + ", description=" + description + ", tags=" + tags + ", createdDate="
				+ createdDate + ", plannedEndDate=" + plannedEndDate + ", status=" + status + ", priority=" + priority
				+ "]";
	}

	

}
