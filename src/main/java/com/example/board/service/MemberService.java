package com.example.board.service;

import com.example.board.domain.Role;
import com.example.board.domain.entity.MemberEntity;
import com.example.board.domain.repository.MemberRepository;
import com.example.board.dto.MemberDto;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import javax.transaction.Transactional;
import java.util.*;

@AllArgsConstructor
@Service
public class MemberService implements UserDetailsService {
    private MemberRepository memberRepository;

    @Transactional
    public String joinUser(MemberDto memberDto){
        //비밀번호 암호화
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //DTO저장 이후 인코딩이 이루어짐
        //즉 DTO상태에서 Valid적용시 입력한 비밀번호 검사
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));

        return memberRepository.save(memberDto.toEntity()).getId();
    }

    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();

        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }

        return validatorResult;
    }

    @Transactional
    public boolean checkIdDuplicate(MemberDto memberDto){
        return memberRepository.existsById(memberDto.getId());
    }

    @Transactional
    public boolean checkNicknameDuplicate(MemberDto memberDto){
        return memberRepository.existsByNickname(memberDto.getNickname());
    }
    
    @Transactional
    public MemberDto getMemberById(String id){
        MemberEntity member = memberRepository.getById(id);
        return MemberDto.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .password(member.getPassword())
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        //유저네임(현제 : id)
        //유저네임으로 사용자 정보를 가져온다. 현제 id를 기준으로 정보를 가져오도록 되어 있다.
        Optional<MemberEntity> userEntityWrapper = memberRepository.findById(userId);
        MemberEntity userEntity = userEntityWrapper.get();

        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(Role.MEMBER.getValue()));
        return new User(userEntity.getId(), userEntity.getPassword(), authorities);
    }

    @Transactional
    public MemberDto getMember(String id){
        MemberEntity member = memberRepository.getById(id);
        return MemberDto.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .password(member.getPassword())
                .createdDate(member.getCreatedDate())
                .modifiedDate(member.getModifiedDate())
                .build();
    }
}
