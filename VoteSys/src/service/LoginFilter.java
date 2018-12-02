package service;

import entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebFilter(filterName = "LoginFilter",urlPatterns = {"/admin/*"},servletNames = {"IndexVoteIdServlet"})
public class LoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)resp;
        String path = request.getRequestURI();//获取请求路径
        if (path.indexOf("admin/user/login") > -1){
            chain.doFilter(request,response);
        }else {
            HttpSession session = request.getSession();
            User user = (User)session.getAttribute("user");
            if(user == null && path.indexOf("/index/vote")>-1) {
            	request.setAttribute("prompt","您没有登录哦，登陆后才能投票呢");
            	request.setAttribute("page","登录");
            	request.getRequestDispatcher("/WEB-INF/view/prompt.jsp").forward(request,response);
            }
            if (user == null){
                response.sendRedirect(request.getContextPath() + "/admin/user/login");
            }else {
                chain.doFilter(request,response);
            }
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
