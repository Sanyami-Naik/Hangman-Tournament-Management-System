package com.coditas.HangMan.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class
ResetDto {
    private String userName;
    private String oldPassword;
    private String newPassword;
}
