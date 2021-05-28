package com.example.board.service;

import com.example.board.domain.entity.CommentEntity;
import com.example.board.domain.repository.CommentRepository;
import com.example.board.dto.CommentDto;
import lombok.AllArgsConstructor;
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
}
