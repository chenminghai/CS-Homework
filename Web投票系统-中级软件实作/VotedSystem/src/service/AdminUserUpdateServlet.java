package service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDao;
import entity.User;
import net.sf.json.JSONObject;
import util.MD5Util;

@WebServlet(name = "AdminUserUpdateServlet",urlPatterns = "/admin/user/update")
public class AdminUserUpdateServlet extends HttpServlet {
  
	private static final long serialVersionUID = 536902875002400680L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
        session.setAttribute("active",6);
        request.getRequestDispatcher("/WEB-INF/view/user.jsp").forward(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("updata user post");
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		request.setCharacterEncoding("UTF-8");
        String username = user.getUsername();
        String realname = request.getParameter("realname");
        String telephone = request.getParameter("telephone");
        String sex = request.getParameter("sex");
        String birthday = request.getParameter("birthday");
        String oldpassword = request.getParameter("oldpassword");
        String newpassword = request.getParameter("newpassword");
       
        Map<String, Object> map = new HashMap<String, Object>();
        if(username.isEmpty() || realname.isEmpty() || oldpassword.isEmpty() || newpassword.isEmpty()){
            return;
        }
        try {
        	oldpassword = MD5Util.md5Encode(oldpassword);
        	newpassword = MD5Util.md5Encode(newpassword);

        } catch (Exception e) {
            e.printStackTrace();
        }
        UserDao userDao = new UserDao();
        user = userDao.get(username, oldpassword);
        if(user==null) {
        	map.put("status", 2);
        }
        else {
        	int success = userDao.update(realname,newpassword,telephone,sex,birthday,user.getId());
            if(success > 0){
            	user = userDao.get(username,newpassword);
            	session.setAttribute("user",user);
                map.put("status",1);
            }else {
                map.put("status",0);
            }
        }
        JSONObject jsonObject = JSONObject.fromObject(map);
        PrintWriter out = response.getWriter();
        out.print(jsonObject.toString());
        System.out.println(map);
	}

}
