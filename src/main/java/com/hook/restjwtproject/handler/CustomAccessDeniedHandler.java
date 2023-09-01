package com.hook.restjwtproject.handler;

import static com.hook.restjwtproject.utils.ResponseUtils.getMapperWithTimeModule;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hook.restjwtproject.dto.BaseResponse;
import com.hook.restjwtproject.dto.ExceptionResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final CustomExceptionHandler customExceptionHandler;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        ResponseEntity<BaseResponse> responseEntity = customExceptionHandler.handleException(accessDeniedException);
        ExceptionResponse exceptionResponse = (ExceptionResponse) responseEntity.getBody();
        response.setContentType(APPLICATION_JSON_VALUE);
        if (exceptionResponse != null) {
            response.setStatus(exceptionResponse.getStatus());
        }
        ObjectMapper mapper = getMapperWithTimeModule();

        response.getWriter().write(mapper.writeValueAsString(exceptionResponse));
    }


}
