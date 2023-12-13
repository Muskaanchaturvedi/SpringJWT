package org.example.Configuration;

import org.bson.types.ObjectId;
import org.example.Services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class JwtFilter extends GenericFilterBean {
    @Autowired
    private TokenService tokenService;

    //here check
    public boolean allowRequestWithoutToken(HttpServletRequest httpServletRequest){
        return httpServletRequest.getRequestURI().equals("/admin/signup") ||
                httpServletRequest.getRequestURI().equals("/admin/login") ;
    }

    @Override
    public void doFilter(ServletRequest req,
                         ServletResponse res,
                         FilterChain filterChain) throws IOException, ServletException {


        HttpServletRequest httpServletrequest=(HttpServletRequest) req;
        HttpServletResponse httpServletResponse=(HttpServletResponse) res;


        String token=httpServletrequest.getHeader("Authorization");

        if("OPTIONS".equalsIgnoreCase(httpServletrequest.getMethod())){
            httpServletResponse.setStatus(HttpServletResponse.SC_OK,"success");
            return;
        }
        //speific apis without token
        if(allowRequestWithoutToken(httpServletrequest)){
            httpServletResponse.setStatus(HttpServletResponse.SC_OK,"success");
            filterChain.doFilter(req,res);
        }
        else{
            ObjectId adminId= new ObjectId((tokenService.getUserIdFromToken(token)));
            httpServletrequest.setAttribute("adminId",adminId);
            filterChain.doFilter(req,res);
        }

    }


}
