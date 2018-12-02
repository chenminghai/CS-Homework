package service;

import dao.ItemDao;
import entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.regex.Pattern;


@WebServlet(name = "AdminVoteAddServlet",urlPatterns = "/admin/vote/add")
public class AdminVoteAddServlet extends HttpServlet {
    
	private static final long serialVersionUID = 6907426301886770309L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        request.setCharacterEncoding("UTF-8");
        String theme = request.getParameter("theme").trim();
        String startTime = request.getParameter("start_time").trim();
        String stopTime = request.getParameter("stop_time").trim();
        String optionContent = request.getParameter("option_content").trim();
        String type = request.getParameter("type");
        String isWaiver = request.getParameter("is_waiver");
        String isOppose = request.getParameter("is_oppose");
        String temp=request.getParameter("number").trim();
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");  
        pattern.matcher(temp).matches();  
        int number;
        if(type =="1" || temp.equals("") || !pattern.matcher(temp).matches()) {
        	number=1;
        }else {
        	number=Integer.parseInt(temp);
        }
        User user = (User)session.getAttribute("user");
        int userId = user.getId();
        if(theme.isEmpty() || startTime.isEmpty() || stopTime.isEmpty() || optionContent.isEmpty() || type.isEmpty()){
            System.out.println("数据错误，返回重新添加");
            response.sendRedirect(request.getContextPath() + "/admin/vote/add");
        }
        ItemDao itemDao = new ItemDao();
        int item_id = itemDao.add(theme,startTime,stopTime,optionContent,type,userId,isWaiver,isOppose,number);
        if(item_id > 0){
        	response.sendRedirect(request.getContextPath() + "/admin/candidate/add?id=" + item_id);
        }else {
            System.out.println("添加投票失败");
            request.getRequestDispatcher("/WEB-INF/view/addvote.jsp").forward(request,response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.setAttribute("active",5);
        request.getRequestDispatcher("/WEB-INF/view/addvote.jsp").forward(request,response);
    }
}
