package com.example.board.dto;

import com.example.board.domain.entity.MemberEntity;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberDto {
    @NotBlank(message = "아이디는 필수 입력값입니다.")
    private String id;

    @NotBlank(message = "닉네임은 필수 입력값입니다.")
    private String nickname;

    @NotBlank(message = "이메일은 필수 입력값입니다.")
    @Email(message = "올바른 형식을 입력해야 합니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력값이니다.")
    @Pattern(regexp=".{8,20}", message = "비밀번호는 8자 ~ 20자의 비밀번호여야 합니다.")
    private String password;

    private String auth;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public MemberEntity toEntity(){
        return MemberEntity.builder()
                .id(id)
                .nickname(nickname)
                .email(email)
                .password(password)
                .auth(auth)
                .build();
    }

    @Builder
    public MemberDto(String id, String nickname, String email, String password, String auth, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.auth = auth;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
