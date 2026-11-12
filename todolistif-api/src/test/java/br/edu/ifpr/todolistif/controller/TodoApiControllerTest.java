package br.edu.ifpr.todolistif.controller;

import br.edu.ifpr.todolistif.dto.TodoResponseDTO;
import br.edu.ifpr.todolistif.model.Todo;
import br.edu.ifpr.todolistif.service.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class TodoApiControllerTest {

    private MockMvc mockMvc;
    private TodoService todoService;

    @BeforeEach
    void setUp() {
        todoService = mock(TodoService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new TodoApiController(todoService)).build();
    }

    @Test
    void deveRetornarStatus200EListaDeTarefasPendentesNoJson() throws Exception {
        Todo todo1 = new Todo();
        todo1.setId(1L);
        todo1.setTitle("Estudar Spring Boot");
        todo1.setCreatedAt(LocalDateTime.of(2026, 11, 12, 8, 0));
        todo1.setDeadLine(LocalDateTime.of(2026, 11, 20, 18, 0));

        Todo todo2 = new Todo();
        todo2.setId(2L);
        todo2.setTitle("Implementar testes com MockMvc");
        todo2.setCreatedAt(LocalDateTime.of(2026, 11, 12, 9, 0));
        todo2.setDeadLine(LocalDateTime.of(2026, 11, 25, 18, 0));

        List<TodoResponseDTO> listaMock = List.of(
                new TodoResponseDTO(todo1),
                new TodoResponseDTO(todo2)
        );

       when(todoService.listarPendentes()).thenReturn(listaMock);

        mockMvc.perform(get("/api/todos/pendentes")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Estudar Spring Boot"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].title").value("Implementar testes com MockMvc"));
    }
}
