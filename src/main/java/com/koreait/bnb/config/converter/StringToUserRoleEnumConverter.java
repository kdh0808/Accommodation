package com.koreait.bnb.config.converter;

import com.koreait.bnb.domains.enums.UserRole;
import com.koreait.bnb.domains.enums.UserSocial;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.convert.converter.Converter;

@Log4j2
public class StringToUserRoleEnumConverter implements Converter<String, UserRole>{
    @Override
    public UserRole convert(String source) {
        try {
            return UserRole.valueOf(source.toUpperCase());
        }catch (IllegalArgumentException e){
            log.error("USER_ROLE ENUM 형식이 맞지 않음");
            return null;
        }
    }
}
