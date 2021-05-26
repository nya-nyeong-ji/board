package com.example.board.service;

import com.example.board.domain.entity.BoardEntity;
import com.example.board.domain.repository.BoardRepository;
import com.example.board.dto.BoardDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class BoardService {

    private BoardRepository boardRepository;

    @Transactional
    public Long savePost(BoardDto boardDto){
        return boardRepository.save(boardDto.toEntity()).getId();
    }

    @Transactional
    public List<BoardDto> getPostList(){
        List<BoardEntity> boardEntities = boardRepository.findAll();
        List<BoardDto> boardDtos = new ArrayList<>();

        for(BoardEntity boardEntity: boardEntities){
            BoardDto boardDto = convertEntityToDto(boardEntity);
            boardDtos.add(boardDto);
        }

        return boardDtos;
    }

    @Transactional
    public BoardDto getPost(Long id){
        BoardEntity boardEntity = boardRepository.getById(id);

        return convertEntityToDto(boardEntity);
    }

    @Transactional
    public void deletePost(Long id){boardRepository.deleteById(id);}

    public BoardDto convertEntityToDto(BoardEntity boardEntity){
        return BoardDto.builder()
                .id(boardEntity.getId())
                .title(boardEntity.getTitle())
                .content(boardEntity.getContent())
                .writer(boardEntity.getWriter())
                .createdDate(boardEntity.getCreatedDate())
                .build();
    }
}
