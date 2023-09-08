package com.example.testlocal.module.user.application.dto.request;

import lombok.Data;

@Data
public class UserAvailabilityRequest {
    String authCode;
    String studentNumber;
}
