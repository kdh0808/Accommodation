package com.koreait.bnb.controller;

import com.koreait.bnb.domains.dto.ReservationDTO;
import com.koreait.bnb.domains.enums.UserRole;
import com.koreait.bnb.domains.enums.UserSocial;
import com.koreait.bnb.domains.security.SecurityUser;
import com.koreait.bnb.domains.vo.ReservationVO;
import com.koreait.bnb.domains.vo.UserVO;
import com.koreait.bnb.service.*;
import com.koreait.bnb.service.security.CustomOAuth2LogoutService;
import com.sun.mail.util.MailConnectException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.AuthorizationCodeOAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import java.util.List;

@Log4j2
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired private SMSService smsService;
    @Autowired private UserService userService;
    @Autowired private UserMailService userMailService;
    @Autowired private ReservationService reservationService;
    @Autowired private CustomOAuth2LogoutService oAuth2LogoutService;

    @GetMapping("/login")
    public void get_login(){

    }

    @PostMapping("/login")
    public void post_login(){

    }

    @GetMapping("/logout")
    public String get_logout(
            HttpSession session,
            @AuthenticationPrincipal SecurityUser securityUser
    ){
        log.info("로그아웃하는 유저의 token: " + securityUser.getAccessToken());
        if(securityUser.getUserVO().getSocial().equals(UserSocial.KAKAO)) {
            oAuth2LogoutService.kakao_logout(securityUser.getAccessToken());
        }
        session.invalidate();
        return "redirect:/";
    }

    /***************** 회원가입 *******************/
    @GetMapping("/join")
    public void get_join(){

    }

    // 휴대폰 인증 체크하며 회원가입
    @PostMapping("/join")
    public String post_join(
            HttpSession session,
            @Validated UserVO userVO,
            @RequestParam(defaultValue = "false") boolean verified,
            BindingResult result
    ){
        if(result.hasErrors()){
            log.error("USER_VO의 형식이 맞지 않음");
            log.info(userVO);
            if(!userVO.getSocial().equals(UserSocial.NORMAL)){
                return "redirect:/user/moreInfo";
            }else {
                return "redirect:/user/join";
            }
        }

        Object object = session.getAttribute("verified");
        if(userVO.getSocial().equals(UserSocial.NORMAL) && (!verified || object == null || !(boolean)object)){
            log.error("휴대폰 인증이 안된 사용자!");
            if(!userVO.getSocial().equals(UserSocial.NORMAL)){
                return "redirect:/user/moreInfo";
            }else {
                return "redirect:/user/join";
            }
        }

        userService.join_user(userVO);
        return "redirect:/";
    }

    // 인증번호 요청 시 인증번호를 생성
    @ResponseBody
    @GetMapping("/sms/key")
    public boolean get_verifyKey(
            HttpSession session,
            @RequestParam String phoneNumber
    ){
        session.setAttribute("verified", false);
        // 이미 한번 인증 요청을 해서, 인증번호를 받은 적이 있다면
        if(session.getAttribute("VERIFY_KEY") != null){
            log.warn("이미 VERIFY_KEY가 존재하므로, 기존 코드를 삭제합니다");
            session.removeAttribute("VERIFY_KEY");
        }
        // 새로 VERIFY_KEY를 발급받음
        String VERIFY_KEY = smsService.get_verify_key(phoneNumber);
        // 발급에 실패했다면
        if(VERIFY_KEY == null){
            log.error("VERIFY_KEY가 생성되지 않았음 => SMS 요청 실패!");
            return false;
        }
        // 발급에 성공했다면
        log.warn("VERIFY_KEY가 생성되었음 => " + VERIFY_KEY);
        session.setAttribute("VERIFY_KEY", VERIFY_KEY);
        return true;
    }

    // 사용자가 인증번호를 작성하고 인증 시도
    @ResponseBody
    @GetMapping("/sms/verify")
    public boolean get_verify(
            HttpSession session,
            @RequestParam("key") String userKey
    ){
        Object object = session.getAttribute("VERIFY_KEY");
        session.setAttribute("verified", false);
        // 인증번호를 발급받은적이 없는데 session에서 가져오는것은 에러
        if(object == null){
            log.error("생성되어있는 VERIFY_KEY가 존재하지 않음!");
            return false; //인증 실패!
        }
        String VERIFY_KEY = (String) object;
        log.info("생성되어있는 VERIFY_KEY => " + VERIFY_KEY);
        // 사용자가 입력한 값이 틀렸다면
        if(!VERIFY_KEY.equals(userKey)){
            return false;
        }
        // 사용자가 입력한 값과, 기존 코드가 동일하다면
        log.info("VERIFY_KEY가 일치함! 인증 성공!");
        session.setAttribute("verified", true);
        session.removeAttribute("VERIFY_KEY");
        return true;
    }


    // 소셜 로그인 시 사용자의 추가 정보 작성란
    @GetMapping("/moreInfo")
    public void join_more_info(){

    }




    /******************* 아이디 / 비밀번호********************/
    // 아이디 / 비밀번호 찾기 페이지로 이동하는 매핑
    @GetMapping("/info")
    public void get_info(){

    }

    // 메일 발송하기
    @ResponseBody
    @GetMapping("/find")
    public void send_mail(
            @RequestParam(defaultValue = "")String userId,
            @RequestParam String userEmail
    ){
        try {
            userMailService.find_id(userEmail);
        }catch (MessagingException e){
        }
    }

    /***************** 마이페이지 *******************/
    @GetMapping("/mypage")
    public void get_mypage(){

    }

    // 유저 회원정보 보기/수정 페이지로 이동
    @GetMapping("/mypage/info")
    public void get_mypage_info(){

    }
    // 유저 회원정보 수정하기
    @PostMapping("/mypage/info")
    public String post_mypage_info(
            @AuthenticationPrincipal SecurityUser securityUser, //로그인 된 유저
            UserVO userVO
    ){
        userService.update_user(securityUser, userVO);
        return "redirect:/user/mypage";
    }

    // 유저에 따라 예약 상태 VIEW로 이동 (USER => 예약 상태 목록, SELLER => 예약 요청 목록)
    @GetMapping("/mypage/reservation")
    public String get_mypage_reservation(
            @AuthenticationPrincipal SecurityUser user,
            Model model
    ){
        List<ReservationDTO> reservationDTOS;
        UserRole role = user.getUserVO().getRole();
        switch (role){
            case USER:
                reservationDTOS = reservationService.get_reserve_info_by_user(user.getUserVO());
                break;
            case SELLER:
                reservationDTOS = reservationService.get_reserve_request_info_of_user(user.getUserVO());
                break;
            default:
                return "redirect:/";
        }
        model.addAttribute("reservationDTOS", reservationDTOS);
        return "/user/mypage/reservation";
    }


    @GetMapping("/mypage/sale")
    public void get_mypage_sale(@AuthenticationPrincipal SecurityUser user,
                                Model model){
        List<ReservationDTO> reservationDTOS = reservationService.get_sale_info_by_user(user.getUserVO());
        model.addAttribute("reservationDTOS", reservationDTOS);
    }

    @GetMapping("/mypage/heart")
    public void get_mypage_heart(){

    }
}
