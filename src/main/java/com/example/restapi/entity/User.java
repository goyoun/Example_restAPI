package com.example.restapi.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder // builder를 사용할 수 있게 됨
@Entity // jpa Entity 임을 알림.
@Getter // user 필드값의 getter를 자동으로 생성
@NoArgsConstructor // 인자 없는 생성자를 생성
@AllArgsConstructor // 인자를 모두 갖춘 생성자를 자동으로 생성
@Table(name = "userq") // user 테이블과 매핑됨을 명시
public class User {

    @Id // primary? key 임을 알림.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // pk생성전략을  DB에 위임한다는 의미 , MySQL 로 보면  PK 필드를 auto_increment로 설정해 놓은 경우로 보면 됨.
    @Column(name="MSRL") // uid Column을 명시합니다. 필수이고 유니크한 필드이며 길이는 30 입니다.
    private long msrl;

    @Column(nullable = false, unique = true, length = 30) // uid Column을 명시합니다. 필수이고 유니크한 필드이며 길이는 30 입니다.
    private String uid;

    @Column(nullable = false, length = 100) // name Column을 명시합니다. 필수이고 길이는 100
    private String name;
}
