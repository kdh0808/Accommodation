package com.koreait.bnb.config.converter;

import com.koreait.bnb.domains.enums.UserRole;
import com.koreait.bnb.domains.enums.UserSocial;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.convert.converter.Converter;

@Log4j2
public class StringToSocialEnumConverter implements Converter<String, UserSocial> {
    @Override
    public UserSocial convert(String source) {
        try{
            return UserSocial.valueOf(source.toUpperCase());
        }catch (IllegalArgumentException e){
            log.error("UserSocial ENUM 형식이 맞지 않음");
            return null;
        }
    }
}
