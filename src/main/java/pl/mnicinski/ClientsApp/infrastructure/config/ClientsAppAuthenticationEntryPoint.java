package pl.mnicinski.ClientsApp.infrastructure.config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ClientsAppAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    @Override public void afterPropertiesSet() throws Exception {
        setRealmName("mnicinski");
        super.afterPropertiesSet();
    }

    @Override public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.addHeader("WWW-Authenticate", "Basic realm=" + getRealmName());
        response.getWriter().println("HTTP Status 403 - " + authException.getMessage());
    }
}
