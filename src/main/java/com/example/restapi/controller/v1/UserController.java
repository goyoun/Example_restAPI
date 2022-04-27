package com.example.restapi.controller.v1;

import com.example.restapi.advice.exception.CUserNotFoundException;
import com.example.restapi.entity.User;
import com.example.restapi.model.response.CommonResult;
import com.example.restapi.model.response.ListResult;
import com.example.restapi.model.response.SingleResult;
import com.example.restapi.repository.UserJpaRepository;
import com.example.restapi.service.ResponseService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

@Api(tags = {"1. User"}) // UserController를 대표하는 최상단 타이틀 영역에 표시될 값을 세팅합니다.
@RequiredArgsConstructor // 선언시 class 내부에 final로 선언된 객체에 대해서 Constructor Injection을 수행함
                        //  해당 어노테이션을 사용하지 않고 선언된 객체에 @Autowired를 사용해도 된다.
@RestController // 결과값을 JSon으로 출력
@RequestMapping(value="/v1")
public class UserController {

    private final UserJpaRepository userJpaRepository;
    private final ResponseService responseService;

    @ApiOperation(value = "회원 리스트 조회", notes = "모든 회원을 조회한다")
    @GetMapping(value = "/users")
    public ListResult<User> findAllUser() {
        // 결과데이터가 여러건인 경우 getListResult를 이용해서 결과를 출력한다.
        return responseService.getListResult(userJpaRepository.findAll());
    }

    @ApiOperation(value = "회원 단건 조회", notes = "userId로 회원을 조회한다")
    @GetMapping(value = "/user/{userId}")
    public SingleResult<User> findUserById(@ApiParam(value = "회원ID", required = true) @PathVariable long userId,
                                           @ApiParam(value = "언어", defaultValue = "ko")
                                           @RequestParam String lang) {
        // 결과 데이터가 단일건인경우 getBasicResult를 이용해서 결과를 출력한다.
        return responseService.getSingleResult(userJpaRepository.findById(userId).orElseThrow(CUserNotFoundException::new));
    }

    @ApiOperation(value = "회원 입력", notes = "회원을 입력한다")
    @PostMapping(value = "/user")
    public SingleResult<User> save(@ApiParam(value = "회원아이디",required = true) @RequestParam String uid,
                                   @ApiParam(value = "회원 이름", required = true) @RequestParam String name) {
        User user = User.builder()
                .uid(uid)
                .name(name)
                .build();
        return responseService.getSingleResult(userJpaRepository.save(user));
    }

    @ApiOperation(value = "회원 수정", notes = "회원정보를 수정한다")
    @PutMapping(value = "/user")
    public SingleResult<User> modify(
            @ApiParam(value = "회원번호", required = true) @RequestParam long msrl,
            @ApiParam(value = "회원아이디", required = true) @RequestParam String uid,
            @ApiParam(value = "회원이름", required = true) @RequestParam String name) {
        User user = User.builder()
                .msrl(msrl)
                .uid(uid)
                .name(name)
                .build();
        return responseService.getSingleResult(userJpaRepository.save(user));
    }

    @ApiOperation(value = "회원 삭제", notes = "userId로 회원정보를 삭제한다")
    @DeleteMapping(value = "/user/{msrl}")
    public CommonResult delete(
            @ApiParam(value = "회원번호", required = true) @PathVariable Long msrl) {
        userJpaRepository.deleteById(msrl);
        //성공 결과 정보만 필요한 경우 getSuccessResult() 를 이용하여 결과를 출력한다
        return responseService.getSuccessResult();
    }

//    @ApiOperation(value = "회원 조회", notes = "모든 회원을 조회한다") // 각각의 resource에 제목과 설명을 표시하기 위해 세팅합니다.
//    @GetMapping(value = "/userq") // user테이블에 있는 데이터를 모두 읽어온다. 데이터가 한개 이상일 수 있으므로 리턴타입은 List<User>로 선언.
//    public List<User> findAllUser() {
//        return userJpaRepository.findAll(); // Jpa를 사용하면 기본으로 CRUD에 대해서는 별다른 설정 없이 쿼리를 질의할 수 있도록 메소드를 지원합니다.
//                                            // findAll()은 select msrl, name, uid from user; 쿼리를 내부적으로 실행 시켜 줌.
//                                            // insert into user (msrl, name, uid) values (null, ?, ?)
//    }
//
//    @ApiOperation(value = "회원 입력", notes = "회원을 입력한다.")
//    @PostMapping(value = "/userq") // user테이블에 데이터를 1건 입력, userJpaRepository.save(user); 역시 JPA에서 기본으로 제공하는 메소드 입니다.
//                                    // user객체를 전달하면 다음과 같이 내부적으로 insert문을 실행시켜 줍니다.
//                                    // insert into user (msrl, name, uid) values (null, ?, ?)
//                                    // 웹브라우저를 열고 localhost:8080/v1/userq 를 실행합니다. 최초에는 데이터가 없으므로 []만 리턴됩니다.
//                                    // 데이터 입력을 위해 POST로 localhost:8080/v1/userq 를 실행합니다.
//
//                                    // 웹브라우저 입력으로는 GET URL만 호출할 수 있으므로 테스트를 위해 아래 링크의 POST맨
//    public User save(@ApiParam(value = "회원아이디", required = true) @RequestParam String uid,
//                     @ApiParam(value = "회원이름", required = true) @RequestParam String name) {
//                    // @ApiParam~~ @RequestParam~~ : 파라미터에 대한 설명을 보여주기 위해 세팅한다.
//        User user = User.builder()
//                .uid(uid)
//                .name(name)
//                .build();
//        return userJpaRepository.save(user);
//    }
}
