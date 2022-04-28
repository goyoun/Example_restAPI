package com.example.restapi.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.security.core.userdetails.UserDetails;

@Builder // builder를 사용할 수 있게 됨
@Entity // jpa Entity 임을 알림.
@Getter // user 필드값의 getter를 자동으로 생성
@NoArgsConstructor // 인자 없는 생성자를 생성
@AllArgsConstructor // 인자를 모두 갖춘 생성자를 자동으로 생성
@Table(name = "userq") // user 테이블과 매핑됨을 명시
public class User implements UserDetails {

    @Id // primary key 임을 알림.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // pk생성전략을  DB에 위임한다는 의미 , MySQL 로 보면  PK 필드를 auto_increment로 설정해 놓은 경우로 보면 됨.
    @Column(name="MSRL") // uid Column을 명시합니다. 필수이고 유니크한 필드이며 길이는 30 입니다.
    private long msrl;

    @Column(nullable = false, unique = true, length = 30) // uid Column을 명시합니다. 필수이고 유니크한 필드이며 길이는 30 입니다.
    private String uid;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 100) // name Column을 명시합니다. 필수이고 길이는 100
    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public String getUsername() {
        return this.uid;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonExpired() { // 계정이 만료가 안되었는지 확인
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonLocked() { // 계정이 잠기지 않았는지 확인
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isCredentialsNonExpired() { //계정 패스워드가 만료되었는지 확인
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isEnabled() { //계정이 사용 가능한지 확인
        return true;
    }
    
}

