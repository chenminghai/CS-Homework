package service;

import dao.UserDao;
import entity.User;
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


@WebServlet(name = "AdminUserLoginServlet",urlPatterns = {"/admin/user/login","/admin/user/logout"})
public class AdminUserLoginServlet extends HttpServlet {

	private static final long serialVersionUID = -1667283209552015811L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        request.setCharacterEncoding("UTF-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String code = request.getParameter("code");
        String realcode=(String) session.getAttribute("rand");
        Map<String, Object> map = new HashMap<String, Object>();
        if(username.isEmpty() || password.isEmpty() || code.isEmpty()){
            return;
        }
        try {
            password = MD5Util.md5Encode(password);

        } catch (Exception e) {
            e.printStackTrace();
        }
        UserDao userDao = new UserDao();
        User user = userDao.get(username,password);
        if(user != null && realcode.equals(code)){
            session.setAttribute("user",user);
            map.put("status",1);
        }else {
            map.put("status",0);
        }
        JSONObject jsonObject = JSONObject.fromObject(map);
        PrintWriter out = response.getWriter();
        out.print(jsonObject.toString());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.setAttribute("active",3);
        String path = request.getRequestURI();
        if (path.indexOf("logout") > -1){
            session.removeAttribute("user");
            response.sendRedirect(request.getContextPath() + "/admin/user/login");
            return;
        }
        request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request,response);
    }
}
