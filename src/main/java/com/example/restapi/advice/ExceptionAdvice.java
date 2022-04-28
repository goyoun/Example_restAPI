package com.example.restapi.advice;

import javax.servlet.http.HttpServletRequest;

import com.example.restapi.model.response.CommonResult;
import com.example.restapi.service.ResponseService;
import com.example.restapi.advice.exception.*;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@RestControllerAdvice
/*
ControllerAdvice의 어노테이션은 @ControllerAdvice @RestControllerAdvice 두가지가 있습니다. 예외 발생시 json 형태로 결과를 반환하려면
@RestControllerAdvice를 클래스에 선언하면 됩니다. 어노테이션에 추가로 패키지를 적용하면 위에서 설명한 것처럼 특정 패키지 하위 Controller에만 로직이 적용되게도 할 수 있습니다.
ex) @RestControllerAdvice(basePackages= "com.rest.api")
실습에서는 아무것도 적용하지 않아 프로젝트의 모든 Controller에 로직이 적용 됩니다.
*/
public class ExceptionAdvice {

    private final ResponseService responseService;

    private final MessageSource messageSource;
    
    /* 
       @ExceptionHandler
       Exception이 발생하면 해당 Handler로 처리하겠다고 명시하는 어노테이션 
       괄호안에는 어떤 Exception이 발생 할때 handler를 적용할 것인디 Exception Class를 인자로 넣는다.
       예제에서는 Exception.class를 지정 하였는데 Exception.class는 최상위 예외처리 객체이므로 다른 ExceptionHandler에서 걸러지지 않은 예외가 있으면 최종으로 이 handler를 거쳐 처리됩니다.
       그래서 메서드 명도 defaultException이라 명명 했다.
    */
    /*
        @ResponseStatus
        해당 Exception이 발생하면 Response에 출력되는 HttpStatus Code가 500으로 내려가도록 설정한다.
        참고로 성공시엔 HttpStatus code가 200으로 내려간다. 실습에서 HttpStatus Code의 역할은 성공(200)이냐 정도의 의미만 있고 실제 사용하는 성공,실패 여부는 json으로 출력되는 정보를 이용합니다.
    */
    @ExceptionHandler(Exception.class) 
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  
    protected CommonResult defaultException(HttpServletRequest request, Exception e) {
        // 예외처리의 메세지를 MessageSource에서 가져오도록 수정
        return responseService.getFailResult(Integer.valueOf(getMessage("unKnown.code")), getMessage("unKnown.msg"));
        /* Exception 발생시 이미 만들어둔 CommonResult의 실패 결과를 json 형태로 출력하도록 설정함. 위에서 설정한 HttpStatus code외에 추가로 api 성공 실패 여부를 다시 세팅하는 이유는 상황에 따라 다양한 메세지를 전달하기 위해이다
           HttpsStatus Code는 이미 고정된 스펙이기 떄문에 상세한 예외 메시지 전달에 한계가 있다. 커스텀 Exception을 정의하고 해당 Exception이 발생하면 적절한 형태의 오류 메시지를 담은 Json을 결과에 내리도록 처리하는것. */

    }

    @ExceptionHandler(CUserNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult uesrNotFoundException(HttpServletRequest request, CUserNotFoundException e) {
        // 예외처리 메시지를 MessageSource 에서 가져오도록 수정
        return responseService.getFailResult(Integer.valueOf(getMessage("userNotFound.code")), getMessage("userNotFound.msg"));
    }

    // code 정보에 해당하는 메세지를 조회
    private String getMessage(String code) {
        return getMessage(code,null);
    }

    // code 정보, 추가 argument로 현재 locale에 맞는 메세지를 조회합니다.
    private String getMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }

    // 이메일
    @ExceptionHandler(CEmailSigninFailedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult emailSigninFailed(HttpServletRequest request, CEmailSigninFailedException e) {
        return responseService.getFailResult(Integer.valueOf(getMessage("emailSigninFailed.code")), getMessage("emailSigninFailed.msg"));
    }

    

}