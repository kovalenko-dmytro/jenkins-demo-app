package com.gmail.apach.hexagonaltemplate.application.output.email;

import com.gmail.apach.hexagonaltemplate.infrastructure.output.smpt.dto.SendEmailWrapper;

public interface SendEmailOutputPort {

    void sendEmail(SendEmailWrapper sendEmailWrapper);
}
