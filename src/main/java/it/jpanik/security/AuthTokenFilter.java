package it.jpanik.security;

import it.jpanik.exceptions.ValidationException;
import it.jpanik.repositories.UserRepository;
import it.jpanik.services.servicesImp.UserDetailsServiceImp;
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
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserDetailsServiceImp userDetailsService;
    @Autowired
    private UserRepository userRepository;

    // FIXME Le variabile statiche da best practice si fanno maiuscole
    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

   @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
       String requestURI = request.getRequestURI();
       logger.debug("Request URI: {}", requestURI);

       try {
           String jwt = parseJwt(request);
           if(jwt!=null && jwtUtils.validateJwtToken(jwt)) {
               String username = jwtUtils.getUserNameFromJwtToken(jwt);

               if(this.userRepository.findByUsername(username).isPresent())    {
                   UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                   UsernamePasswordAuthenticationToken authentication =
                           new UsernamePasswordAuthenticationToken(
                                   userDetails,
                                   null,
                                   userDetails.getAuthorities()
                           );
                   authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                   SecurityContextHolder.getContext().setAuthentication(authentication);
               }
                else {
                    throw new ValidationException("User doesn't exists");
               }
           }

       } catch(Exception e) {
           logger.error("Cannot see authentication: {}", e);
       }
       logger.debug("Passing to next filter: {}", requestURI);
       filterChain.doFilter(request,response);
    }

    private String parseJwt(HttpServletRequest request) {
       String headerAuth = request.getHeader("Authorization");

       if(StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer "))  {
           return headerAuth.substring(7);
       }

       return null;
    }
}
