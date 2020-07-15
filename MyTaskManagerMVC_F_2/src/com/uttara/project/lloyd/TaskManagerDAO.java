package com.uttara.project.lloyd;

import java.util.List;

public interface TaskManagerDAO {
	
	public String createCategory(String catName,String email);//

	public UpdateTaskBean getTask(String catName, String email, String taskName);//

	public String addTask(TaskBean task, String catName, String email);

	public boolean checkCategoryExists(String catName,String email);

	public boolean CheckTaskExists(String catName, String taskName, String email);

	public String updateTaskData(String catName, String taskName, String email, TaskBean bean, String task_id);

	public void deleteCategory(String catName, String email );

	public void listOfCategories(String email);

	public void listOfCategoriesAndTask(String email);

	public List<String> categoryList(String email);
	
	public List<String> taskList(String catName, String email);

	public boolean checkCategoryExistsDuringInitialization(String email);

	public boolean searchCategories(String catName, String path, String email);

	public String removeTask(String catName, String taskName, String email, UpdateTaskBean bean);

	public void searchTask(String catName, String taskName, String email);

	public List<TaskBean> listTasksBasedOnCreatedDate(String catName, String email);

	public List<TaskBean> listTasksBasedOnCreatedDate(String email);

	public List<TaskBean> listTasksBasedOnPlannedEndDate(String catName, String email);

	public List<TaskBean> listTasksBasedOnLongestTime(String email);

	public List<TaskBean> listTasksBasedOnLongestTime(String catName, String email);

	public List<TaskBean> listTasksBasedOnPlannedEndDate(String email);

	public List<TaskBean> listTasksBasedOnTaskName(String catName, String email);

	public List<TaskBean> listTasksBasedOnTaskName(String email);

	public List<TaskBean> listTasksBasedOnOverDueTask(String email);

	public List<TaskBean> listTasksBasedOnOverDueTask(String catName, String email);

	public void searchAllCategory(String wordToBeSearched, String email);

	public void searchAllTaskInCategory(String catName, String wordToBeSearched, String email);

	public String removeCat(String catName, String email);
	
}
