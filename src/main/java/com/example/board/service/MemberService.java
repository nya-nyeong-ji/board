package com.example.board.service;

import com.example.board.domain.Role;
import com.example.board.domain.entity.MemberEntity;
import com.example.board.domain.repository.MemberRepository;
import com.example.board.dto.MemberDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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

    private static final int BLOCK_PAGE_NUM_COUNT = 5;  // 블럭에 존재하는 페이지 번호 수
    private static final int PAGE_MEMBER_COUNT = 4;       // 한 페이지에 존재하는 게시글 수

    @Transactional
    public List<MemberDto> getMemberList(Integer pageNum){
        Page<MemberEntity> page;
        Pageable paging;

        paging = PageRequest.of(pageNum - 1, PAGE_MEMBER_COUNT, Sort.by(Sort.Direction.ASC, "createdDate"));
        page = memberRepository.findAll(paging);

        List<MemberEntity> memberEntities = page.getContent();
        List<MemberDto> memberDtoList = new ArrayList<>();

        for (MemberEntity members: memberEntities){
            memberDtoList.add(convert(members));
        }

        return memberDtoList;
    }

    public Integer[] getPageList(Integer curPageNum){
        Integer[] pageList = new Integer[BLOCK_PAGE_NUM_COUNT];

        Double memberTotalCount = Double.valueOf(memberRepository.count());

        if (memberTotalCount == 0) return  pageList;

        Integer totalLastPageNum = (int)Math.ceil(memberTotalCount/BLOCK_PAGE_NUM_COUNT);

        curPageNum = (curPageNum <= 3) ? 1 : curPageNum - 2;

        Integer blockLastPageNum = (totalLastPageNum > curPageNum + BLOCK_PAGE_NUM_COUNT - 1)
                ? curPageNum + BLOCK_PAGE_NUM_COUNT - 1
                : totalLastPageNum;

        if (totalLastPageNum == blockLastPageNum) curPageNum = totalLastPageNum - BLOCK_PAGE_NUM_COUNT + 1;
        if (curPageNum < 1) curPageNum = 1;

        for (int val = curPageNum, idx = 0; val <= blockLastPageNum; val++, idx++) {
            pageList[idx] = val;
        }

        return pageList;
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        //유저네임(현제 : id)
        //유저네임으로 사용자 정보를 가져온다. 현제 id를 기준으로 정보를 가져오도록 되어 있다.
        Optional<MemberEntity> userEntityWrapper = memberRepository.findById(userId);
        MemberEntity userEntity = userEntityWrapper.get();

        List<GrantedAuthority> authorities = new ArrayList<>();

        if (userEntity.getAuth().equals("admin")) authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
        else if (userEntity.getAuth().equals("member")) authorities.add(new SimpleGrantedAuthority(Role.MEMBER.getValue()));
        else if (userEntity.getAuth().equals("baned")) authorities.add(new SimpleGrantedAuthority(Role.BANED.getValue()));

        return new User(userEntity.getId(), userEntity.getPassword(), authorities);
    }

    //게시글 삭제 및 수정시 관리자나 그 게시글의 작성자여야 합니다
//    @Transactional
//    public boolean checkAuth(String id){
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String username = "";
//        boolean isAdmin = false;
//
//        if(principal instanceof UserDetails){
//            username = ((UserDetails)principal).getUsername();
//            isAdmin = ((UserDetails)principal).getAuthorities().contains(Role.ADMIN);
//        } else{
//            username = principal.toString();
//        }
//
//        return username.equals(id) || isAdmin;
//    }

    @Transactional
    public MemberDto getMember(String id){
        MemberEntity member = memberRepository.getById(id);
        return convert(member);
    }

    public MemberDto convert(MemberEntity memberEntity){
        return MemberDto.builder()
                .id(memberEntity.getId())
                .nickname(memberEntity.getNickname())
                .email(memberEntity.getEmail())
                .password(memberEntity.getPassword())
                .auth(memberEntity.getAuth())
                .createdDate(memberEntity.getCreatedDate())
                .modifiedDate(memberEntity.getModifiedDate())
                .build();
    }
}
