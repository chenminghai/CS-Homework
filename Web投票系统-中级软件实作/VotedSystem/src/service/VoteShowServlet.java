package service;

import dao.ItemDao;
import dao.OptionDao;
import entity.Item;
import entity.Option;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


@WebServlet(name = "VoteShowServlet",urlPatterns = "/vote/show")
public class VoteShowServlet extends HttpServlet {
   
	private static final long serialVersionUID = -8179465660111638216L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.setAttribute("active",2);
        int id = Integer.parseInt(request.getParameter("id"));
        if (id > 0){
            ItemDao itemDao     = new ItemDao();
            OptionDao optionDao = new OptionDao();
            Item item           = itemDao.getItemById(id);
            List<Option> optionList     = optionDao.getOptionList(item.getId(),item.getAllVoteCount());
            request.setAttribute("item",item);
            request.setAttribute("optionList",optionList);
            request.getRequestDispatcher("/WEB-INF/view/showvote.jsp").forward(request,response);
        }else {
            return;
        }
    }
}