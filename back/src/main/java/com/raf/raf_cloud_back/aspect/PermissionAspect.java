package com.raf.raf_cloud_back.aspect;

import com.raf.raf_cloud_back.exception.ForbiddenException;
import com.raf.raf_cloud_back.exception.UnauthorizedException;
import com.raf.raf_cloud_back.security.JwtUtil;
import com.raf.raf_cloud_back.security.RequiresPermission;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

@Aspect
@Component
public class PermissionAspect {


    private final JwtUtil jwtUtil;

    public PermissionAspect(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Before("@annotation(requiresPermission)")
    public void checkPermission(RequiresPermission requiresPermission) {
        String requiredPermission = requiresPermission.value();

        // Dobavi HTTP request
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attributes == null) {
            throw new UnauthorizedException("Zahtev nije validan");
        }

        HttpServletRequest request = attributes.getRequest();
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new UnauthorizedException("Token nije pronadjen");
        }

        String token = authHeader.substring(7);

        if (!jwtUtil.validateToken(token)) {
            throw new UnauthorizedException("Token nije validan");
        }

        List<String> userPermissions = jwtUtil.extractPermissions(token);

        if (!userPermissions.contains(requiredPermission)) {
            throw new ForbiddenException("Nemate dozvolu za ovu operaciju");
        }

        // Sacuvaj userId u request attribute za dalju upotrebu
        Long userId = jwtUtil.extractUserId(token);
        request.setAttribute("userId", userId);
        request.setAttribute("permissions", userPermissions);
    }
}
