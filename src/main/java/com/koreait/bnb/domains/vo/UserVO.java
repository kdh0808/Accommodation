package com.koreait.bnb.domains.vo;

import com.koreait.bnb.domains.enums.UserRole;
import com.koreait.bnb.domains.enums.UserSocial;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@ToString

public class UserVO {
    @NotBlank
    @Length(max = 80)
    private String id;

    @NotBlank
    private String pw;

    @Email
    private String email;

    @NotBlank
    @Length(max = 11)
    private String tel;

    @Length(max = 10)
    private String nickName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth;

    @NotNull
    private UserRole role;

    @NotNull
    private UserSocial social;

}

