package kr.or.ddit.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Filter2 implements Filter {

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// 해당 필터의 최초 인스턴시 1회에 한해서 콜백되는 메서드
		// 해당 필터에서 활용될 자원의 취득용
		
	}
	
	@Override
	public void destroy() {
		// 해당 필터의 서버의 종료 또는 필터의 인스턴스가 GC(가비지컬렉터)가 되기 직전에 콜백되는 콜백메서드
		// 해당 필터에서 취득한 자원의 반납용
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain chain) throws IOException, ServletException {
			// FilterChain 무슨 역할이라고?
		
//		HttpServletRequest request = (HttpServletRequest) servletRequest;
//		HttpSession session = request.getSession();
//		HttpServletResponse response = (HttpServletResponse) servletResponse;
		// 필요 없음 왜냐하면 필터 2 이기 때문에 여기서 처리해야하는 것임
		
		// 전처리 코드 : 다른 필터 또는 jsp의 요청 처리 전에 실행되어야하는 코드 구성
		System.out.println("Filter2의 doFilter() 내 전처리 코드");
		
		// 전처리와 후처리를 경계
		chain.doFilter(servletRequest, servletResponse);
		
		// 후처리 코드 : jsp에 의해 작성된 응답 컨텐츠의 클라이언트(브라우저)대상 전송 시작전 실행되어야 하는 코드 구성
		System.out.println("Filter2의 doFilter() 내 후처리 코드");
		
	}


}
