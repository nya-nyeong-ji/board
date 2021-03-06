package com.example.board.service;

import com.example.board.domain.entity.CommentEntity;
import com.example.board.domain.repository.CommentRepository;
import com.example.board.dto.CommentDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CommentService {

    private CommentRepository commentRepository;

    @Transactional
    public Long saveComment(CommentDto commentDto){
        return commentRepository.save(commentDto.toEntity()).getId();
    }

    @Transactional
    public CommentDto getComment(Long id){
        Optional<CommentEntity> commentEntityWrapper = commentRepository.findById(id);
        CommentEntity commentEntity = commentEntityWrapper.get();

        CommentDto commentDto = convertEntityToDto(commentEntity);
        return commentDto;
    }

    @Transactional
    public List<CommentDto> getCommentListByBoardId(Long boardId){
        List<CommentEntity> commentEntityList = commentRepository.findByBoardId(boardId);
        List<CommentDto> commentDtoList = new ArrayList<>();

        for(CommentEntity commentEntity: commentEntityList){
            commentDtoList.add(convertEntityToDto(commentEntity));
        }

        return commentDtoList;
    }

    @Transactional
    public void deleteComment(Long id){commentRepository.deleteById(id);}

    @Transactional
    public void deleteCommentByBoardId(Long boardId){
        commentRepository.deleteAllByBoardId(boardId);
    }

    private CommentDto convertEntityToDto(CommentEntity commentEntity){
        return CommentDto.builder()
                .id(commentEntity.getId())
                .boardId(commentEntity.getBoardId())
                .writer(commentEntity.getWriter())
                .content(commentEntity.getContent())
                .createdDate(commentEntity.getCreatedDate())
                .build();
    }

    private static final int BLOCK_PAGE_NUM_COUNT = 5;  // ????????? ???????????? ????????? ?????? ???
    private static final int PAGE_COMMENT_COUNT = 4;       // ??? ???????????? ???????????? ????????? ???

    @Transactional
    public List<CommentDto> getCommentListByBoardId(Long boardId, Integer pageNum, String type) {
        Pageable paging;
        if (type.equals("date")) paging = PageRequest.of(pageNum - 1, PAGE_COMMENT_COUNT, Sort.by(Sort.Direction.ASC, "createdDate"));
        else paging = PageRequest.of(pageNum - 1, PAGE_COMMENT_COUNT, Sort.by(Sort.Direction.DESC, "totalLike"));
        Page<CommentEntity> page = commentRepository.findAllByBoardId(boardId, paging);

        List<CommentEntity> commentEntities = page.getContent();
        List<CommentDto> commentDtoList = new ArrayList<>();

        for (CommentEntity commentEntity : commentEntities) {
            commentDtoList.add(this.convertEntityToDto(commentEntity));
        }

        return commentDtoList;
    }

    @Transactional
    public Long getCommentCount(Long boardId) {
        return commentRepository.countByBoardId(boardId);
    }

    public Integer[] getPageList(Long boardId, Integer curPageNum) {
        Integer[] pageList = new Integer[BLOCK_PAGE_NUM_COUNT];

        // ??? ????????? ??????
        Double commentsTotalCount = Double.valueOf(this.getCommentCount(boardId));

        if (commentsTotalCount == 0) return pageList;

        // ??? ????????? ???????????? ????????? ????????? ????????? ?????? ?????? (???????????? ??????)
        Integer totalLastPageNum = (int)(Math.ceil((commentsTotalCount/PAGE_COMMENT_COUNT)));

        // ????????? ?????? ?????? ??????
        curPageNum = (curPageNum <= 3) ? 1 : curPageNum - 2;

        // ?????? ???????????? ???????????? ????????? ????????? ????????? ?????? ??????
        Integer blockLastPageNum = (totalLastPageNum > curPageNum + BLOCK_PAGE_NUM_COUNT - 1)
                ? curPageNum + BLOCK_PAGE_NUM_COUNT - 1
                : totalLastPageNum;

        // ????????? ????????? ?????? ?????? ????????? ??????
        if (totalLastPageNum == blockLastPageNum) curPageNum = totalLastPageNum - BLOCK_PAGE_NUM_COUNT + 1;
        if (curPageNum < 1) curPageNum = 1;

        // ????????? ?????? ??????
        for (int val = curPageNum, idx = 0; val <= blockLastPageNum; val++, idx++) {
            pageList[idx] = val;
        }

        return pageList;
    }
}
