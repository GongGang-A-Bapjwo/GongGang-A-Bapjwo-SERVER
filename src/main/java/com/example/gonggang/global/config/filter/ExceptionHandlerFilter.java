package com.example.gonggang.global.config.filter;

import com.example.gonggang.global.config.error.ErrorCode;
import com.example.gonggang.global.config.error.ErrorResponse;
import com.example.gonggang.global.config.error.exception.ServiceException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.filter.OncePerRequestFilter;

public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (ServiceException e) {
            sendErrorResponse(response, e.getErrorCode());
        } catch (Exception e) {
            e.printStackTrace();
            sendErrorResponse(response, ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    private void sendErrorResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setStatus(errorCode.getHttpStatus());
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        ErrorResponse errorResponse = ErrorResponse.of(errorCode);
        Map<String, ErrorResponse> result = new HashMap<>();
        result.put("result", errorResponse);
        response.getWriter().write(objectMapper.writeValueAsString(result));
    }
}