package com.example.restapi.controller.v1;

import com.example.restapi.advice.exception.CEmailSigninFailedException;
import com.example.restapi.config.security.JwtTokenProvider;
import com.example.restapi.entity.User;
import com.example.restapi.model.response.CommonResult;
import com.example.restapi.model.response.SingleResult;
import com.example.restapi.repository.UserJpaRepository;
import com.example.restapi.service.ResponseService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;


@Api(tags = {"1. Sign"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class SignController {

    private final UserJpaRepository userJpaRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final ResponseService responseService;
    private final PasswordEncoder passwordEncoder;

    @ApiOperation(value = "로그인", notes = "이메일 회원 로그인을 한다.")
    @PostMapping(value="signin")
    public SingleResult<String> signin (@ApiParam(value = "회원ID : 이메일", required = true) @RequestParam String id,
                                        @ApiParam(value = "비밀번호", required = true) @RequestParam String password) {
                    User user = userJpaRepository.findByUid(id).orElseThrow(CEmailSigninFailedException::new);
                    if (!passwordEncoder.matches(password, user.getPassword()))
                        throw new CEmailSigninFailedException();
                    
                        return responseService.getSingleResult(jwtTokenProvider.createToken(String.valueOf(user.getMsrl()),user.getRoles()));
                }

    @ApiOperation(value = "가입", notes = "회원가입을 한다.")
    @PostMapping(value = "/signup")
    public CommonResult signin (@ApiParam(value = "회원ID : 이메일", required = true) @RequestParam String id,
                                @ApiParam(value = "비밀번호", required = true) @RequestParam String password,
                                @ApiParam(value = "이름", required = true) @RequestParam String name) {

                userJpaRepository.save(User.builder()
                                 .uid(id)
                                 .password(passwordEncoder.encode(password))
                                 .name(name)
                                 .roles(Collections.singletonList("ROLE_USER"))
                                 .build());
                        return responseService.getSuccessResult();
}


    }
    
    
    
