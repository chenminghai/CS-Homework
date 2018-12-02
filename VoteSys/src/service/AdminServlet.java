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
import java.util.List;


@WebServlet(name = "AdminServlet",urlPatterns = "/admin")
public class AdminServlet extends HttpServlet {
  
	private static final long serialVersionUID = 1148396708394366369L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
    }

    @SuppressWarnings("rawtypes")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.setAttribute("active",5);
        User user = (User)session.getAttribute("user");
        if( !isAdminLogin(user) ) {
        	request.setAttribute("prompt","您不是管理员，无权访问后台哦！");
        	request.setAttribute("page","首页");
        	request.getRequestDispatcher("/WEB-INF/view/prompt.jsp").forward(request,response);
        	return;
        }
        ItemDao itemDao = new ItemDao();
        List itemList = itemDao.getItemList(user.getId());
        request.setAttribute("itemList",itemList);
        request.setAttribute("length",itemList.size());
        request.getRequestDispatcher("/WEB-INF/view/admin.jsp").forward(request,response);
        return;
    }

    private boolean isAdminLogin(User user) {
    	int level=user.getLevel();
    	return level==0 ? true:false;
    }
}
