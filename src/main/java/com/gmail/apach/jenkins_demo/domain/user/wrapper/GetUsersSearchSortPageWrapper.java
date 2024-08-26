package com.gmail.apach.jenkins_demo.domain.user.wrapper;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record GetUsersSearchSortPageWrapper(
    String username,
    String firstName,
    String lastName,
    String email,
    Boolean enabled,
    LocalDate createdFrom,
    LocalDate createdTo,
    String createdBy,
    int page,
    int size,
    String[] sort
) {
}
