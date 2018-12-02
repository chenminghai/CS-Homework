package service;

import dao.ItemDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@SuppressWarnings("rawtypes")
@WebServlet({"/index","/index/vote/list","/index/vote/search"})
public class IndexServlet extends HttpServlet {

	private static final long serialVersionUID = 7209195686057464382L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //由于get传递参数要乱码，所以弄成post，这个问题之后在解决
		//System.out.println("搜索投票");
        request.setCharacterEncoding("UTF-8");
        ItemDao itemDao = new ItemDao();
        String path = request.getRequestURI();
        List itemList;
        if (path.indexOf("/search") > -1){
            String content = request.getParameter("content").trim();
            itemList = itemDao.getSearchItemList(content);
            request.setAttribute("itemList",itemList);
            request.setAttribute("active",2);
            request.getRequestDispatcher("/WEB-INF/view/vote.jsp").forward(request,response);
        }
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        request.setCharacterEncoding("UTF-8");
        ItemDao itemDao = new ItemDao();
        String path = request.getRequestURI();
        List itemList;
        if (path.indexOf("vote/list") > -1){
            session.setAttribute("active",2);
            itemList = itemDao.getAllItemList();
            request.setAttribute("itemList",itemList);
            request.getRequestDispatcher("/WEB-INF/view/vote.jsp").forward(request,response);
        }else {
            session.setAttribute("active",1);
            itemList = itemDao.getLimitItemList(5);//获取最新五条
            request.setAttribute("itemList",itemList);
            request.getRequestDispatcher("/WEB-INF/view/index.jsp").forward(request,response);
        }
    }
}
