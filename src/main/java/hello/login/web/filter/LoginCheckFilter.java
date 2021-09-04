package hello.login.web.filter;

import hello.login.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
public class LoginCheckFilter implements Filter {
    // init, destory는 interface에 default메서드임
    private static final String[] whiteList= {"/", "/member/add", "/login", "/logout", "/css/*"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try{
            log.info("인증 체크 filter 시작", requestURI);

            if(isLoginCheckpath(requestURI)){
                log.info("인즈 체크 로직 실행 {}",requestURI);
                HttpSession session = httpRequest.getSession(false);
                if(session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null){
                    log.info("미인증 사용자 요청 {}", requestURI);

                    //로그인으로 redirect
                    httpResponse.sendRedirect("/login?redirectURL=" + requestURI);
                    return;
                }
            }

            chain.doFilter(request, response);
        }catch (Exception e){
            throw e; // log로 찍을 수 있지만, tomcat에게 보내줘 인식시켜주어야 해야 함
        }finally {
            log.info("인증 체크 필터 종료 {}",requestURI);
        }
    }

    /**
     * whiteList의 경우 체크 x
     */
    private boolean isLoginCheckpath(String requestURI){
        return !PatternMatchUtils.simpleMatch(whiteList, requestURI);
    }
}
