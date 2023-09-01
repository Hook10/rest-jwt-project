package com.hook.restjwtproject.config;

import static com.hook.restjwtproject.utils.ResponseUtils.EXPIRED_JWT_EXCEPTION_MESSAGE;
import static com.hook.restjwtproject.utils.ResponseUtils.MALFORMED_JWT_EXCEPTION_MESSAGE;
import static com.hook.restjwtproject.utils.ResponseUtils.SIGNATURE_EXCEPTION_MESSAGE;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import com.hook.restjwtproject.handler.CustomAccessDeniedHandler;
import com.hook.restjwtproject.token.JwtTokenManager;
import com.hook.restjwtproject.utils.ResponseUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtTokenManager jwtTokenManager;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader(AUTHORIZATION);
        if (authorization!= null && authorization.startsWith("Bearer ")) {
            String jwt = authorization.substring(7);
            try {
                String username = jwtTokenManager.getUsername(jwt);
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            jwtTokenManager.getUserRoles(jwt).stream()
                                    .map(SimpleGrantedAuthority::new)
                                    .toList()
                    );
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            } catch (MalformedJwtException exception) {
                createExceptionResponseAndLog(request, response, MALFORMED_JWT_EXCEPTION_MESSAGE, exception);
                return;
            } catch (ExpiredJwtException exception) {
                createExceptionResponseAndLog(request, response, EXPIRED_JWT_EXCEPTION_MESSAGE, exception);
                return;
            } catch (SignatureException exception) {
                createExceptionResponseAndLog(request, response, SIGNATURE_EXCEPTION_MESSAGE, exception);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private void createExceptionResponseAndLog(HttpServletRequest request, HttpServletResponse response, String message, Exception exception) throws IOException {
        log.debug(exception.getMessage());
        customAccessDeniedHandler.handle(request, response, new AccessDeniedException(message));
    }
}
