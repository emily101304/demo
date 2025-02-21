package com.example.demo.todo.service;

import com.example.demo.member.entity.Member;
import com.example.demo.member.repository.MemberRepository;
import com.example.demo.todo.dto.request.TodoSaveRequestDto;
import com.example.demo.todo.dto.request.TodoUpdateRequestDto;
import com.example.demo.todo.dto.response.TodoResponseDto;
import com.example.demo.todo.dto.response.TodoSaveResponseDto;
import com.example.demo.todo.dto.response.TodoUpdateResponseDto;
import com.example.demo.todo.entity.Todo;
import com.example.demo.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public TodoSaveResponseDto save(Long memberId, TodoSaveRequestDto dto) {

        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new IllegalArgumentException("해당 멤버는 존재하지 않습니다!")
        );
        Todo todo = new Todo(
                dto.getContent(),
                member
        );
        Todo savedDto = todoRepository.save(todo);
        return new TodoSaveResponseDto(
                savedDto.getId(),
                savedDto.getContent(),
                member.getId(),
                member.getEmail()
        );
    }

    @Transactional(readOnly = true)
    public List<TodoResponseDto> findAll() {
        List<Todo> todos = todoRepository.findAll();
        List<TodoResponseDto> dtos = new ArrayList<>();
        for (Todo todo : todos) {
            dtos.add(new TodoResponseDto(
                    todo.getId(),
                    todo.getContent()
            ));
        }
        return dtos;
    }

    @Transactional(readOnly = true)
    public TodoResponseDto findById(Long todoId) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(
                () -> new IllegalArgumentException("해당 Todo는 존재하지 않습니다!")
        );
        return new TodoResponseDto(
                todo.getId(),
                todo.getContent());
    }

    @Transactional
    public TodoUpdateResponseDto update(Long memberId, Long todoId, TodoUpdateRequestDto dto) {

        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new IllegalArgumentException("해당 멤버는 존재하지 않습니다!")
        );

        Todo todo = todoRepository.findById(todoId).orElseThrow(
                () -> new IllegalArgumentException("해당 Todo는 존재하지 않습니다!")
        );

        if (!todo.getMember().getId().equals(member.getId())) {
            throw new IllegalArgumentException("해당 Todo 작성자가 아닙니다!");
        }

        todo.update(dto.getContent());
        return new TodoUpdateResponseDto(
                todo.getId(),
                todo.getContent());
    }

    @Transactional
    public void deleteById(Long memberId, Long todoId) {

        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new IllegalArgumentException("해당 멤버는 존재하지 않습니다!")
        );

        Todo todo = todoRepository.findById(todoId).orElseThrow(
                () -> new IllegalArgumentException("해당 Todo는 존재하지 않습니다!")
        );

        if (!todo.getMember().getId().equals(member.getId())) {
            throw new IllegalArgumentException("해당 Todo 작성자가 아닙니다!");
        }

        todoRepository.deleteById(todoId);
    }
}
