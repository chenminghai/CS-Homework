package service;

import dao.UserDao;
import net.sf.json.JSONObject;
import util.MD5Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;


@WebServlet(name = "AdminUserRegisterServlet",urlPatterns = "/register")
public class AdminUserRegisterServlet extends HttpServlet {

	private static final long serialVersionUID = -7058087102058286747L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String username = request.getParameter("username");
        String realname = request.getParameter("realname");
        String password = request.getParameter("password");
        String telephone = request.getParameter("telephone");
        String sex = request.getParameter("sex");
        String birthday = request.getParameter("birthday");
       
        Map<String, Object> map = new HashMap<String, Object>();
        if(username.isEmpty() || realname.isEmpty() || password.isEmpty()){
            return;
        }
        try {
            password = MD5Util.md5Encode(password);

        } catch (Exception e) {
            e.printStackTrace();
        }
        UserDao userDao = new UserDao();
        int success = userDao.add(username,realname,password,telephone,sex,birthday,1);
        if(success > 0){
            map.put("status", 1);
            request.setAttribute("prompt","注册成功");
        }else {
            map.put("status",0);
        }
        JSONObject jsonObject = JSONObject.fromObject(map);
        PrintWriter out = response.getWriter();
        out.print(jsonObject.toString());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.setAttribute("active",4);
        request.getRequestDispatcher("WEB-INF/view/register.jsp").forward(request,response);
    }
}
