package com.gmail.apach.hexagonaltemplate.domain.file.wrapper;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record GetFilesRequestWrapper(
    String fileName,
    LocalDate createdFrom,
    LocalDate createdTo,
    int page,
    int size,
    String[] sort
) {
}
