package com.gmail.apach.hexagonaltemplate.domain.user.service;

import com.gmail.apach.hexagonaltemplate.application.output.user.GetUsersOutputPort;
import com.gmail.apach.hexagonaltemplate.data.AuthoritiesTestData;
import com.gmail.apach.hexagonaltemplate.data.UsersTestData;
import com.gmail.apach.hexagonaltemplate.domain.user.validator.GetUsersPermissionsValidator;
import com.gmail.apach.hexagonaltemplate.domain.user.wrapper.GetUsersRequestWrapper;
import com.gmail.apach.hexagonaltemplate.infrastructure.common.exception.ForbiddenException;
import com.gmail.apach.hexagonaltemplate.infrastructure.common.util.CurrentUserContextUtil;
import com.gmail.apach.hexagonaltemplate.infrastructure.common.wrapper.CurrentUserAuthContext;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetUsersServiceTest {

    @InjectMocks
    private GetUsersService getUsersService;
    @Mock
    private CurrentUserContextUtil currentUserContextUtil;
    @Mock
    private GetUsersPermissionsValidator getUsersPermissionsValidator;
    @Mock
    private GetUsersOutputPort getUsersOutputPort;

    @Test
    void getUsers_success() {
        final var users = new PageImpl<>(List.of(UsersTestData.admin(), UsersTestData.userCreatedByAdmin()));
        final var context = CurrentUserAuthContext.builder()
            .authorities(AuthoritiesTestData.adminAuthorities())
            .build();
        final var wrapper = GetUsersRequestWrapper.builder().build();

        when(currentUserContextUtil.getContext())
            .thenReturn(context);
        doNothing()
            .when(getUsersPermissionsValidator)
            .validate(context);

        when(getUsersOutputPort.getUsers(wrapper, context))
            .thenReturn(users);

        final var actual = getUsersService.getUsers(wrapper);

        assertNotNull(actual);
        assertTrue(CollectionUtils.isNotEmpty(actual.getContent()));
        assertEquals(2, actual.getContent().size());
    }

    @Test
    void getUsers_forbidden() {
        final var context = CurrentUserAuthContext.builder()
            .authorities(AuthoritiesTestData.userAuthorities())
            .build();
        final var wrapper = GetUsersRequestWrapper.builder().build();

        when(currentUserContextUtil.getContext())
            .thenReturn(context);
        doThrow(new ForbiddenException("forbidden"))
            .when(getUsersPermissionsValidator).validate(context);

        assertThrows(ForbiddenException.class, () -> getUsersService.getUsers(wrapper));
    }
}