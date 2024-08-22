package com.gmail.apach.jenkins_demo.domain.user.validator;

import com.gmail.apach.jenkins_demo.common.constant.message.Error;
import com.gmail.apach.jenkins_demo.common.dto.CurrentUserContext;
import com.gmail.apach.jenkins_demo.common.exception.ForbiddenException;
import com.gmail.apach.jenkins_demo.common.util.CurrentUserContextUtil;
import com.gmail.apach.jenkins_demo.domain.common.constant.RoleType;
import com.gmail.apach.jenkins_demo.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetUserByIdPermissionsValidator {

    private final CurrentUserContextUtil currentUserContextUtil;
    private final MessageSource messageSource;

    public void validate(User user) {
        CurrentUserContext currentUserContext = currentUserContextUtil.getContext();

        final var isAdmin = currentUserContext.isAdmin();

        final var managerTriesToGetAnyExceptAdmin =
            currentUserContext.isManager()
                && user.getRoles().stream().noneMatch(role -> role.getRole().equals(RoleType.ADMIN));

        final var userTriesToGetSelf =
            currentUserContext.isUser()
                && user.getUsername().contentEquals(currentUserContext.username());

        if (!(isAdmin || managerTriesToGetAnyExceptAdmin || userTriesToGetSelf)) {
            throw new ForbiddenException(messageSource.getMessage(
                Error.FORBIDDEN_USER_GET_BY_ID.getKey(),
                new Object[]{user.getUserId()},
                LocaleContextHolder.getLocale()));
        }
    }
}
