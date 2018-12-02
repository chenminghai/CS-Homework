package service;

import dao.ItemDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "AdminVoteDeleteServlet",urlPatterns = "/admin/vote/delete")
public class AdminVoteDeleteServlet extends HttpServlet {
   
	private static final long serialVersionUID = -3303825622408959076L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id =  Integer.parseInt(request.getParameter("id"));
        if (id > -1){
            ItemDao itemDao = new ItemDao();
            int success = itemDao.deleteItemById(id);
            if (success > 0){
                response.sendRedirect(request.getContextPath() + "/admin");
            }
        }else {
            return;
        }
    }
}
