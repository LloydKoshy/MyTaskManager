package com.uttara.project.lloyd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class HSQLDBDAO implements TaskManagerDAO {

	Logger logger = Logger.getInstrance();

	@Override
	public String createCategory(String catName, String email) {

		JDBCConnectionEstablisher connector = new JDBCConnectionEstablisher();
		String msg = null;
		String slNo = null;
		Connection con = null;
		logger.log("modelbl -> createCategory() -> connecting db");
		PreparedStatement ps_sel = null;
		PreparedStatement ps_ins = null;
		ResultSet rs = null;
		boolean b = false;

		try {

			con = connector.connect();
			System.out.println("JDBC connection established");
			ps_sel = con.prepareStatement("SELECT * FROM TM_User_db WHERE Email=?");
			ps_sel.setString(1, email);
			rs = ps_sel.executeQuery();
			if (rs.next()) {
				slNo = rs.getString("id");
			}
			System.out.println("the user sl no " + slNo);
			if (slNo != null) {
				logger.log("model -> craetecategory() -> inserting into Category");
				ps_ins = con.prepareStatement("INSERT INTO CATEGORIES (Name, TM_User_db_id) VALUES (?,?);",
						Statement.RETURN_GENERATED_KEYS);
				ps_ins.setString(1, (catName));
				ps_ins.setString(2, slNo);
				b = ps_ins.execute();
				System.out.println(b);
				if (!b) {
					msg = "success";
				} else {
					msg = "failed to create category";
				}
			} else {
				msg = "Kindly Login and try again";
			}

		} catch (Exception e) {
			logger.log("modelbl -> craetecategory() -> exception occured");
			msg = "category Exists!!!!....try diffrent category name";
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.log("model -> craetecategory() -> rs close -> Exception occured -> " + e.getStackTrace());
				}
			}

			if (ps_sel != null) {
				try {
					ps_sel.close();
				} catch (SQLException e) {
					logger.log(
							"model -> craetecategory() -> ps_sel close -> Exception occured -> " + e.getStackTrace());
					// e.printStackTrace();
				}
			}

			if (ps_ins != null) {
				try {
					ps_sel.close();
				} catch (SQLException e) {
					logger.log(
							"model -> craetecategory() -> ps_sel close -> Exception occured -> " + e.getStackTrace());
					// e.printStackTrace();
				}
			}

			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					logger.log(
							"model -> craetecategory() -> ps_sel close -> Exception occured -> " + e.getStackTrace());
					// e.printStackTrace();
				}
			}
		}

		return msg;
	}

	@Override
	public UpdateTaskBean getTask(String catName, String email, String taskName) {

		JDBCConnectionEstablisher connector = new JDBCConnectionEstablisher();
		UpdateTaskBean bean = new UpdateTaskBean();
		String msg = null;
		String slNo = null;
		String name = taskName;
		String description = null;
		String tags = null;
		String startDate = null;
		String pldDate = null;
		String priority = null;
		String status = null;
		String cat_ID = null;
		String task_id = null;
		String priority_ID = null;
		String status_ID = null;
		Connection con = null;
		logger.log("modelbl -> getTask() -> connecting db");
		PreparedStatement ps_sel = null;
		PreparedStatement ps_ins = null;
		ResultSet rs = null;

		try {

			con = connector.connect();
			System.out.println("JDBC connection established");
			ps_sel = con.prepareStatement("SELECT * FROM TM_User_db WHERE Email=?");
			ps_sel.setString(1, email);
			rs = ps_sel.executeQuery();
			if (rs.next()) {
				slNo = rs.getString("id");
			}
			System.out.println("the user sl no " + slNo);
			if (slNo != null) {
				ps_sel = con.prepareStatement("SELECT * FROM CATEGORIES WHERE Name=? AND TM_USER_DB_ID=?;");
				ps_sel.setString(1, catName);
				ps_sel.setString(2, slNo);
				rs = ps_sel.executeQuery();
				if (rs.next()) {
					cat_ID = rs.getString("id");
				}

				logger.log("model -> craetecategory() -> inserting into Tasks");
				ps_sel = con.prepareStatement("SELECT * FROM TASKS WHERE Name = ? AND Categories_id = ?;");
				ps_sel.setString(1, name);
				ps_sel.setString(2, cat_ID);
				rs = ps_sel.executeQuery();
				if (rs.next()) {
					task_id = rs.getString("id");
					description = rs.getString("Description");
					startDate = rs.getString("St_date");
					pldDate = rs.getString("End_date");
					priority_ID = rs.getString("priority_ID");
					status_ID = rs.getString("status_ID");
				}

				ps_sel = con.prepareStatement("SELECT * FROM TAGS WHERE Task_id = ?;");
				ps_sel.setString(1, task_id);
				ps_sel.executeQuery();
				rs = ps_sel.executeQuery();
				if (rs.next()) {
					tags = rs.getString("tags");
				}

				ps_sel = con.prepareStatement("SELECT * FROM PRIORITY_MASTER WHERE id=?");
				ps_sel.setString(1, priority_ID);
				rs = ps_sel.executeQuery();
				if (rs.next()) {
					priority = rs.getString("id");
				}

				ps_sel = con.prepareStatement("SELECT * FROM STATUS_MASTER WHERE id=?");
				ps_sel.setString(1, status_ID);
				rs = ps_sel.executeQuery();
				if (rs.next()) {
					status = rs.getString("status");
				}

				bean.setId_no(task_id);
				bean.setTaskName(name);
				bean.setDescription(description);
				bean.setTags(tags);
				bean.setPlannedEndDate(pldDate);
				bean.setCreatedDate(startDate);
				bean.setPriority(priority);
				bean.setStatus(status);

			}

		} catch (Exception e) {
			logger.log("modelbl -> getTask() -> exception occured");
			;
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.log("model -> getTask() -> rs close -> Exception occured -> " + e.getStackTrace());
				}
			}

			if (ps_sel != null) {
				try {
					ps_sel.close();
				} catch (SQLException e) {
					logger.log("model -> getTask() -> ps_sel close -> Exception occured -> " + e.getStackTrace());
					// e.printStackTrace();
				}
			}

			if (ps_ins != null) {
				try {
					ps_sel.close();
				} catch (SQLException e) {
					logger.log("model -> getTask() -> ps_sel close -> Exception occured -> " + e.getStackTrace());
					// e.printStackTrace();
				}
			}

			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					logger.log("model -> getTask() -> ps_sel close -> Exception occured -> " + e.getStackTrace());
					// e.printStackTrace();
				}
			}
		}

		return bean;

	}

	@Override
	public String addTask(TaskBean bean, String catName, String email) {

		JDBCConnectionEstablisher connector = new JDBCConnectionEstablisher();
		String msg = null;
		String slNo = null;
		String name = bean.getTaskName();
		String description = bean.getDescription();
		String tags = bean.getTags();
		String startDate = bean.getCreatedDate();
		String pldDate = bean.getPlannedEndDate();
		String priority = bean.getPriority();
		String status = bean.getStatus();
		String cat_ID = null;
		String priority_ID = null;
		String status_ID = null;
		int sectionID = 0;
		Connection con = null;
		logger.log("modelbl -> createCategory() -> connecting db");
		PreparedStatement ps_sel = null;
		PreparedStatement ps_ins = null;
		ResultSet rs = null;
		boolean b = false;

		try {

			con = connector.connect();
			System.out.println("JDBC connection established");
			ps_sel = con.prepareStatement("SELECT * FROM TM_User_db WHERE Email=?");
			ps_sel.setString(1, email);
			rs = ps_sel.executeQuery();
			if (rs.next()) {
				slNo = rs.getString("id");
			}
			System.out.println("the user sl no " + slNo);
			if (slNo != null) {
				ps_sel = con.prepareStatement("SELECT * FROM CATEGORIES WHERE Name=? AND TM_USER_DB_ID=?;");
				ps_sel.setString(1, catName);
				ps_sel.setString(2, slNo);
				rs = ps_sel.executeQuery();
				if (rs.next()) {
					cat_ID = rs.getString("id");
					System.out.println("cat_id-> "+cat_ID);
				}

				ps_sel = con.prepareStatement("SELECT * FROM PRIORITY_MASTER WHERE Priority=?");
				ps_sel.setString(1, priority);
				rs = ps_sel.executeQuery();
				if (rs.next()) {
					priority_ID = rs.getString("id");
				}

				ps_sel = con.prepareStatement("SELECT * FROM STATUS_MASTER WHERE Status=?");
				ps_sel.setString(1, status);
				rs = ps_sel.executeQuery();
				if (rs.next()) {
					status_ID = rs.getString("id");
				}

				logger.log("model -> addTask -> inserting into Tasks");
				ps_ins = con.prepareStatement(
						"INSERT INTO TASKS (Name,Description,St_date,End_date,Categories_id,Priority_id,Status_id) VALUES (?,?,?,?,?,?,?);",
						Statement.RETURN_GENERATED_KEYS);
				ps_ins.setString(1, name);
				ps_ins.setString(2, description);
				ps_ins.setString(3, startDate);
				ps_ins.setString(4, pldDate);
				ps_ins.setString(5, cat_ID);
				ps_ins.setString(6, priority_ID);
				ps_ins.setString(7, status_ID);
				b = ps_ins.execute();
				System.out.println(b);
				ResultSet generatedValue = ps_ins.getGeneratedKeys();
				if (generatedValue.next()) {
					sectionID = generatedValue.getInt(1);
					System.out.println(sectionID);
				}

				ps_ins = con.prepareStatement("INSERT INTO TAGS (Task_id,Tags) VALUES (?,?);");
				ps_ins.setInt(1, sectionID);
				ps_ins.setString(2, tags);
				ps_ins.execute();

				if (!b) {
					msg = "success";
				} else {
					msg = "failed to create category";
				}
			} else {
				msg = "Kindly Login and try again";
			}

		} catch (Exception e) {
			logger.log("modelbl -> addTask() -> exception occured");
			msg = "category Exists!!!!....try diffrent category name";
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.log("model -> addTask() -> rs close -> Exception occured -> " + e.getStackTrace());
				}
			}

			if (ps_sel != null) {
				try {
					ps_sel.close();
				} catch (SQLException e) {
					logger.log("model -> addTask() -> ps_sel close -> Exception occured -> " + e.getStackTrace());
					// e.printStackTrace();
				}
			}

			if (ps_ins != null) {
				try {
					ps_sel.close();
				} catch (SQLException e) {
					logger.log("model -> addTask() -> ps_sel close -> Exception occured -> " + e.getStackTrace());
					// e.printStackTrace();
				}
			}

			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					logger.log("model -> addTask() -> ps_sel close -> Exception occured -> " + e.getStackTrace());
					// e.printStackTrace();
				}
			}
		}

		return msg;
	}

	@Override
	public boolean checkCategoryExists(String catName, String email) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean CheckTaskExists(String catName, String taskName, String email) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String updateTaskData(String catName, String taskName, String email, TaskBean bean, String task_id) {

		logger.log("model -> updateContact()");
		String msg = null;
		JDBCConnectionEstablisher connector = new JDBCConnectionEstablisher();
		logger.log("model -> updateContact() -> connecting db");
		Connection con = connector.connect();
		PreparedStatement ps_sel = null;
		PreparedStatement ps_ins = null;
		PreparedStatement ps_up = null;
		ResultSet rs = null;
		boolean b = false;
		String slNo = null;
		String name = bean.getTaskName();
		String description = bean.getDescription();
		String tags = bean.getTags();
		String startDate = bean.getCreatedDate();
		String pldDate = bean.getPlannedEndDate();
		String priority = bean.getPriority();
		String status = bean.getStatus();
		String cat_ID = null;
		String priority_ID = null;
		String status_ID = null;

		try {
			ps_sel = con.prepareStatement("SELECT * FROM TM_User_db WHERE Email=?");
			ps_sel.setString(1, email);
			rs = ps_sel.executeQuery();
			if (rs.next()) {
				slNo = rs.getString("id");
			}
			ps_sel.close();
			rs.close();

			ps_sel = con.prepareStatement("SELECT * FROM CATEGORIES WHERE Name=? AND TM_USER_DB_ID=?;");
			ps_sel.setString(1, catName);
			ps_sel.setString(2, slNo);
			rs = ps_sel.executeQuery();
			if (rs.next()) {
				cat_ID = rs.getString("id");
			}

			ps_sel = con.prepareStatement("SELECT * FROM PRIORITY_MASTER WHERE Priority=?");
			ps_sel.setString(1, priority);
			rs = ps_sel.executeQuery();
			if (rs.next()) {
				priority_ID = rs.getString("id");
			}

			ps_sel = con.prepareStatement("SELECT * FROM STATUS_MASTER WHERE Status=?");
			ps_sel.setString(1, status);
			rs = ps_sel.executeQuery();
			if (rs.next()) {
				status_ID = rs.getString("id");
			}

			logger.log("model -> updateTask -> inserting into Tasks");

			if (slNo != null) {
				ps_up = con.prepareStatement(
						"UPDATE TASKS SET Name=(?),Description=(?),St_date=(?),End_date=(?),Priority_id=(?),Status_id=(?) WHERE Categories_id = (?) AND id = (?);");
				ps_up.setString(1, name);
				ps_up.setString(2, description);
				ps_up.setString(3, startDate);
				ps_up.setString(4, pldDate);
				ps_up.setString(5, priority_ID);
				ps_up.setString(6, status_ID);
				ps_up.setString(7, cat_ID);
				ps_up.setString(8, task_id);
				b = ps_up.execute();
				System.out.println("update tags");
				ps_up = con.prepareStatement("UPDATE TAGS SET Tags=(?) WHERE Task_id = (?);");
				ps_up.setString(1, tags);
				ps_up.setString(2, task_id);
				boolean b1 = ps_up.execute();
				if (!b) {
					System.out.println("Successfully updated Task");
				} else {
					System.out.println("failed to update");
				}
				if (!b) {
					msg = "Successfully updated Task";
				} else {
					msg = "failed to update";
				}

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.log("model -> updateTask() -> rs close -> Exception occured -> " + e.getStackTrace());
				}
			}

			if (ps_sel != null) {
				try {
					ps_sel.close();
				} catch (SQLException e) {
					logger.log("model -> updateTask() -> ps_sel close -> Exception occured -> " + e.getStackTrace());
					// e.printStackTrace();
				}
			}

			if (ps_ins != null) {
				try {
					ps_sel.close();
				} catch (SQLException e) {
					logger.log("model -> updateTask() -> ps_sel close -> Exception occured -> " + e.getStackTrace());
					// e.printStackTrace();
				}
			}

			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					logger.log("model -> updateTask() -> ps_sel close -> Exception occured -> " + e.getStackTrace());
					// e.printStackTrace();
				}
			}
		}

		return msg;
	}

	@Override
	public void deleteCategory(String catName, String email) {
		// TODO Auto-generated method stub

	}

	@Override
	public void listOfCategories(String email) {
		// TODO Auto-generated method stub

	}

	@Override
	public void listOfCategoriesAndTask(String email) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<String> categoryList(String email) {
		JDBCConnectionEstablisher connector = new JDBCConnectionEstablisher();
		List<String> list = new ArrayList<String>();
		String slNo = null;
		Connection con = null;
		logger.log("modelbl -> CategoryList() -> connecting db");
		PreparedStatement ps_sel = null;
		ResultSet rs = null;
		boolean b = false;

		try {

			con = connector.connect();
			System.out.println("JDBC connection established");
			ps_sel = con.prepareStatement("SELECT * FROM TM_User_db WHERE Email=?");
			ps_sel.setString(1, email);
			rs = ps_sel.executeQuery();
			if (rs.next()) {
				slNo = rs.getString("id");
			}
			System.out.println("the user sl no " + slNo);
			if (slNo != null) {
				logger.log("model -> CategoryList() -> inserting into Category");
				ps_sel = con.prepareStatement("SELECT * FROM Categories WHERE TM_USER_DB_ID = ? ;");
				ps_sel.setString(1, slNo);
				rs = ps_sel.executeQuery();
				while (rs.next()) {
					list.add(rs.getString("Name"));
				}
			}

		} catch (Exception e) {
			logger.log("modelbl -> CategoryList() -> exception occured");
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.log("model -> cCategoryList() -> rs close -> Exception occured -> " + e.getStackTrace());
				}
			}

			if (ps_sel != null) {
				try {
					ps_sel.close();
				} catch (SQLException e) {
					logger.log("model -> CategoryList() -> ps_sel close -> Exception occured -> " + e.getStackTrace());
					// e.printStackTrace();
				}
			}

			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					logger.log("model -> CategoryList() -> ps_sel close -> Exception occured -> " + e.getStackTrace());
					// e.printStackTrace();
				}
			}
		}

		return list;
	}

	@Override
	public boolean checkCategoryExistsDuringInitialization(String email) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean searchCategories(String catName, String path, String email) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String removeTask(String catName, String taskName, String email, UpdateTaskBean bean) {

		logger.log("model -> removeTask()");
		String msg = null;
		boolean b = false, b1 = false;
		JDBCConnectionEstablisher connector = new JDBCConnectionEstablisher();
		logger.log("model -> removeTask() -> connecting db");
		Connection con = connector.connect();
		PreparedStatement ps_del = null, ps_del1 = null;
		String id_no = bean.getId_no();
		String TName = bean.getTaskName();
		try {

			ps_del = con.prepareStatement("DELETE FROM TAGS WHERE Task_id =(?)");
			ps_del.setString(1, id_no);
			b = ps_del.execute();
			if (!b) {
				ps_del1 = con.prepareStatement("DELETE FROM TASKS WHERE ID =(?)");
				ps_del1.setString(1, id_no);
				b1 = ps_del1.execute();
				if (!b1) {
					return "successfully deleted " + TName;
				} else {
					return "deleting " + TName + " failed";
				}
			}

		} catch (Exception e) {
			logger.log("model -> removeTask() -> exception occured");
			// e.printStackTrace();
		} finally {
			if (ps_del != null) {
				try {
					ps_del.close();
				} catch (SQLException e) {
					logger.log("model -> removeTask() -> ps_del close -> " + e.getStackTrace());
					// e.printStackTrace();
				}
			}

			if (ps_del1 != null) {
				try {
					ps_del1.close();
				} catch (SQLException e) {
					logger.log("model -> removeTask() -> ps_del1 close -> " + e.getStackTrace());
					// e.printStackTrace();
				}
			}

			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					logger.log("model -> removeTask() -> con close -> " + e.getStackTrace());
					// e.printStackTrace();
				}
			}

		}

		return null;

	}

	@Override
	public void searchTask(String catName, String taskName, String email) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<TaskBean> listTasksBasedOnCreatedDate(String catName, String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TaskBean> listTasksBasedOnCreatedDate(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TaskBean> listTasksBasedOnPlannedEndDate(String catName, String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TaskBean> listTasksBasedOnLongestTime(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TaskBean> listTasksBasedOnLongestTime(String catName, String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TaskBean> listTasksBasedOnPlannedEndDate(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TaskBean> listTasksBasedOnTaskName(String catName, String email) {
		logger.log("model -> updateContact()");
		String msg = null;
		TaskBean bean = null;
		List<TaskBean> list = new ArrayList<TaskBean>();
		JDBCConnectionEstablisher connector = new JDBCConnectionEstablisher();
		logger.log("model -> updateContact() -> connecting db");
		Connection con = connector.connect();
		PreparedStatement ps_sel = null, ps_sel1 = null;
		ResultSet rs = null, rs1 = null;
		String task_id = null;
		String slNo = null;
		String name = null;
		String description = null;
		String tags = null;
		String startDate = null;
		String pldDate = null;
		String priority = null;
		String status = null;
		String cat_ID = null;
		String priority_ID = null;
		String status_ID = null;
		try {

			ps_sel = con.prepareStatement("SELECT * FROM TM_User_db WHERE Email=?");
			ps_sel.setString(1, email);
			rs = ps_sel.executeQuery();
			if (rs.next()) {
				slNo = rs.getString("id");
			}

			ps_sel.close();
			rs.close();

			ps_sel = con.prepareStatement("SELECT * FROM CATEGORIES WHERE Name=? AND TM_USER_DB_ID=?;");
			ps_sel.setString(1, catName);
			ps_sel.setString(2, slNo);
			rs = ps_sel.executeQuery();
			if (rs.next()) {
				cat_ID = rs.getString("id");
			}

			logger.log("model -> updateTask -> inserting into Tasks");

			if (slNo != null) {
				ps_sel = con.prepareStatement("SELECT * FROM TASKS WHERE Categories_id = (?)");

				ps_sel.setString(1, cat_ID);

				rs = ps_sel.executeQuery();

				while (rs.next()) {
					task_id = rs.getString("ID");
					name = rs.getString("Name");
					description = rs.getString("Description");
					startDate = rs.getString("st_date");
					pldDate = rs.getString("End_date");
					priority_ID = rs.getString("Priority_id");
					status_ID = rs.getString("Status_id");

					ps_sel1 = con.prepareStatement("SELECT * FROM TAGS WHERE Task_id = (?);");
					ps_sel1.setString(1, task_id);
					rs1 = ps_sel1.executeQuery();
					if (rs1.next()) {
						tags = rs1.getString("Tags");
					}

					ps_sel1 = con.prepareStatement("SELECT * FROM PRIORITY_MASTER WHERE ID=?");
					ps_sel1.setString(1, priority_ID);
					rs1 = ps_sel1.executeQuery();
					if (rs1.next()) {
						priority = rs1.getString("Priority");
					}

					ps_sel1 = con.prepareStatement("SELECT * FROM STATUS_MASTER WHERE ID=?");
					ps_sel1.setString(1, status_ID);
					rs1 = ps_sel1.executeQuery();
					if (rs1.next()) {
						status = rs1.getString("Status");
					}
					bean = new TaskBean();
					bean.setTaskName(name);
					bean.setDescription(description);
					bean.setCreatedDate(startDate);
					bean.setPlannedEndDate(pldDate);
					bean.setPriority(priority);
					bean.setStatus(status);
					bean.setTags(tags);
					list.add(bean);

				}
			}

			con.commit();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.log("model -> updateTask() -> rs close -> Exception occured -> " + e.getStackTrace());
				}
			}

			if (ps_sel != null) {
				try {
					ps_sel.close();
				} catch (SQLException e) {
					logger.log("model -> updateTask() -> ps_sel close -> Exception occured -> " + e.getStackTrace());
					// e.printStackTrace();
				}
			}

			if (ps_sel1 != null) {
				try {
					ps_sel1.close();
				} catch (SQLException e) {
					logger.log("model -> updateTask() -> ps_sel close -> Exception occured -> " + e.getStackTrace());
					// e.printStackTrace();
				}
			}

			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					logger.log("model -> updateTask() -> ps_sel close -> Exception occured -> " + e.getStackTrace());
					// e.printStackTrace();
				}
			}

		}
		return list;
	}

	@Override
	public List<TaskBean> listTasksBasedOnTaskName(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TaskBean> listTasksBasedOnOverDueTask(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TaskBean> listTasksBasedOnOverDueTask(String catName, String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void searchAllCategory(String wordToBeSearched, String email) {
		// TODO Auto-generated method stub

	}

	@Override
	public void searchAllTaskInCategory(String catName, String wordToBeSearched, String email) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<String> taskList(String catName, String email) {

		logger.log("model -> contactsNameList()");
		List<String> list = new ArrayList<String>();
		JDBCConnectionEstablisher connector = new JDBCConnectionEstablisher();
		Connection con = null;
		PreparedStatement ps_sel = null;
		ResultSet rs = null;
		try {
			logger.log("model -> contactsNameList()");
			con = connector.connect();
			ps_sel = con.prepareStatement(
					"select Task_Name from User_TasksList_view where LOWER(email)=LOWER(?) AND LOWER(Name)=LOWER(?) ;");
			ps_sel.setString(1, email);
			ps_sel.setString(2, catName);
			rs = ps_sel.executeQuery();
			while (rs.next()) {
				list.add(rs.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					logger.log("model -> contactNameList() -> rs close -> Exception occured -> " + e.getStackTrace());
					// e.printStackTrace();
				}
			if (ps_sel != null)
				try {
					ps_sel.close();
				} catch (SQLException e) {
					logger.log(
							"model -> contactNameList() -> ps_sel close -> Exception occured -> " + e.getStackTrace());
					// e.printStackTrace();
				}
			if (con != null)
				try {
					con.close();
				} catch (SQLException e) {
					logger.log("model -> contactNameList() -> con close -> Exception occured -> " + e.getStackTrace());
					// e.printStackTrace();
				}
		}

		return list;
	}

	@Override
	public String removeCat(String catName, String email) {
		logger.log("model -> removeTask()");
		String msg = null;
		boolean b = false, b1 = false;
		JDBCConnectionEstablisher connector = new JDBCConnectionEstablisher();
		logger.log("model -> removeTask() -> connecting db");
		Connection con = connector.connect();
		PreparedStatement ps_sel = null, ps_del = null, ps_del1 = null;
		PreparedStatement ps_del2 = null;
		ResultSet rs = null;
		String slNo = null;
		String cat_ID = null;
		String task_ID = null;
		try {

			ps_sel = con.prepareStatement("SELECT * FROM TM_User_db WHERE Email=?");
			ps_sel.setString(1, email);
			rs = ps_sel.executeQuery();
			if (rs.next()) {
				slNo = rs.getString("id");
			}

			ps_sel.close();
			rs.close();

			ps_sel = con.prepareStatement("SELECT * FROM CATEGORIES WHERE Name=? AND TM_USER_DB_ID=?;");
			ps_sel.setString(1, catName);
			ps_sel.setString(2, slNo);
			rs = ps_sel.executeQuery();
			if (rs.next()) {
				cat_ID = rs.getString("id");
			}

			ps_sel = con.prepareStatement("SELECT * FROM TASKS WHERE Categories_ID=?;");
			ps_sel.setString(1, cat_ID);
			rs = ps_sel.executeQuery();
			while (rs.next()) {
				task_ID = rs.getString("id");
				
				ps_del = con.prepareStatement("DELETE FROM TAGS WHERE Task_id =(?)");
				ps_del.setString(1, task_ID);
				b = ps_del.execute();
				if (!b) {
					ps_del1 = con.prepareStatement("DELETE FROM TASKS WHERE ID =(?)");
					ps_del1.setString(1, task_ID);
				    ps_del1.execute();					
				}

			}
			
			ps_del2 = con.prepareStatement("DELETE FROM CATEGORIES WHERE Name=? AND TM_USER_DB_ID=?;");
			System.out.println("Delete categories");
			ps_del2.setString(1, catName);
			ps_del2.setString(2, slNo);
			b1 = ps_del2.execute();
			if (!b1) {
				msg =  "successfully deleted ";
			} else {
				msg =  "deleting failed";
			}
			
			return msg;

		} catch (Exception e) {
			logger.log("model -> removeCat() -> exception occured");
			System.out.println(e.getStackTrace());
			// e.printStackTrace();
		} finally {
			if (ps_del != null) {
				try {
					ps_del.close();
				} catch (SQLException e) {
					logger.log("model -> removeCat() -> ps_del close -> " + e.getStackTrace());
					// e.printStackTrace();
				}
			}

			if (ps_del1 != null) {
				try {
					ps_del1.close();
				} catch (SQLException e) {
					logger.log("model -> removeCat() -> ps_del1 close -> " + e.getStackTrace());
					// e.printStackTrace();
				}
			}

			if (ps_sel != null) {
				try {
					ps_sel.close();
				} catch (SQLException e) {
					logger.log("model -> removeCat() -> ps_del1 close -> " + e.getStackTrace());
					// e.printStackTrace();
				}
			}

			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					logger.log("model -> removeCat() -> con close -> " + e.getStackTrace());
					// e.printStackTrace();
				}
			}

		}

		return null;
	}

}
