package com.uttara.project.lloyd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class ModelBL {

	Logger logger = Logger.getInstrance();

	public String register(RegBean bean) {

		logger.log("model -> Regiter()");
		return bean.validate();

	}

	public String login(LoginBean bean) {
		logger.log("model ->login()");
		return bean.validate();

	}

	public String checkSuccess(RegBean bean) {
		logger.log("model -> checkSuccess()");
		String email = bean.getEmail();
		String name = bean.getUname();
		String pass = bean.getPass();
		ResultSet rs = null;
		JDBCConnectionEstablisher connector = new JDBCConnectionEstablisher();
		Connection con = connector.connect();
		logger.log("model -> checkSuccess() -> connecting to db");
		PreparedStatement ps_sel = null;
		PreparedStatement ps_ins = null;
		try {
			String sel = "SELECT * FROM TM_USER_DB WHERE email=?";
			ps_sel = con.prepareStatement(sel);
			ps_sel.setString(1, email);
			rs = ps_sel.executeQuery();
			if (rs.next()) {
//				failed
				logger.log("model -> checkSuccess() -> invalid mail id");
				return "mail ID exists already!!!! try a different mail ID else Login...";

			} else {
//				success
				ps_ins = con.prepareStatement("INSERT INTO TM_USER_DB (Name,Email,Pass) VALUES (?,?,?)");
				ps_ins.setString(1, name);
				ps_ins.setString(2, email);
				ps_ins.setString(3, pass);
				boolean bool = ps_ins.execute();
				System.out.println(bool);
				if (bool) {
					logger.log("model -> checkSuccess() -> registration failed");
					return "Registration failed please try again";
				} else {
					logger.log("model -> checkSuccess() -> successful registration");
					return "hoooray...Successfully registered!!!!!";
				}

			}

		} catch (Exception e) {
			logger.log("model -> checkSuccess() -> Exception occured -> " + e.getStackTrace());
			// return e.getMessage();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.log("model -> checkSuccess() -> rs close -> Exception occured -> " + e.getStackTrace());
				}
			}

			if (ps_ins != null) {
				try {
					ps_ins.close();
				} catch (SQLException e) {
					logger.log("model -> checkSuccess() -> ps_ins close -> Exception occured -> " + e.getStackTrace());
					// e.printStackTrace();
				}
			}

			if (ps_sel != null) {
				try {
					ps_sel.close();
				} catch (SQLException e) {
					logger.log("model -> checkSuccess() -> ps_sel close -> Exception occured -> " + e.getStackTrace());
					// e.printStackTrace();
				}
			}

			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					logger.log("model -> checkSuccess() -> con close -> Exception occured -> " + e.getStackTrace());
					// e.printStackTrace();
				}
			}
		}
		return "oops something went wrong....plz try again!!!";
	}

	public String verify(LoginBean bean) {

		logger.log("model -> verify() login details");
		String email = bean.getEmail();
		String pass = bean.getPass();
		JDBCConnectionEstablisher connector = new JDBCConnectionEstablisher();
		Connection con = connector.connect();
		logger.log("model -> verify() login details -> connecting to db");
		PreparedStatement ps_sel = null;
		ResultSet rs = null;
		try {

			ps_sel = con.prepareStatement("SELECT * FROM TM_USER_DB WHERE email=? ");
			ps_sel.setString(1, email);
			rs = ps_sel.executeQuery();
			String name = null;
			if (rs.next()) {
				String pass1 = rs.getString("pass");
				name = rs.getString("name");
				boolean b = pass1.equals(pass);
				System.out.println(b);
				if (!b) {
					logger.log("model -> verify() login details -> wrong password");
					return "wrong passsword";
				} else {
					logger.log("model -> verify() login details -> login success");
					return "SUCCESS";
				}

			} else {
				logger.log("model -> verify() login details -> invalid email id");
				return "Email ID does not exist.... please create an account!!!";
			}

		} catch (Exception e) {
			logger.log("model -> verify() login details -> exception occured -> " + e.getStackTrace());
			// e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.log("model -> verify() -> rs close -> Exception occured -> " + e.getStackTrace());
				}
			}

			if (ps_sel != null) {
				try {
					ps_sel.close();
				} catch (SQLException e) {
					logger.log("model -> verify() -> ps_sel close -> Exception occured -> " + e.getStackTrace());
					// e.printStackTrace();
				}
			}

			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					logger.log("model -> verify() -> con close -> Exception occured -> " + e.getStackTrace());
					// e.printStackTrace();
				}
			}
		}
		return "oops something went wrong....";

	}

	public String userName(LoginBean bean) {
		logger.log("model -> userName()");
		String email = bean.getEmail();
		JDBCConnectionEstablisher connector = new JDBCConnectionEstablisher();
		Connection con = connector.connect();
		logger.log("model -> userName() -> connecting to db");
		PreparedStatement ps_sel = null;
		String name = null;
		ResultSet rs = null;
		try {

			ps_sel = con.prepareStatement("SELECT * FROM TM_USER_DB WHERE email=? ");
			ps_sel.setString(1, email);
			rs = ps_sel.executeQuery();

			if (rs.next()) {
				logger.log("model -> userName() -> contact name retrieved successfully");
				name = rs.getString("name");
			}

		} catch (Exception e) {
			logger.log("model -> userName() -> exception occured -> " + e.getStackTrace());
			// e.printStackTrace();
		} finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.log("model -> userName() -> rs close -> Exception occured -> " + e.getStackTrace());
				}
			}

			if (ps_sel != null) {
				try {
					ps_sel.close();
				} catch (SQLException e) {
					logger.log("model -> userName() -> ps_sel close -> Exception occured -> " + e.getStackTrace());
					// e.printStackTrace();
				}
			}

			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					logger.log("model -> userName() -> connection close -> exception occured -> " + e.getStackTrace());
					// e.printStackTrace();
				}
			}
		}
		return name;

	}

	public String createCategory(String catName, String storageMedium, String email) {

		int id = 0;
		TaskManagerDAO storage = null;
		String msg = null;

		if (storageMedium.equals("offline")) {
			id = 1;
			storage = TM_DAOFactory.getInstance(id);
			msg = storage.createCategory(catName, email);
		}

		if (storageMedium.equals("online")) {
			id = 2;
			storage = TM_DAOFactory.getInstance(id);
			msg = storage.createCategory(catName, email);
		}

		if (storageMedium.equals("both")) {

			storage = TM_DAOFactory.getInstance(1);
			msg = storage.createCategory(catName, email);
			storage = TM_DAOFactory.getInstance(2);
			msg = storage.createCategory(catName, email);

		}
		System.out.println("modelBL() -> createCat");
		return msg;
	}

	public List<String> categotyList(String storageMedium, String email) {
		int id = 0;
		TaskManagerDAO storage = null;
		List<String> list = new ArrayList<String>();

		if (storageMedium.equals("offline")) {
			id = 1;
			storage = TM_DAOFactory.getInstance(id);
			list = storage.categoryList(email);
		}

		if (storageMedium.equals("online")) {
			id = 2;
			storage = TM_DAOFactory.getInstance(id);
			list = storage.categoryList(email);
		}

		if (storageMedium.equals("both")) {

			Set<String> set = new TreeSet<String>();
			storage = TM_DAOFactory.getInstance(1);
			set.addAll(storage.categoryList(email));
			storage = TM_DAOFactory.getInstance(2);
			set.addAll(storage.categoryList(email));
			list.addAll(set);

		}
		System.out.println("modelBL() -> createCat");
		return list;
	}

	public List<String> taskList(String storageMedium, String catName, String email) {
		int id = 0;
		TaskManagerDAO storage = null;
		List<String> list = new ArrayList<String>();

		if (storageMedium.equals("offline")) {
			id = 1;
			storage = TM_DAOFactory.getInstance(id);
			list = storage.taskList(catName, email);
		}

		if (storageMedium.equals("online")) {
			id = 2;
			storage = TM_DAOFactory.getInstance(id);
			list = storage.taskList(catName, email);
		}

		if (storageMedium.equals("both")) {

			Set<String> set = new TreeSet<String>();
			storage = TM_DAOFactory.getInstance(1);
			set.addAll(storage.taskList(catName, email));
			storage = TM_DAOFactory.getInstance(2);
			set.addAll(storage.taskList(catName, email));
			list.addAll(set);

		}
		System.out.println("modelBL() -> createCat");
		return list;
	}

	public String addTask(String catName, String storageMedium, String email, TaskBean task) {

		int id = 0;
		TaskManagerDAO storage = null;
		String msg = null;

		if (storageMedium.equals("offline")) {
			id = 1;
			storage = TM_DAOFactory.getInstance(id);
			msg = storage.addTask(task, catName, email);
		}

		if (storageMedium.equals("online")) {
			id = 2;
			storage = TM_DAOFactory.getInstance(id);
			msg = storage.addTask(task, catName, email);
		}

		if (storageMedium.equals("both")) {

			Set<String> set = new TreeSet<String>();
			storage = TM_DAOFactory.getInstance(1);
			msg = storage.addTask(task, catName, email);
			storage = TM_DAOFactory.getInstance(2);
			msg = storage.addTask(task, catName, email);

		}
		System.out.println("modelBL() -> createCat");
		return msg;

	}

	public UpdateTaskBean getTask(String catName, String email, String storageMedium, String taskName) {

		int id = 0;
		TaskManagerDAO storage = null;
		UpdateTaskBean bean = null;

		if (storageMedium.equals("offline")) {
			id = 1;
			storage = TM_DAOFactory.getInstance(id);
			bean = storage.getTask(catName, email, taskName);
		}

		if (storageMedium.equals("online")) {
			id = 2;
			storage = TM_DAOFactory.getInstance(id);
			bean = storage.getTask(catName, email, taskName);
		}

		if (storageMedium.equals("both")) {

			Set<String> set = new TreeSet<String>();
			storage = TM_DAOFactory.getInstance(1);
			bean = storage.getTask(catName, email, taskName);
			storage = TM_DAOFactory.getInstance(2);
			bean = storage.getTask(catName, email, taskName);

		}

		return bean;
	}

	public String updateTaskData(String catName, String taskName, String email, String storageMedium, TaskBean bean,
			String task_id) {
		String msg = null;

		int id = 0;
		TaskManagerDAO storage = null;

		if (storageMedium.equals("offline")) {
			id = 1;
			storage = TM_DAOFactory.getInstance(id);
			msg = storage.updateTaskData(catName, taskName, email, bean, task_id);
		}

		if (storageMedium.equals("online")) {
			id = 2;
			storage = TM_DAOFactory.getInstance(id);
			msg = storage.updateTaskData(catName, taskName, email, bean, task_id);
		}

		return msg;
	}

	public String removeTask(String catName, String email, String storageMedium, String taskName, UpdateTaskBean bean) {

		int id = 0;
		String msg = null;
		TaskManagerDAO storage = null;

		if (storageMedium.equals("offline")) {
			id = 1;
			storage = TM_DAOFactory.getInstance(id);
			msg = storage.removeTask(catName, taskName, email, bean);
		}

		if (storageMedium.equals("online")) {
			id = 2;
			storage = TM_DAOFactory.getInstance(id);
			msg = storage.removeTask(catName, taskName, email, bean);
		}

		return msg;
	}
	
	public String removeCategory(String catName, String email, String storageMedium) {

		int id = 0;
		String msg = null;
		TaskManagerDAO storage = null;

		if (storageMedium.equals("offline")) {
			id = 1;
			storage = TM_DAOFactory.getInstance(id);
			msg = storage.removeCat(catName, email);
		}

		if (storageMedium.equals("online")) {
			id = 2;
			storage = TM_DAOFactory.getInstance(id);
			msg = storage.removeCat(catName, email);
		}
		
		System.out.println("Removecat()");

		return msg;
	}
	
	public List<TaskBean> listTasksBasedOnTaskName(String catName, String email, String storageMedium){
		int id = 0;
		String msg = null;
		TaskManagerDAO storage = null;
		List<TaskBean> list = new ArrayList<TaskBean>();

		if (storageMedium.equals("offline")) {
			id = 1;
			storage = TM_DAOFactory.getInstance(id);
			list = storage.listTasksBasedOnTaskName(catName, email);
		}

		if (storageMedium.equals("online")) {
			id = 2;
			storage = TM_DAOFactory.getInstance(id);
			list = storage.listTasksBasedOnTaskName(catName, email);
		}

		return list;
	}

}
