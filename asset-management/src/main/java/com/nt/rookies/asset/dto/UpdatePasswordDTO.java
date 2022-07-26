package com.nt.rookies.asset.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UpdatePasswordDTO {

    private String code;

    private String oldPassword;

    private String newPassword;
}
