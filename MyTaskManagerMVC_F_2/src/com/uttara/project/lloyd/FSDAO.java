package com.uttara.project.lloyd;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class FSDAO implements TaskManagerDAO{
	
	Logger logger = Logger.getInstrance();
	MyTaskUtility myTaskUtility = new MyTaskUtility();
	
	@Override             //Done
	public String createCategory(String catName,String email) {
		String filePath = System.getProperty("user.dir") + File.separator+email;
		String msg = null;
		File file = null;
		boolean dirExists = new File(filePath).mkdir();
		System.out.println(dirExists);
		if(dirExists) {
		file = new File(filePath+catName +".todo");
		System.out.println(file.getAbsolutePath());
		}else {
			file = new File(filePath+ File.separator+catName +".todo");
			System.out.println(file.getAbsolutePath());
		}
		
		boolean fvar = false;
		try {
			fvar = file.createNewFile();
			if(fvar) {
				msg = "success";
			}else {
				msg = "failed to create category";
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;
	}

	@Override            //Done
	public UpdateTaskBean getTask(String catName, String email, String taskName) {
		
		BufferedReader br = null;
		UpdateTaskBean task = null;
		String line = null;
		int counter = 0;
		try {
			br = new BufferedReader(new FileReader(System.getProperty("user.dir") + File.separator+email+ File.separator+catName + ".todo"));
			while ((line = br.readLine()) != null) {
				System.out.println(taskName);
				if(line.contains(taskName)) {
				counter = line.length();
				String[] str = line.split(":");
				task = new UpdateTaskBean(str[0], str[1], str[4], str[3], str[2],
						str[5], str[6]);
				}

			}
			if (counter == 0) {
				System.out.println("");
				System.out.println("no tasks added yet....");
				System.out.println();
			}
		} catch (Exception e) {
			e.printStackTrace();

		}

		finally {
			try {
				if (br != null)
					br.close();

			} catch (Exception e2) {
				e2.printStackTrace();
			}

		}
		return task;

	}

	@Override    //Done
	public String addTask(TaskBean task, String catName,String email) {
		BufferedWriter bw = null;
		catName = myTaskUtility.camelCase(catName);
		try {
			if (bw == null) {
				bw = new BufferedWriter(new FileWriter(email+File.separator+catName + ".todo", true));
				bw.write(task.getTaskName() + ":" + task.getDescription() + ":" + task.getPriority() + ":"
						+ task.getPlannedEndDate() + ":" + task.getTags() + ":" + task.getCreatedDate() + ":"
						+ task.getStatus());
				bw.newLine();
				bw.flush();
			}

		} catch (IOException e) {
			e.printStackTrace();
			return "Oops something went wrong";

		} finally {

			try {
				if (bw != null)
					bw.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return Constants.SUCCESS;

	}

	@Override
	public boolean checkCategoryExists(String catName,String email) {
		catName = myTaskUtility.camelCase(catName);
		return new File(catName + ".todo").exists();

	}

	@Override
	public boolean CheckTaskExists(String catName, String taskName, String email) {
		catName = myTaskUtility.camelCase(catName);
		taskName = taskName.toLowerCase();
		File f = new File(catName + ".todo");
		BufferedReader br = null;
		String line;
		boolean b = false;
		try {
			br = new BufferedReader(new FileReader(f));
			while ((line = br.readLine()) != null) {

				if (line.contains(taskName)) {
					b = true;
				}

			}

		} catch (Exception e) {

			e.printStackTrace();
			return b;
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}

		}

		return b;
	}

	@Override
	public String updateTaskData(String catName, String taskName, String email, TaskBean bean, String task_id) {
		taskName = taskName.toLowerCase();
		catName = myTaskUtility.camelCase(catName);
		String msg = null;
		BufferedReader br = null;
		BufferedWriter bw = null;
		boolean flag = false;
		String newFilePath = System.getProperty("user.dir") + File.separator + email + File.separator  + "tempfile.todo";
		String currentFilePath = email + File.separator  + catName + ".todo";
		File currentFile = new File(currentFilePath);
		File newFile = new File(newFilePath);
		String line = null;
		String tempLine = null;
		try {
			br = new BufferedReader(new FileReader(currentFile));
			bw = new BufferedWriter(new FileWriter(newFile, true));
			while ((line = br.readLine()) != null) {
				flag = false;
				if (line.contains(taskName)) {
					String str = line;
						tempLine = line.replace(str,(bean.getTaskName() + ":" + bean.getDescription() + ":" + bean.getPriority() + ":"
								+ bean.getPlannedEndDate() + ":" + bean.getTags() + ":" + bean.getCreatedDate() + ":"
								+ bean.getStatus()));// status editing
						msg = "successfully updated task";
					

					flag = true;
				}
				if (!flag) {
					bw.write(line);
					bw.newLine();
				} else {
					bw.write(tempLine);
					bw.newLine();
				}
				bw.flush();

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
				if (bw != null)
					bw.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		if (currentFile.exists()) {
			if (!currentFile.delete())
				msg = "could not delete the old File";// No else given
		}
		if (newFile.exists()) {
			if (!newFile.renameTo(currentFile))
				msg = "could not rename the new file.......";
		}
		
		return msg;

	}

	@Override
	public void deleteCategory(String catName, String email) {
		catName = myTaskUtility.camelCase(catName);
		File f = new File(catName + ".todo");
		if (f.delete())
			System.out.println("the category was successfully removed.....");
		else
			System.out.println("removing category failed.....the category does not exist......");

	}

	@Override  //already implemented ass categList need to see if it is needed
	public void listOfCategories(String email) {
		int count = 0;
		File f = new File(myTaskUtility.currentSystemDirectory());
		f.getName().endsWith(".todo");
		String[] str = f.list();
		for (String s : str) {
			if (s.endsWith(".todo")) {
				System.out.println(s);
				count++;
			}
		}
		if (count == 0) {
			System.out.println("no categories Created yet");
		}

	}

	@Override
	public void listOfCategoriesAndTask(String email) {
		int count = 0;
		File f = new File(myTaskUtility.currentSystemDirectory());
		List<TaskBean> tasks = null;
		f.getName().endsWith(".todo");
		String[] str = f.list();
		for (String s : str) {
			if (s.endsWith(".todo")) {
				String catName = s.replace(".todo", "");
				System.out.println("category name ->" + catName);
				System.out.println("===============================================================");
				tasks = (List<TaskBean>) getTask(catName, null, null);//
				for (TaskBean task : tasks) {
					System.out.print("task name : " + task.getTaskName() + "\n" + "description : "
							+ task.getDescription() + "\n" + "Priority : " + task.getPriority() + "\n" + "tags :"
							+ task.getTags() + "\n" + "planned end date : " + task.getPlannedEndDate() + "\n"
							+ "created date :" + task.getCreatedDate() + "\n" + "status :" + task.getStatus() + "\n");
					System.out.println("____________________________________________________________");

				}
				System.out.println("===============================================================");
				count++;
			}
		}
		if (count == 0) {
			System.out.println("no categories Created yet");
		}

	}

	@Override       //done
	public List<String> categoryList(String email) {
		
		File f = new File(System.getProperty("user.dir")+File.separator+email);
		f.getName().endsWith(".todo");
		System.out.println("list of Existing categories");
		String[] str = f.list();
		List<String> list = Arrays.asList(str);
		
		return list;
	}

	@Override
	public boolean checkCategoryExistsDuringInitialization(String email) {
		int count = 0;
		boolean b = false;
		File f = new File(System.getProperty("user.dir"));
		f.getName().endsWith(".todo");
		String[] str = f.list();
		for (String s : str) {
			if (s.endsWith(".todo")) {

				count++;
				b = true;
			}
		}
		if (count == 0) {

			b = false;
		}
		return b;
	}

	@Override
	public boolean searchCategories(String catName, String path, String email) {
		catName = myTaskUtility.camelCase(catName);
		File f = new File(path + File.separator + catName + ".todo");
		if (f.exists())
			return true;

		return false;
	}

	@Override
	public String removeTask(String catName, String taskName, String email, UpdateTaskBean bean) {
		catName = myTaskUtility.camelCase(catName);
		taskName = taskName.toLowerCase();
		String msg = null;
		BufferedReader br = null;
		BufferedWriter bw = null;
		int count = 0;
		boolean flag = false;
		String newFilePath = System.getProperty("user.dir") + File.separator + email + File.separator  + "tempfile.todo";
		String currentFilePath = email + File.separator  + catName + ".todo";
		File currentFile = new File(currentFilePath);
		File newFile = new File(newFilePath);
		String line = null;
		try {
			br = new BufferedReader(new FileReader(currentFile));
			bw = new BufferedWriter(new FileWriter(newFile, true));
			while ((line = br.readLine()) != null) {
				if (line.contains(taskName)) {
					flag = true;
					count++;
					break;
				}
				if (!flag) {
					bw.write(line);
					bw.newLine();
				}
				bw.flush();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
				if (bw != null)
					bw.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		if (count > 0) {
			msg = "the task has been removed Successfully";
		} else {
			msg = "task does not exist";
		}
		if (currentFile.exists()) {
			if (!currentFile.delete())
				msg = "could not delete the old File";// No else given
		}
		if (newFile.exists()) {
			if (!newFile.renameTo(currentFile))
				msg = "could not rename the new file.......";
		}
		
		return msg;

	}

	@Override
	public void searchTask(String catName, String taskName, String email) {
		BufferedReader br = null;
		catName = myTaskUtility.camelCase(catName);
		taskName = taskName.toLowerCase();
		String fileName = catName + ".todo";
		String line = null;
		TaskBean task = null;
		try {

			br = new BufferedReader(new FileReader(fileName));
			while ((line = br.readLine()) != null) {
				if (line.contains(taskName)) {
					System.out.println("task name " + taskName + " found");
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					String[] str = line.split(":");
					task = new TaskBean(str[0], str[1], str[4], str[3], str[2],
							str[5], str[6]);
					;
				}
			}

			System.out.print("task name : " + task.getTaskName() + "\n" + "description : " + task.getDescription()
					+ "\n" + "Priority : " + task.getPriority() + "\n" + "tags :" + task.getTags() + "\n"
					+ "planned end date : " + task.getPlannedEndDate() + "\n" + "created date :" + task.getCreatedDate()
					+ "\n" + "status :" + task.getStatus() + "\n");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}

	@Override
	public List<TaskBean> listTasksBasedOnCreatedDate(String catName, String email) {

		TaskCreatedDateComparator tcdc = new TaskCreatedDateComparator();
		List<TaskBean> task = new ArrayList<TaskBean>();

		BufferedReader br = null;
		String path = catName + ".todo";
		String line = null;
		TaskBean tasks = null;

		try {
			br = new BufferedReader(new FileReader(path));
			while ((line = br.readLine()) != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				String[] stra = line.split(":");
				tasks = new TaskBean(stra[0], stra[1], stra[4], stra[3], stra[2],
						stra[5], stra[6]);
				task.add(tasks);
			}
			Collections.sort(task, tcdc);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				if (br != null)
					br.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return task;
	}

	@Override
	public List<TaskBean> listTasksBasedOnCreatedDate(String email) {

		Comparator<TaskBean> tcdc = new TaskCreatedDateComparator();
		List<TaskBean> task = new ArrayList<TaskBean>();
		BufferedReader br = null;
		String line = null;
		TaskBean tasks = null;
		String path = System.getProperty("user.dir");
		List<String> catName = new ArrayList<String>();

		File f = new File(path);
		String[] str = f.list();
		for (String s : str) {
			if (s.endsWith(".todo")) {
				catName.add(s);
			}
		}

		for (String s1 : catName) {

			try {
				br = new BufferedReader(new FileReader(s1));
				while ((line = br.readLine()) != null) {
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					String[] stra = line.split(":");
					tasks = new TaskBean(stra[0], stra[1], stra[4], stra[3], stra[2],
							stra[5], stra[6]);
					task.add(tasks);
				}
				// tcdc = Collections.reverseOrder();
				Collections.sort(task, tcdc);

			} catch (Exception e) {
				e.printStackTrace();
			} finally {

				try {
					if (br != null)
						br.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}

		}
		return task;
	}

	@Override
	public List<TaskBean> listTasksBasedOnPlannedEndDate(String catName, String email) {

		TaskPlannedEndDateComparator tpedc = new TaskPlannedEndDateComparator();
		List<TaskBean> task = new ArrayList<TaskBean>();

		BufferedReader br = null;
		String path = catName + ".todo";
		String line = null;
		TaskBean tasks = null;

		try {
			br = new BufferedReader(new FileReader(path));
			while ((line = br.readLine()) != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				String[] stra = line.split(":");
				tasks = new TaskBean(stra[0], stra[1], stra[4], stra[3], stra[2],
						stra[5], stra[6]);
				task.add(tasks);
			}
			Collections.sort(task, tpedc);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				if (br != null)
					br.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return task;
	}

	@Override
	public List<TaskBean> listTasksBasedOnLongestTime(String email) {

		Comparator<TaskBean> tcdc = new TaskCreatedDateComparator();
		List<TaskBean> task = new ArrayList<TaskBean>();
		BufferedReader br = null;
		String line = null;
		TaskBean tasks = null;
		String path = System.getProperty("user.dir");
		List<String> catName = new ArrayList<String>();

		File f = new File(path);
		String[] str = f.list();
		for (String s : str) {
			if (s.endsWith(".todo")) {
				catName.add(s);
			}
		}

		for (String s1 : catName) {

			try {
				br = new BufferedReader(new FileReader(s1));
				while ((line = br.readLine()) != null) {
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					String[] stra = line.split(":");
					tasks = new TaskBean(stra[0], stra[1], stra[4], stra[3], stra[2],
							stra[5], stra[6]);
					task.add(tasks);
				}
				tcdc = Collections.reverseOrder();
				Collections.sort(task, tcdc);

			} catch (Exception e) {
				e.printStackTrace();
			} finally {

				try {
					if (br != null)
						br.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}

		}
		return task;
	}

	@Override
	public List<TaskBean> listTasksBasedOnLongestTime(String catName, String email) {

		Comparator<TaskBean> tcdc = new TaskCreatedDateComparator();
		List<TaskBean> task = new ArrayList<TaskBean>();

		BufferedReader br = null;
		String path = catName + ".todo";
		String line = null;
		TaskBean tasks = null;

		try {
			br = new BufferedReader(new FileReader(path));
			while ((line = br.readLine()) != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				String[] stra = line.split(":");
				tasks = new TaskBean(stra[0], stra[1], stra[4], stra[3], stra[2],
						stra[5], stra[6]);
				task.add(tasks);
			}
			tcdc = Collections.reverseOrder();
			Collections.sort(task, tcdc);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				if (br != null)
					br.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return task;
	}

	@Override
	public List<TaskBean> listTasksBasedOnPlannedEndDate(String email) {

		TaskPlannedEndDateComparator tpedc = new TaskPlannedEndDateComparator();
		List<TaskBean> task = new ArrayList<TaskBean>();
		BufferedReader br = null;
		String line = null;
		TaskBean tasks = null;
		String path = System.getProperty("user.dir");
		List<String> catName = new ArrayList<String>();

		File f = new File(path);
		String[] str = f.list();
		for (String s : str) {
			if (s.endsWith(".todo")) {
				catName.add(s);
			}
		}

		for (String s1 : catName) {

			try {
				br = new BufferedReader(new FileReader(s1));
				while ((line = br.readLine()) != null) {
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					String[] stra = line.split(":");
					tasks = new TaskBean(stra[0], stra[1], stra[4], stra[3], stra[2],
							stra[5], stra[6]);
					task.add(tasks);
				}
				Collections.sort(task, tpedc);

			} catch (Exception e) {
				e.printStackTrace();
			} finally {

				try {
					if (br != null)
						br.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}

		}
		return task;
	}

	@Override
	public List<TaskBean> listTasksBasedOnTaskName(String catName, String email) {

		TaskNameBasedComparator tnbc = new TaskNameBasedComparator();
		List<TaskBean> task = new ArrayList<TaskBean>();

		BufferedReader br = null;
		String path = email + File.separator  + catName + ".todo";
		String line = null;
		TaskBean tasks = null;

		try {
			br = new BufferedReader(new FileReader(path));
			while ((line = br.readLine()) != null) {
				String[] stra = line.split(":");
				tasks = new TaskBean(stra[0], stra[1], stra[4], stra[3], stra[2],
						stra[5], stra[6]);
				task.add(tasks);
			}
			Collections.sort(task, tnbc);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				if (br != null)
					br.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return task;
	}

	@Override
	public List<TaskBean> listTasksBasedOnTaskName(String email) {

		TaskNameBasedComparator tnbc = new TaskNameBasedComparator();
		List<TaskBean> task = new ArrayList<TaskBean>();
		BufferedReader br = null;
		String line = null;
		TaskBean tasks = null;
		String path = System.getProperty("user.dir");
		List<String> catName = new ArrayList<String>();

		File f = new File(path);
		String[] str = f.list();
		for (String s : str) {
			if (s.endsWith(".todo")) {
				catName.add(s);
			}
		}

		for (String s1 : catName) {

			try {
				br = new BufferedReader(new FileReader(s1));
				while ((line = br.readLine()) != null) {
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					String[] stra = line.split(":");
					tasks = new TaskBean(stra[0], stra[1], stra[4], stra[3], stra[2],
							stra[5], stra[6]);
					task.add(tasks);
				}
				Collections.sort(task, tnbc);

			} catch (Exception e) {
				e.printStackTrace();
			} finally {

				try {
					if (br != null)
						br.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}

		}
		return task;
	}

	@Override
	public List<TaskBean> listTasksBasedOnOverDueTask(String email) {

		Comparator<TaskBean> tcdc = new TaskCreatedDateComparator();
		List<TaskBean> task = new ArrayList<TaskBean>();
		BufferedReader br = null;
		String line = null;
		TaskBean tasks = null;
		String path = System.getProperty("user.dir");
		List<String> catName = new ArrayList<String>();

		File f = new File(path);
		String[] str = f.list();
		for (String s : str) {
			if (s.endsWith(".todo")) {
				catName.add(s);
			}
		}

		for (String s1 : catName) {

			try {
				br = new BufferedReader(new FileReader(s1));
				while ((line = br.readLine()) != null) {
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					String[] stra = line.split(":");
					Date d = new Date();
					Date d1 = sdf.parse(stra[3]);
					if (d1.before(d)) {
						tasks = new TaskBean(stra[0], stra[1], stra[4], stra[3], stra[2],
								stra[5], stra[6]); 
						task.add(tasks);
					}
				}
				tcdc = Collections.reverseOrder();
				Collections.sort(task, tcdc);

			} catch (Exception e) {
				e.printStackTrace();
			} finally {

				try {
					if (br != null)
						br.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}

		}
		return task;
	}

	@Override
	public List<TaskBean> listTasksBasedOnOverDueTask(String catName, String email) {

		Comparator<TaskBean> tcdc = new TaskCreatedDateComparator();
		List<TaskBean> task = new ArrayList<TaskBean>();

		BufferedReader br = null;
		String path = catName + ".todo";
		String line = null;
		TaskBean tasks = null;

		try {
			br = new BufferedReader(new FileReader(path));
			while ((line = br.readLine()) != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				String[] stra = line.split(":");
				Date d = new Date();
				Date d1 = sdf.parse(stra[3]);
				if (d1.before(d)) {
					tasks = new TaskBean(stra[0], stra[1], stra[4], stra[3], stra[2], stra[5],
							stra[6]);
					task.add(tasks);
				}
			}
			tcdc = Collections.reverseOrder();
			Collections.sort(task, tcdc);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				if (br != null)
					br.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return task;
	}

	@Override
	public void searchAllCategory(String wordToBeSearched, String email) {

		List<String> found = new ArrayList<String>();
		List<String> found1 = new ArrayList<String>();
		List<String> found2 = new ArrayList<String>();
		BufferedReader br = null;
		String line = null;
		String path = System.getProperty("user.dir");
		List<String> catName = new ArrayList<String>();
		int count = 0, count1 = 0, count2 = 0;
		File f = new File(path);
		String[] str = f.list();
		for (String s : str) {
			if (s.endsWith(".todo")) {
				catName.add(s);
			}
		}

		for (String s1 : catName) {

			try {
				br = new BufferedReader(new FileReader(s1));
				String s2 = s1.replace(".todo", "");
				while ((line = br.readLine()) != null) {

					String[] stra = line.split(":");
					if (stra[0].contains(wordToBeSearched)) {// task name
						count++;
						found.add(s2 + "->" + stra[0]);
					}
					if (stra[1].contains(wordToBeSearched)) {// description
						count1++;
						found1.add(s2 + "->" + stra[1]);
					}
					if (stra[4].contains(wordToBeSearched)) {// tags
						count2++;
						found2.add(s2 + "->" + stra[4]);
					}

				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {

				try {
					if (br != null)
						br.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}

		}
		if (found.size() > 0) {
			System.out.println("number of occurence in task name ->" + count);
			for (String str1 : found) {
				System.out.println(str1);
			}
			System.out.println("============================================");
		}
		if (found1.size() > 0) {
			System.out.println("number of occurence in description ->" + count1);
			for (String str1 : found1) {
				System.out.println(str1);
			}
			System.out.println("============================================");
		}
		if (found2.size() > 0) {
			System.out.println("number of occurence in tags ->" + count2);
			for (String str1 : found2) {
				System.out.println(str1);
			}
			System.out.println("============================================");
		}

	}

	@Override
	public void searchAllTaskInCategory(String catName, String wordToBeSearched, String email) {

		List<String> found = new ArrayList<String>();
		List<String> found1 = new ArrayList<String>();
		List<String> found2 = new ArrayList<String>();
		BufferedReader br = null;
		String line = null;
		String path = catName + ".todo";
		int count = 0, count1 = 0, count2 = 0;
		File f = new File(path);

		try {
			br = new BufferedReader(new FileReader(f));
			while ((line = br.readLine()) != null) {
				String[] stra = line.split(":");
				if (stra[0].contains(wordToBeSearched)) {// task name
					count++;
					found.add(stra[0]);
				}
				if (stra[1].contains(wordToBeSearched)) {// description
					count1++;
					found1.add(stra[1]);
				}
				if (stra[4].contains(wordToBeSearched)) {// tags
					count2++;
					found2.add(stra[4]);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				if (br != null)
					br.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		if (found.size() > 0) {
			System.out.println("number of occurence in task name ->" + count);
			for (String str1 : found) {
				System.out.println(str1);
			}
			System.out.println("============================================");
		}
		if (found1.size() > 0) {
			System.out.println("number of occurence in description ->" + count1);
			for (String str1 : found1) {
				System.out.println(str1);
			}
			System.out.println("============================================");
		}
		if (found2.size() > 0) {
			System.out.println("number of occurence in tags ->" + count2);
			for (String str1 : found2) {
				System.out.println(str1);
			}
			System.out.println("============================================");
		}

	}

	@Override
	public List<String> taskList(String catName, String email) {

		File path = new File(System.getProperty("user.dir")+File.separator+email+File.separator+catName+".todo");
		System.out.println("list of Existing task");
		List<String> list = new ArrayList<String>();

		BufferedReader br = null;
		String line = null;
		String tasks = null;

		try {
			br = new BufferedReader(new FileReader(path));
			while ((line = br.readLine()) != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				String[] stra = line.split(":");
				tasks = stra[0];
				list.add(tasks);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				if (br != null)
					br.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return list;
	}

	@Override
	public String removeCat(String catName, String email) {
		catName = myTaskUtility.camelCase(catName);
		String msg = null;
		File f = new File(email+File.separator+catName + ".todo");
		if (f.delete())
			msg = "the category was successfully removed.....";
		else
			msg = "removing category failed.....the category does not exist......";
		
		return msg;
	}

}
