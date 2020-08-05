package com.uttara.project.lloyd;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ControllerServlet
 */
public class ControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ControllerServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		process(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	protected void process(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Logger logger = Logger.getInstrance();
		MyTaskUtility myTaskUtility = new MyTaskUtility();
		String requri = request.getRequestURI();
		RequestDispatcher rd = null;
		
		/*=====================Initiation and login start===========================================*/
		if (requri.contains("/home.do")) {
			rd = request.getRequestDispatcher("homepage.jsp");
			rd.forward(request, response);
		}

		if (requri.contains("/Register.do")) {
			rd = request.getRequestDispatcher("Register.jsp");
			rd.forward(request, response);
		}
		if (requri.contains("/Login.do")) {
			rd = request.getRequestDispatcher("Login.jsp");
			rd.forward(request, response);
		}

		if (requri.contains("/LoginData.do")) {
			rd = request.getRequestDispatcher("LoginInt.jsp");
			rd.forward(request, response);
		}

		if (requri.contains("/RegData.do")) {
			rd = request.getRequestDispatcher("RegInt.jsp");
			rd.forward(request, response);
		}

		if (requri.contains("/Registration.do")) {
			System.out.println("registration part validation");
			RegBean bean = (RegBean) request.getAttribute("regBean");
			ModelBL model = new ModelBL();
			String msg = model.register(bean);
			if (!msg.contains("SUCCESS")) {
				System.out.println("inside cs -> process() -> /registration.do -> if block of not success");
				rd = request.getRequestDispatcher("Register.jsp");
				request.setAttribute("msg", msg);
				rd.forward(request, response);
			} else {
				msg = model.checkSuccess(bean);
				rd = request.getRequestDispatcher("Register.jsp");
				request.setAttribute("msg", msg);
				rd.forward(request, response);
			}

		}

		if (requri.contains("/LoggedIn.do")) {
			System.out.println("cs loggedin.do");
			LoginBean bean = (LoginBean) request.getAttribute("login");
			HttpSession session = request.getSession();
			session.setAttribute("emailID", bean.getEmail());
			ModelBL model = new ModelBL();
			String msg = model.login(bean);
			if (msg != null) {
				System.out.println("no entry scenario in loggedin.do");
				rd = request.getRequestDispatcher("Login.jsp");
				request.setAttribute("msg", msg);
				rd.forward(request, response);
			} else {
				msg = model.verify(bean);
				if (msg.equals("SUCCESS")) {
//					loginPage....
					String name = model.userName(bean);
					request.setAttribute("name", name);
					rd = request.getRequestDispatcher("LoggedIn.jsp");
					rd.forward(request, response);
					System.out.println("login successful");
				} else {
					System.out.println("wrong password scenario in loggedin.do");
					rd = request.getRequestDispatcher("Login.jsp");
					request.setAttribute("msg", msg);
					rd.forward(request, response);
				}
			}

		}

		if (requri.contains("/Logout.do")) {
			HttpSession session = request.getSession();
			session.invalidate();
			rd = request.getRequestDispatcher("homepage.jsp");
			rd.forward(request, response);
		}

		if (requri.contains("/LoopLoggedIn.do")) {
			String email = null;
			HttpSession session = request.getSession();
			email = (String) session.getAttribute("emailID");
			System.out.println(email);
			if (email != null) {
				rd = request.getRequestDispatcher("LoggedIn.jsp");
				rd.forward(request, response);
				System.out.println(" back to login welcome");
			} else {
				rd = request.getRequestDispatcher("Login.jsp");
				rd.forward(request, response);
			}
		}
		
		/*=====================Initiation and login end===========================================*/
		
		/*=====================Initiation after login start===========================================*/

		if (requri.contains("/setStorage.do")) {
			String email = null;
			String storageMedium = null;
			HttpSession session = request.getSession();
			storageMedium = (String) request.getParameter("storeAt");
			System.out.println("setStorage ->" + storageMedium);
			session.setAttribute("storage", storageMedium);
			email = (String) session.getAttribute("emailID");
			System.out.println(email);
			if (email != null) {
				rd = request.getRequestDispatcher("LoggedIn.jsp");
				rd.forward(request, response);
				System.out.println(" back to login welcome");
			} else {
				rd = request.getRequestDispatcher("Login.jsp");
				rd.forward(request, response);
			}
		}
		
		/*=====================Initiation after login end===========================================*/

		if (requri.contains("/createcat.do")) {
			String email = null;
			String storageMedium = null;
			HttpSession session = request.getSession();
			storageMedium = (String) session.getAttribute("storage");
			email = (String) session.getAttribute("emailID");
			System.out.println("create ->" + storageMedium);
			if (email != null) {
				if (storageMedium != null) {
					rd = request.getRequestDispatcher("createCategory.jsp");
					rd.forward(request, response);
				} else {
					String msg = "please Select a Storage medium";
					request.setAttribute("msg", msg);
					rd = request.getRequestDispatcher("LoggedIn.jsp");
					rd.forward(request, response);
				}

			} else {
				rd = request.getRequestDispatcher("Login.jsp");
				rd.forward(request, response);
			}
		}

		if (requri.contains("/createCategory.do")) {

			String email = null;
			String storageMedium = null;
			String msg = null;
			HttpSession session = request.getSession();
			storageMedium = (String) session.getAttribute("storage");
			email = (String) session.getAttribute("emailID");
			String catName = (String) request.getParameter("catName");
			if (!myTaskUtility.validateName(catName)) {
				msg = "enter a valid Name that starts with alphabet followed by Alphanumeric with out any space";
				request.setAttribute("msg", msg);
				rd = request.getRequestDispatcher("createCategory.jsp");
				rd.forward(request, response);
			} else {
				System.out.println("createCategory() ->" + storageMedium);
				if (email != null) {
					if (storageMedium != null) {
						ModelBL model = new ModelBL();
						msg = model.createCategory(catName, storageMedium, email);
						request.setAttribute("msg", msg);
						rd = request.getRequestDispatcher("createCategory.jsp");
						rd.forward(request, response);
					} else {
						msg = "please Select a Storage medium";
						request.setAttribute("msg", msg);
						rd = request.getRequestDispatcher("LoggedIn.jsp");
						rd.forward(request, response);
					}

				} else {
					rd = request.getRequestDispatcher("Login.jsp");
					rd.forward(request, response);
				}

			}
		}

		if (requri.contains("/updatecat.do")) {
			String email = null;
			String storageMedium = null;
			ModelBL model = new ModelBL();
			HttpSession session = request.getSession();
			storageMedium = (String) session.getAttribute("storage");
			email = (String) session.getAttribute("emailID");
			System.out.println("updatecat ->" + storageMedium);
			if (email != null) {
				if (storageMedium != null) {
					List<String> list = model.categotyList(storageMedium, email);
					request.setAttribute("list", list);
					rd = request.getRequestDispatcher("updateCategory.jsp");
					rd.forward(request, response);
				} else {
					String msg = "please Select a Storage medium";
					request.setAttribute("msg", msg);
					rd = request.getRequestDispatcher("LoggedIn.jsp");
					rd.forward(request, response);
				}

			} else {
				rd = request.getRequestDispatcher("Login.jsp");
				rd.forward(request, response);
			}
		}
		
		if (requri.contains("/removecat.do")) {
			String email = null;
			String storageMedium = null;
			ModelBL model = new ModelBL();
			HttpSession session = request.getSession();
			storageMedium = (String) session.getAttribute("storage");
			email = (String) session.getAttribute("emailID");
			System.out.println("updatecat ->" + storageMedium);
			if (email != null) {
				if (storageMedium != null) {
					List<String> list = model.categotyList(storageMedium, email);
					request.setAttribute("list", list);
					rd = request.getRequestDispatcher("removeCategory.jsp");
					rd.forward(request, response);
				} else {
					String msg = "please Select a Storage medium";
					request.setAttribute("msg", msg);
					rd = request.getRequestDispatcher("LoggedIn.jsp");
					rd.forward(request, response);
				}

			} else {
				rd = request.getRequestDispatcher("Login.jsp");
				rd.forward(request, response);
			}
		}
		
		if (requri.contains("/removedCat.do")) {
			String email = null;
			String storageMedium = null;
			String catName = null;
			ModelBL model = new ModelBL();
			UpdateTaskBean bean = null; 
			String msg = null;
			HttpSession session = request.getSession();
			catName = (String)request.getParameter("catName");
			storageMedium = (String) session.getAttribute("storage");
			email = (String) session.getAttribute("emailID");
			System.out.println("createTaskView ->" + storageMedium);
			if (email != null) {
				if (storageMedium != null) {
					System.out.println("CS -> catName = "+ catName);
					msg = model.removeCategory(catName, email, storageMedium);
					request.setAttribute("msg", msg);
					rd = request.getRequestDispatcher("LoggedIn.jsp");
					rd.forward(request, response);
				} else {
					msg = "please Select a Storage medium";
					request.setAttribute("msg", msg);
					rd = request.getRequestDispatcher("LoggedIn.jsp");
					rd.forward(request, response);
				}

			} else {
				rd = request.getRequestDispatcher("Login.jsp");
				rd.forward(request, response);
			}

		}

		/*=========================SELECTING start====================================================*/

		if (requri.contains("/CTselectCat.do")) {

			String email = null;
			String storageMedium = null;
			ModelBL model = new ModelBL();
			HttpSession session = request.getSession();
			storageMedium = (String) session.getAttribute("storage");
			email = (String) session.getAttribute("emailID");
			System.out.println("updatecat ->" + storageMedium);
			if (email != null) {
				if (storageMedium != null) {
					List<String> list = model.categotyList(storageMedium, email);
					request.setAttribute("list", list);					
					    rd = request.getRequestDispatcher("selectCategory.jsp");
				     	rd.forward(request, response);
				} else {
					String msg = "please Select a Storage medium";
					request.setAttribute("msg", msg);
					rd = request.getRequestDispatcher("LoggedIn.jsp");
					rd.forward(request, response);
				}

			} else {
				rd = request.getRequestDispatcher("Login.jsp");
				rd.forward(request, response);
			}

		}
		
		if (requri.contains("/UTselectCat.do")) {

			String email = null;
			String storageMedium = null;
			ModelBL model = new ModelBL();
			HttpSession session = request.getSession();
			storageMedium = (String) session.getAttribute("storage");
			email = (String) session.getAttribute("emailID");
			System.out.println("updatecat ->" + storageMedium);
			if (email != null) {
				if (storageMedium != null) {
					List<String> list = model.categotyList(storageMedium, email);
					request.setAttribute("list", list);					
					    rd = request.getRequestDispatcher("selectCategoryUT.jsp");
				     	rd.forward(request, response);
				} else {
					String msg = "please Select a Storage medium";
					request.setAttribute("msg", msg);
					rd = request.getRequestDispatcher("LoggedIn.jsp");
					rd.forward(request, response);
				}

			} else {
				rd = request.getRequestDispatcher("Login.jsp");
				rd.forward(request, response);
			}

		}
		
		if (requri.contains("/UTselectTask.do")) {

			String email = null;
			String storageMedium = null;
			String catName = null;
			ModelBL model = new ModelBL();
			HttpSession session = request.getSession();
			storageMedium = (String) session.getAttribute("storage");
			email = (String) session.getAttribute("emailID");
			System.out.println("updatecat ->" + storageMedium);
			if (email != null) {
				if (storageMedium != null) {
					catName = request.getParameter("catName");
					session.setAttribute("catName", catName);
					List<String> list = model.taskList(storageMedium, catName, email);
					request.setAttribute("list", list);					
					    rd = request.getRequestDispatcher("selectTaskUT.jsp");
				     	rd.forward(request, response);
				} else {
					String msg = "please Select a Storage medium";
					request.setAttribute("msg", msg);
					rd = request.getRequestDispatcher("LoggedIn.jsp");
					rd.forward(request, response);
				}

			} else {
				rd = request.getRequestDispatcher("Login.jsp");
				rd.forward(request, response);
			}

		}
		
		if (requri.contains("/RTselectCat.do")) {

			String email = null;
			String storageMedium = null;
			ModelBL model = new ModelBL();
			HttpSession session = request.getSession();
			storageMedium = (String) session.getAttribute("storage");
			email = (String) session.getAttribute("emailID");
			System.out.println("updatecat ->" + storageMedium);
			if (email != null) {
				if (storageMedium != null) {
					List<String> list = model.categotyList(storageMedium, email);
					request.setAttribute("list", list);					
					    rd = request.getRequestDispatcher("selectCategoryRT.jsp");
				     	rd.forward(request, response);
				} else {
					String msg = "please Select a Storage medium";
					request.setAttribute("msg", msg);
					rd = request.getRequestDispatcher("LoggedIn.jsp");
					rd.forward(request, response);
				}

			} else {
				rd = request.getRequestDispatcher("Login.jsp");
				rd.forward(request, response);
			}

		}
		
		if (requri.contains("/RTselectTask.do")) {

			String email = null;
			String storageMedium = null;
			String catName = null;
			ModelBL model = new ModelBL();
			HttpSession session = request.getSession();
			storageMedium = (String) session.getAttribute("storage");
			email = (String) session.getAttribute("emailID");
			System.out.println("updatecat ->" + storageMedium);
			if (email != null) {
				if (storageMedium != null) {
					catName = request.getParameter("catName");
					session.setAttribute("catName", catName);
					List<String> list = model.taskList(storageMedium, catName, email);
					request.setAttribute("list", list);					
					    rd = request.getRequestDispatcher("selectTaskRT.jsp");
				     	rd.forward(request, response);
				} else {
					String msg = "please Select a Storage medium";
					request.setAttribute("msg", msg);
					rd = request.getRequestDispatcher("LoggedIn.jsp");
					rd.forward(request, response);
				}

			} else {
				rd = request.getRequestDispatcher("Login.jsp");
				rd.forward(request, response);
			}

		}
		
		if (requri.contains("/LTselectCat.do")) {

			String email = null;
			String storageMedium = null;
			ModelBL model = new ModelBL();
			HttpSession session = request.getSession();
			storageMedium = (String) session.getAttribute("storage");
			email = (String) session.getAttribute("emailID");
			System.out.println("updatecat ->" + storageMedium);
			if (email != null) {
				if (storageMedium != null) {
					List<String> list = model.categotyList(storageMedium, email);
					request.setAttribute("list", list);					
					    rd = request.getRequestDispatcher("selectCategoryLT.jsp");
				     	rd.forward(request, response);
				} else {
					String msg = "please Select a Storage medium";
					request.setAttribute("msg", msg);
					rd = request.getRequestDispatcher("LoggedIn.jsp");
					rd.forward(request, response);
				}

			} else {
				rd = request.getRequestDispatcher("Login.jsp");
				rd.forward(request, response);
			}

		}
		
		if (requri.contains("/STselectCat.do")) {

			String email = null;
			String storageMedium = null;
			ModelBL model = new ModelBL();
			HttpSession session = request.getSession();
			storageMedium = (String) session.getAttribute("storage");
			email = (String) session.getAttribute("emailID");
			System.out.println("updatecat ->" + storageMedium);
			if (email != null) {
				if (storageMedium != null) {
					List<String> list = model.categotyList(storageMedium, email);
					request.setAttribute("list", list);					
					    rd = request.getRequestDispatcher("selectCategoryST.jsp");
				     	rd.forward(request, response);
				} else {
					String msg = "please Select a Storage medium";
					request.setAttribute("msg", msg);
					rd = request.getRequestDispatcher("LoggedIn.jsp");
					rd.forward(request, response);
				}

			} else {
				rd = request.getRequestDispatcher("Login.jsp");
				rd.forward(request, response);
			}

		}
		
		/*=====================Selecting part end===========================================*/
		
		/*=====================task view start===========================================*/

		if (requri.contains("/createTaskView.do")) {

			String email = null;
			String storageMedium = null;
			HttpSession session = request.getSession();
			String catName = (String)request.getParameter("catName");
			System.out.println(catName);
			session.setAttribute("catName", catName);
			storageMedium = (String) session.getAttribute("storage");
			email = (String) session.getAttribute("emailID");
			System.out.println("createTaskView ->" + storageMedium);
			if (email != null) {
				if (storageMedium != null) {
					rd = request.getRequestDispatcher("createTask.jsp");
					rd.forward(request, response);
				} else {
					String msg = "please Select a Storage medium";
					request.setAttribute("msg", msg);
					rd = request.getRequestDispatcher("LoggedIn.jsp");
					rd.forward(request, response);
				}

			} else {
				rd = request.getRequestDispatcher("Login.jsp");
				rd.forward(request, response);
			}

		}
		
		if (requri.contains("/updateTaskView.do")) {

			String email = null;
			String storageMedium = null;
			UpdateTaskBean bean = null;
			String catName = null;
			ModelBL model = new ModelBL();
			HttpSession session = request.getSession();
			String taskName = (String)request.getParameter("taskName");
			session.setAttribute("taskName", taskName);
			System.out.println("UTV() ->"+taskName);
			catName = (String)session.getAttribute("catName");
			storageMedium = (String) session.getAttribute("storage");
			email = (String) session.getAttribute("emailID");
			System.out.println("createTaskView ->" + storageMedium);
			if (email != null) {
				if (storageMedium != null) {
					bean = model.getTask(catName, email, storageMedium, taskName);
					System.out.println("UPTV -> "+bean);
					session.setAttribute("bean", bean);
					rd = request.getRequestDispatcher("updateTask.jsp");
					rd.forward(request, response);
				} else {
					String msg = "please Select a Storage medium";
					request.setAttribute("msg", msg);
					rd = request.getRequestDispatcher("LoggedIn.jsp");
					rd.forward(request, response);
				}

			} else {
				rd = request.getRequestDispatcher("Login.jsp");
				rd.forward(request, response);
			}

		}
		
		if (requri.contains("/removeTaskView.do")) {

			String email = null;
			String storageMedium = null;
			String catName = null;
			ModelBL model = new ModelBL();
			UpdateTaskBean bean = null; 
			String msg = null;
			HttpSession session = request.getSession();
			String taskName = (String)request.getParameter("taskName");
			System.out.println("RTV() ->"+taskName);
			catName = (String)session.getAttribute("catName");
			storageMedium = (String) session.getAttribute("storage");
			email = (String) session.getAttribute("emailID");
			System.out.println("createTaskView ->" + storageMedium);
			if (email != null) {
				if (storageMedium != null) {
					bean = model.getTask(catName, email, storageMedium, taskName);
					msg = model.removeTask(catName, email, storageMedium, taskName,bean);
					request.setAttribute("msg", msg);
					rd = request.getRequestDispatcher("LoggedIn.jsp");
					rd.forward(request, response);
				} else {
					msg = "please Select a Storage medium";
					request.setAttribute("msg", msg);
					rd = request.getRequestDispatcher("LoggedIn.jsp");
					rd.forward(request, response);
				}

			} else {
				rd = request.getRequestDispatcher("Login.jsp");
				rd.forward(request, response);
			}

		}
		
		if (requri.contains("/listTaskView.do")) {

			String email = null;
			String storageMedium = null;
			HttpSession session = request.getSession();
			String catName = (String)request.getParameter("catName");
			ModelBL model = new ModelBL();
			List<TaskBean> list = new ArrayList<TaskBean>();
			System.out.println(catName);
			session.setAttribute("catName", catName);
			storageMedium = (String) session.getAttribute("storage");
			email = (String) session.getAttribute("emailID");
			System.out.println("createTaskView ->" + storageMedium);
			if (email != null) {
				if (storageMedium != null) {
					list = model.listTasksBasedOnTaskName(catName, email, storageMedium);
					request.setAttribute("list", list);
					rd = request.getRequestDispatcher("listTask.jsp");
					rd.forward(request, response);
				} else {
					String msg = "please Select a Storage medium";
					request.setAttribute("msg", msg);
					rd = request.getRequestDispatcher("LoggedIn.jsp");
					rd.forward(request, response);
				}

			} else {
				rd = request.getRequestDispatcher("Login.jsp");
				rd.forward(request, response);
			}

		}
		
		if (requri.contains("/searchTaskView.do")) {

			String email = null;
			String storageMedium = null;
			HttpSession session = request.getSession();
			String catName = (String)request.getParameter("catName");
			System.out.println(catName);
			session.setAttribute("catName", catName);
			storageMedium = (String) session.getAttribute("storage");
			email = (String) session.getAttribute("emailID");
			System.out.println("createTaskView ->" + storageMedium);
			if (email != null) {
				if (storageMedium != null) {
					rd = request.getRequestDispatcher("searchTask.jsp");
					rd.forward(request, response);
				} else {
					String msg = "please Select a Storage medium";
					request.setAttribute("msg", msg);
					rd = request.getRequestDispatcher("LoggedIn.jsp");
					rd.forward(request, response);
				}

			} else {
				rd = request.getRequestDispatcher("Login.jsp");
				rd.forward(request, response);
			}

		}
		/*=====================task view end===========================================*/

		if (requri.contains("/createTask.do")) {
			String email = null;
			String storageMedium = null;
			String msg = null;
			TaskBean bean = null;
			HttpSession session = request.getSession();
			storageMedium = (String) session.getAttribute("storage");
			email = (String) session.getAttribute("emailID");
			String catName = (String) session.getAttribute("catName");
			System.out.println(catName);
			if (!myTaskUtility.validateName(catName)) {
				msg = "enter a valid Name that starts with alphabet followed by Alphanumeric with out any space";
				request.setAttribute("msg", msg);
				rd = request.getRequestDispatcher("createCategory.jsp");
				rd.forward(request, response);
			} else {
				System.out.println("cs -> updateCategory ->" + storageMedium);
				bean = (TaskBean)request.getAttribute("taskBean");	  
				if (email != null) {
					if (storageMedium != null) {
						ModelBL model = new ModelBL();
						msg = model.addTask(catName, storageMedium, email, bean);//verification needed and should not be null implementation needed 
						request.setAttribute("msg", msg);
						rd = request.getRequestDispatcher("createTask.jsp");
						rd.forward(request, response);
					} else {
						msg = "please Select a Storage medium";
						request.setAttribute("msg", msg);
						rd = request.getRequestDispatcher("LoggedIn.jsp");
						rd.forward(request, response);
					}

				} else {
					rd = request.getRequestDispatcher("Login.jsp");
					rd.forward(request, response);
				}

			}
		}
		
		if (requri.contains("/updateTask.do")) {
			String email = null;
			String storageMedium = null;
			String msg = null;
			TaskBean bean = null;
			UpdateTaskBean uBean = null;
			String task_id = null;
			HttpSession session = request.getSession();
			uBean = (UpdateTaskBean) session.getAttribute("bean");
			 task_id = (String) uBean.getId_no();
			storageMedium = (String) session.getAttribute("storage");
			email = (String) session.getAttribute("emailID");
			String catName = (String) session.getAttribute("catName");
			String taskName = (String) session.getAttribute("taskName");
			System.out.println(catName);
			if (!myTaskUtility.validateName(catName)) {
				msg = "enter a valid Name that starts with alphabet followed by Alphanumeric with out any space";
				request.setAttribute("msg", msg);
				rd = request.getRequestDispatcher("createCategory.jsp");
				rd.forward(request, response);
			} else {
				System.out.println("cs -> updateCategory ->" + storageMedium);
				bean = (TaskBean)request.getAttribute("taskBean");	
				if (email != null) {
					if (storageMedium != null) {
						ModelBL model = new ModelBL();
						msg = model.updateTaskData(catName, taskName, email, storageMedium, bean, task_id);//verification needed and should not be null implementation needed 
						request.setAttribute("msg", msg);
						rd = request.getRequestDispatcher("LoggedIn.jsp");
						rd.forward(request, response);
					} else {
						msg = "please Select a Storage medium";
						request.setAttribute("msg", msg);
						rd = request.getRequestDispatcher("LoggedIn.jsp");
						rd.forward(request, response);
					}

				} else {
					rd = request.getRequestDispatcher("Login.jsp");
					rd.forward(request, response);
				}

			}
		}

	}
}
