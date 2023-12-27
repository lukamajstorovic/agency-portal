package com.lmajstorovic.agencyportal.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class UpdatePasswordRequest {
   private UUID userId;
   private String password;
}
