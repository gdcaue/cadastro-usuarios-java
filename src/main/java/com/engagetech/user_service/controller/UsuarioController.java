package com.engagetech.user_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.engagetech.user_service.business.UsuarioService;
import com.engagetech.user_service.business.UsuarioService.TipoBusca;
import com.engagetech.user_service.infrastructure.model.Usuario;

import lombok.RequiredArgsConstructor;
import java.util.Map;

/**
 * Controller responsável por expor a API REST de {@link Usuario}.
 *
 * Endpoints disponíveis:
 * - POST /api/usuarios → Cria um novo usuário
 * - GET /api/usuarios → Busca um usuário por ID, e-mail ou telefone
 * - PUT /api/usuarios/{id} → Atualiza usuário existente
 * - DELETE /api/usuarios → Remove usuário por critério
 *
 * Todas as respostas são enviadas no formato JSON.
 */
@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    /**
     * Cria um novo usuário no sistema.
     *
     * @param usuario Dados do usuário no corpo da requisição.
     * @return Usuário criado (201 Created) ou erro (400 Bad Request).
     */
    @PostMapping
    public ResponseEntity<?> salvarUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario salvo = usuarioService.salvarUsuario(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("erro", "Não foi possível salvar o usuário", "detalhe", e.getMessage()));
        }
    }

    /**
     * Busca um usuário no sistema.
     *
     * @param valor Valor usado na busca (e-mail, telefone ou ID).
     * @param tipo Tipo da busca.
     * @return Usuário encontrado (200 OK) ou erro (404 Not Found).
     */
    @GetMapping
    public ResponseEntity<?> buscarUsuario(
            @RequestParam String valor,
            @RequestParam TipoBusca tipo) {
        try {
            Usuario usuario = usuarioService.buscarUsuario(valor, tipo);
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("erro", e.getMessage()));
        }
    }

    /**
     * Deleta um usuário conforme o critério informado.
     *
     * @param valor Valor usado na busca.
     * @param tipo Tipo da busca.
     * @return Resposta vazia (204 No Content) ou erro (404 Not Found).
     */
    @DeleteMapping
    public ResponseEntity<?> deletarUsuario(
            @RequestParam String valor,
            @RequestParam TipoBusca tipo) {
        try {
            usuarioService.deletarUsuario(valor, tipo);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("erro", e.getMessage()));
        }
    }

    /**
     * Atualiza um usuário existente pelo ID.
     *
     * @param id ID do usuário a ser atualizado.
     * @param usuario Dados do usuário no corpo da requisição.
     * @return Usuário atualizado (200 OK) ou erro (404 Not Found).
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarUsuarioPorId(
            @PathVariable Integer id,
            @RequestBody Usuario usuario) {
        try {
            Usuario atualizado = usuarioService.atualizarUsuarioPorId(id, usuario);
            return ResponseEntity.ok(atualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("erro", e.getMessage()));
        }
    }
}
