package com.example.restapi.controller.v1;

import com.example.restapi.entity.User;
import com.example.restapi.repository.UserJpaRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"1. User"}) // UserController를 대표하는 최상단 타이틀 영역에 표시될 값을 세팅합니다.
@RequiredArgsConstructor // 선언시 class 내부에 final로 선언된 객체에 대해서 Constructor Injection을 수행함
                        //  해당 어노테이션을 사용하지 않고 선언된 객체에 @Autowired를 사용해도 된다.
@RestController // 결과값을 JSon으로 출력
@RequestMapping(value="/v1")
public class UserController {

    private final UserJpaRepository userJpaRepository;

    @ApiOperation(value = "회원 조회", notes = "모든 회원을 조회한다") // 각각의 resource에 제목과 설명을 표시하기 위해 세팅합니다.
    @GetMapping(value = "/userq") // user테이블에 있는 데이터를 모두 읽어온다. 데이터가 한개 이상일 수 있으므로 리턴타입은 List<User>로 선언.
    public List<User> findAllUser() {
        return userJpaRepository.findAll(); // Jpa를 사용하면 기본으로 CRUD에 대해서는 별다른 설정 없이 쿼리를 질의할 수 있도록 메소드를 지원합니다.
                                            // findAll()은 select msrl, name, uid from user; 쿼리를 내부적으로 실행 시켜 줌.
                                            // insert into user (msrl, name, uid) values (null, ?, ?)
    }

    @ApiOperation(value = "회원 입력", notes = "회원을 입력한다.")
    @PostMapping(value = "/userq") // user테이블에 데이터를 1건 입력, userJpaRepository.save(user); 역시 JPA에서 기본으로 제공하는 메소드 입니다.
                                    // user객체를 전달하면 다음과 같이 내부적으로 insert문을 실행시켜 줍니다.
                                    // insert into user (msrl, name, uid) values (null, ?, ?)
                                    // 웹브라우저를 열고 localhost:8080/v1/userq 를 실행합니다. 최초에는 데이터가 없으므로 []만 리턴됩니다.
                                    // 데이터 입력을 위해 POST로 localhost:8080/v1/userq 를 실행합니다.

                                    // 웹브라우저 입력으로는 GET URL만 호출할 수 있으므로 테스트를 위해 아래 링크의 POST맨
    public User save(@ApiParam(value = "회원아이디", required = true) @RequestParam String uid,
                     @ApiParam(value = "회원이름", required = true) @RequestParam String name) {
                    // @ApiParam~~ @RequestParam~~ : 파라미터에 대한 설명을 보여주기 위해 세팅한다.
        User user = User.builder()
                .uid(uid)
                .name(name)
                .build();
        return userJpaRepository.save(user);
    }
}
