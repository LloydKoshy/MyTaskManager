package com.uttara.project.lloyd;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class Logger {

	private static Logger obj = null;
	private static boolean LOG_TO_MONITOR = false;

	private Logger() {

	}

	public static Logger getInstrance() {

		if (obj == null)
			obj = new Logger();
		return obj;

	}

	public void log(String data) {
		BufferedWriter bw = null;
		Date dt = new Date();
		try {
			String msg = dt + " : " + data;
			bw = new BufferedWriter(
					new FileWriter(System.getProperty("user.dir") + File.separator + "task_logger.txt", true));
			bw.write(msg);
			bw.newLine();
			bw.flush();
			if (Logger.LOG_TO_MONITOR == true) {
				System.out.println("Logger : " + msg);

			}

		} catch (IOException e) {

			e.printStackTrace();
		} finally {

			try {
				if (bw != null)
					bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

}
