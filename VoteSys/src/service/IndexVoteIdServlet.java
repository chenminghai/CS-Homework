package service;

import dao.ItemDao;
import dao.OptionDao;
import dao.ResultDao;
import entity.Item;
import entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


@WebServlet(name = "IndexVoteIdServlet",urlPatterns = "/index/vote")
public class IndexVoteIdServlet extends HttpServlet {
    
	private static final long serialVersionUID = -6818273018879095397L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int id = Integer.parseInt(request.getParameter("id"));
        String[] selectIds = request.getParameterValues("answer");
        String ip = this.getRemortIP(request);
        ResultDao resultDao = new ResultDao();
        User user = (User)session.getAttribute("user");
        int success = resultDao.add(user.getId(),id,selectIds,ip);
        if (success > 0){
            session.setAttribute("msg","投票成功");
            response.sendRedirect(request.getContextPath() + "/index/vote/list");
        }else{
            session.setAttribute("msg","投票失败，请不要用同一ip投票");
            response.sendRedirect(request.getContextPath() + "/index/vote?id=" + id);
        }
    }

    @SuppressWarnings("rawtypes")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.setAttribute("active",2);
        int id = Integer.parseInt(request.getParameter("id"));
        ItemDao itemDao = new ItemDao();
        OptionDao optionDao = new OptionDao();
        Item item = itemDao.getItemById(id);
        if (itemDao.getVoteStatus(item.getStartTime(),item.getStopTime()) != 2){
            //判断是否可以投票，即投票是否结束，防攻击
            response.sendRedirect(request.getContextPath() + "/index/vote/list");
            return;
        }
        List optionList = optionDao.getOptionList(item.getId(),item.getAllVoteCount());
        request.setAttribute("item",item);
        request.setAttribute("optionList",optionList);
        String isWaiver="不可以弃权";
        String isOppose="不可以投反对票";
        System.out.println(item.getIsWaiver());
        System.out.println(item.getIsOppose());
        if( "1".equals(item.getIsWaiver()) ){
        	isWaiver="可以弃权";
        }
        if( "1".equals(item.getIsOppose()) ){
        	isOppose="可以投反对票";
        }
        if( "1".equals(item.getType()) ) {
        	request.setAttribute("type","(单选，"+isWaiver+"，"+isOppose+")");
        }else if( "2".equals(item.getType()) ){
        	request.setAttribute("type","(多选，可选人数:"+item.getNumber()+"，"+isWaiver+"，"+isOppose+")");
        }else {
        	request.setAttribute("type","(评分制，可选人数:"+item.getNumber()+"，"+isWaiver+"，"+isOppose+")");
        }
        request.getRequestDispatcher("/WEB-INF/view/info.jsp").forward(request,response);
    }

    /**
     * 获取用户的ip，防止多次投票
     * @param request
     * @return
     */
    private String getRemortIP(HttpServletRequest request) {
        if (request.getHeader("x-forwarded-for") == null) {
            return request.getRemoteAddr();
        }
        return request.getHeader("x-forwarded-for");
    }
}
