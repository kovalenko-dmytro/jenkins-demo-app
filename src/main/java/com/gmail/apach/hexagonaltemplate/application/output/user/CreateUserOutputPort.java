package com.gmail.apach.hexagonaltemplate.application.output.user;

import com.gmail.apach.hexagonaltemplate.domain.user.model.User;

public interface CreateUserOutputPort {

    User createUser(User user);
}
