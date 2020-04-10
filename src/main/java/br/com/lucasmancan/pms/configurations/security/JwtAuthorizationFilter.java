package br.com.lucasmancan.pms.configurations.security;

import br.com.lucasmancan.pms.configurations.AuthenticationUser;
import io.jsonwebtoken.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthorizationFilter.class);

    private UserDetailsService userDetailsService;

    @Autowired
    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserDetailsService userDetailService) {
        super(authenticationManager);
        this.userDetailsService = userDetailService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, java.io.IOException {

        var authentication = getAuthentication(request);
        String header = request.getHeader(SecurityConstants.TOKEN_HEADER);

        if (StringUtils.isEmpty(header) || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    private AuthenticationUser getAuthentication(HttpServletRequest request) {
        var token = request.getHeader(SecurityConstants.TOKEN_HEADER);

        if (StringUtils.isNotEmpty(token)) {
            try {
                byte[] signingKey = SecurityConstants.JWT_SECRET.getBytes();

                Jws<Claims> parsedToken = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token.replace("Bearer ", ""));

                var username = parsedToken.getBody().getSubject();

                if (StringUtils.isNotEmpty(username)) {
                    return (AuthenticationUser) userDetailsService.loadUserByUsername(username);
                }
            } catch (ExpiredJwtException exception) {
                log.warn("Request to parse expired JWT : {} failed : {}", token, exception.getMessage());
            } catch (UnsupportedJwtException exception) {
                log.warn("Request to parse unsupported JWT : {} failed : {}", token, exception.getMessage());
            } catch (MalformedJwtException exception) {
                log.warn("Request to parse invalid JWT : {} failed : {}", token, exception.getMessage());
            } catch (IllegalArgumentException exception) {
                log.warn("Request to parse empty or null JWT : {} failed : {}", token, exception.getMessage());
            }

        }

        return null;
    }
}
