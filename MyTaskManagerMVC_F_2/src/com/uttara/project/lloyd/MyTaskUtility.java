package com.uttara.project.lloyd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class MyTaskUtility {

	public void promptEnterKey() {
		System.out.println("press \"ENTER\" to continue..... ");
		try {
			System.in.read();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public boolean validateName(String str) {

		if (str == null)
			return false;
		if (str.trim().equals(""))
			return false;
		if (str.split(" ").length > 1)
			return false;
		if (!Character.isLetter(str.charAt(0)))
			return false;
		for (int i = 1; i < str.length(); i++) {
			char c = str.charAt(i);
			if (!(Character.isDigit(c) || Character.isLetter(c)))
				return false;
		}

		return true;
	}

	public boolean validateTaskName(String str) {
		if (str == null)
			return false;
		if (str.trim().equals(""))
			return false;

		return true;
	}

	public boolean validateCatNameExists(String catName) {
		File f = new File(catName + ".todo");
		if (!f.exists())
			return false;

		return true;

	}

	public boolean validateCatNameEmpty(String catName) {
		File f = new File(catName + ".todo");
		if (f.length() == 0)
			return false;

		return true;

	}

	public boolean checkCategoryExists(String catName) {

		File f = new File(catName + ".todo");
		if (f.exists()) {
			return true;
		}
		return false;

	}

	public String currentSystemDirectory() {

		String dir = "";
		dir = System.getProperty("user.dir");

		return dir;
	}

	public String camelCase(String str) {
		str = str.toLowerCase();
		str = str.substring(0, 1).toUpperCase() + str.substring(1);
		return str;
	}

	public boolean validateDateFormat(String date) {
		if (date.trim().equals(""))
			return false;
		DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setLenient(false);
		try {
			sdf.parse(date);
		} catch (ParseException e) {
			System.out.println("please enter a valid date format....");
			return false;
		}
		return true;

	}

	public boolean validatePriority(String priority) {
		int i = 0;
		try {
			i = Integer.parseInt(priority);
		} catch (Exception e) {
			return false;
		}
		if (!(i >= 0 && i <= 10)) {
			return false;
		} else
			return true;
	}

	public boolean checkTaskNameUnique(String catName, String taskName) {
		BufferedReader br = null;
		catName = camelCase(catName);
		taskName = taskName.toLowerCase();
		String fileName = catName + ".todo";
		String line = null;
		boolean b = false;
		try {

			br = new BufferedReader(new FileReader(fileName));
			while ((line = br.readLine()) != null) {
				if (line.contains(taskName)) {
					b = true;
					break;
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
		return b;

	}

}
