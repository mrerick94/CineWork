package filter;

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

@WebFilter(urlPatterns = { "*.xhtml" })
public class LoginFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession session = req.getSession(false);
		String requestURI = req.getRequestURI();
		if (requestURI.contains("/login.xhtml") && (session != null && session.getAttribute("empresaLogada") != null)) {
			String novaURI = req.getContextPath() + "/app/listafuncionarios.xhtml";
			resp.sendRedirect(novaURI);
			return;
		}
		
		if (requestURI.contains("/login.xhtml")
				|| !requestURI.contains("/app/")
				|| (session != null && session.getAttribute("empresaLogada") != null)) {
			
			chain.doFilter(request, response);
		} else {
			String novaURI = req.getContextPath() + "/login.xhtml";
			resp.sendRedirect(novaURI);
		}
	}

	@Override
	public void destroy() {
	}

}
