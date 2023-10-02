package com.lmajstorovic.agencyportal.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateRankRequest {
    private String username;
    private String rank;
}
