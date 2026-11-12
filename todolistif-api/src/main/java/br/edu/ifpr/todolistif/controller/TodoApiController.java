package br.edu.ifpr.todolistif.controller;

import br.edu.ifpr.todolistif.dto.TodoResponseDTO;
import br.edu.ifpr.todolistif.service.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodoApiController {

    private final TodoService todoService;

    public TodoApiController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public ResponseEntity<List<TodoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(todoService.listar());
    }

    @GetMapping("/pendentes")
    public ResponseEntity<List<TodoResponseDTO>> listarPendentes() {
        return ResponseEntity.ok(todoService.listarPendentes());
    }
}