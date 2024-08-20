package com.gmail.apach.jenkins_demo.domain.user.validator;

import com.gmail.apach.jenkins_demo.common.exception.ForbiddenException;
import com.gmail.apach.jenkins_demo.data.AuthoritiesTestData;
import com.gmail.apach.jenkins_demo.data.RolesTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateUserPermissionsValidatorTest {

    @InjectMocks
    private CreateUserPermissionsValidator createUserPermissionsValidator;
    @Mock
    private MessageSource messageSource;

    @Test
    void validateAdminCreatesUserOrManager_success() {
        final var authentication = mock(AbstractAuthenticationToken.class);
        final var securityContext = mock(SecurityContext.class);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getAuthorities()).thenReturn(AuthoritiesTestData.adminAuthorities());

        assertDoesNotThrow(() -> createUserPermissionsValidator.validate(RolesTestData.userRoles()));
        assertDoesNotThrow(() -> createUserPermissionsValidator.validate(RolesTestData.managerRoles()));

        Mockito.reset(authentication, securityContext);
    }

    @Test
    void validateManagerCreatesUser_success() {
        final var authentication = mock(AbstractAuthenticationToken.class);
        final var securityContext = mock(SecurityContext.class);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getAuthorities()).thenReturn(AuthoritiesTestData.managerAuthorities());

        assertDoesNotThrow(() -> createUserPermissionsValidator.validate(RolesTestData.userRoles()));

        Mockito.reset(authentication, securityContext);
    }

    @Test
    void validateManagerCreatesManager_fail() {
        final var authentication = mock(AbstractAuthenticationToken.class);
        final var securityContext = mock(SecurityContext.class);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getAuthorities()).thenReturn(AuthoritiesTestData.managerAuthorities());

        assertThrows(ForbiddenException.class,
            () -> createUserPermissionsValidator.validate(RolesTestData.managerRoles()));

        Mockito.reset(authentication, securityContext);
    }

    @Test
    void validateUserCreatesAny_fail() {
        final var authentication = mock(AbstractAuthenticationToken.class);
        final var securityContext = mock(SecurityContext.class);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getAuthorities()).thenReturn(AuthoritiesTestData.userAuthorities());

        assertThrows(ForbiddenException.class,
            () -> createUserPermissionsValidator.validate(RolesTestData.userRoles()));
        assertThrows(ForbiddenException.class,
            () -> createUserPermissionsValidator.validate(RolesTestData.managerRoles()));
        assertThrows(ForbiddenException.class,
            () -> createUserPermissionsValidator.validate(RolesTestData.adminRoles()));

        Mockito.reset(authentication, securityContext);
    }
}