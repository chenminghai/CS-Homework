package service;

import dao.ItemDao;
import entity.Item;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.regex.Pattern;


@WebServlet(name = "AdminVoteUpdateIdServlet",urlPatterns = "/admin/vote/update")
public class AdminVoteUpdateIdServlet extends HttpServlet {
  
	private static final long serialVersionUID = -4173550689612102234L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String theme = request.getParameter("theme").trim();
        String startTime = request.getParameter("start_time").trim();
        String stopTime = request.getParameter("stop_time").trim();
        String type = request.getParameter("type");
        int id = Integer.parseInt(request.getParameter("id"));
        String temp=request.getParameter("number").trim();
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");  
        pattern.matcher(temp).matches();  
        int number;
        if(type =="1" || temp.equals("") || !pattern.matcher(temp).matches()) {
        	number=1;
        }else {
        	number=Integer.parseInt(temp);
        }
        if(theme.isEmpty() || startTime.isEmpty() || stopTime.isEmpty() || type.isEmpty()){
            System.out.println("数据错误，返回重新修改");
            response.sendRedirect(request.getContextPath() + "/admin/vote/update");
        }
        ItemDao itemDao = new ItemDao();
        int success = itemDao.updateItemById(theme,startTime,stopTime,type,number,id);
        if(success > 0){
            response.sendRedirect(request.getContextPath() + "/admin");
        }else {
            System.out.println("修改投票失败");
            request.getRequestDispatcher("/WEB-INF/view/updatevote.jsp").forward(request,response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	System.out.println("新增投票");
    	HttpSession session = request.getSession();
        session.setAttribute("active",5);
        int id = Integer.parseInt(request.getParameter("id"));
        ItemDao itemDao = new ItemDao();
        Item item = itemDao.getItemById(id);
        request.setAttribute("item",item);
        request.getRequestDispatcher("/WEB-INF/view/updatevote.jsp").forward(request,response);
    }
}
