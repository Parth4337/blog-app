package com.example.blog.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.micrometer.observation.GlobalObservationConvention;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    private Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestToken = request.getHeader("Authorization");
        String userName = null;
        String token = null;
        logger.trace("Request: {}", request);
        logger.info(" Header: {}", requestToken);

        if(requestToken!=null && requestToken.startsWith("Bearer")){
            token = requestToken.substring(7);
            try {
                userName = jwtTokenHelper.getUserNameFromToken(token);
            } catch (IllegalArgumentException e) {
                logger.info("Unable to get JWT. " + e.getMessage());
                e.printStackTrace();
            } catch (ExpiredJwtException e){
                logger.info("JWT has expired. " + e.getMessage());
                e.printStackTrace();
            } catch (MalformedJwtException e){
                logger.info("Invalid JWT. " + e.getMessage());
                e.printStackTrace();
            } catch (Exception e) {
                logger.info("Unknown Exception Occurred" + e.getMessage());
                e.printStackTrace();
            }

        } else {
            logger.info("Invalid jwst token: " + requestToken);
        }

        if(userName!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

            if(jwtTokenHelper.validateToken(token, userDetails)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
            else {
                logger.info("Invalid JWT");
            }
        } else {
            logger.info("Username is null or context is not null");
        }
        filterChain.doFilter(request,response);
    }
}
