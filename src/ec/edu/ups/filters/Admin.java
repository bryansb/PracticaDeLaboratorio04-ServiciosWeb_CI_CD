package ec.edu.ups.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ec.edu.ups.entities.User;

/**
 * Servlet Filter implementation class Admin
 */
@WebFilter("/XHTML/private/admin/*")
public class Admin implements Filter {

    /**
     * Default constructor. 
     */
    public Admin() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		boolean sesion;
		HttpServletRequest httpReq = (HttpServletRequest) request;
		HttpServletResponse httpResp = (HttpServletResponse) response;
		HttpSession session =  httpReq.getSession(false);

		try {
			User user = (User)session.getAttribute("user");
			
			sesion = session == null ? false : (Boolean) session.getAttribute("isLogged");
			
			if (sesion) {
				if (user.getRole() == 'A') {
					chain.doFilter(request, response);
				}else {
					session.setAttribute("user", null);
					session.invalidate();
					httpResp.sendRedirect("/Practica03/index.jsf");
				}
			}else {
				session.setAttribute("user", null);
				session.invalidate();
				httpResp.sendRedirect("/Practica03/index.jsf");
			}
			
		} catch (Exception e) {
			httpResp.sendRedirect("/Practica03/index.jsf");
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
