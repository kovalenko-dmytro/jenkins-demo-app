package com.gmail.apach.jenkins_demo.application.input.user;

import com.gmail.apach.jenkins_demo.domain.user.model.User;

public interface UpdateUserInputPort {

    User update(User user);
}
