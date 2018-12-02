package service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ItemDao;
import dao.OptionDao;
import entity.Item;
import entity.Option;
import net.sf.json.JSONObject;


@WebServlet(name ="AdminCandidateAddServlet",urlPatterns = "/admin/candidate/add")
public class AdminCandidateAddServlet extends HttpServlet {
  
	private static final long serialVersionUID = -5050754717526811714L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("添加候选人信息");
        HttpSession session = request.getSession();
        session.setAttribute("active",5);
        int id = Integer.parseInt(request.getParameter("id"));
        ItemDao itemDao = new ItemDao();
        OptionDao optionDao = new OptionDao();
        Item item = itemDao.getItemById(id);
        List<Option> optionList = optionDao.getOptionList(item.getId(),item.getAllVoteCount());
        request.setAttribute("item",item);
        request.setAttribute("optionList",optionList);
        request.getRequestDispatcher("/WEB-INF/view/candidate.jsp").forward(request,response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
        String sex = request.getParameter("sex");
        String birthday = request.getParameter("birthday");
        String age = request.getParameter("age");
        String nation = request.getParameter("nation");
        String political = request.getParameter("political");
        String address = request.getParameter("address");
        String telephone = request.getParameter("telephone");
        String recommend = request.getParameter("recommend");
        int id = Integer.parseInt( request.getParameter("option_id") );
        Map<String, Object> map = new HashMap<String, Object>();
        
        OptionDao optionDao = new OptionDao();
        int success = optionDao.updateOptionById(id,sex,birthday,age,nation,political,address,telephone,recommend);
        if(success > 0){
            map.put("status", 1);
        }else {
            map.put("status",0);
        }
        JSONObject jsonObject = JSONObject.fromObject(map);
        PrintWriter out = response.getWriter();
        out.print(jsonObject.toString());
        System.out.println(id+sex+birthday+age+nation+political+address+telephone+recommend);
	}
}
