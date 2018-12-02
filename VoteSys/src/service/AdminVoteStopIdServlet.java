package service;

import dao.ItemDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "AdminVoteStopIdServlet",urlPatterns = {"/admin/vote/stop","/admin/vote/start"})
public class AdminVoteStopIdServlet extends HttpServlet {
    
	private static final long serialVersionUID = -1864397850201181544L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String path = request.getRequestURI();
        String status;
        if (path.indexOf("stop") > -1){
            status = "1";
        }else if (path.indexOf("start") > -1){
            status = "2";
        }else {
            return;
        }
        ItemDao itemDao = new ItemDao();
        int success = itemDao.statusItemById(id,status);
        if (success > 0){
            response.sendRedirect(request.getContextPath() + "/admin");
        }else {
            System.out.println("停用失败");
        }
    }
}
