package com.engagetech.user_service.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.engagetech.user_service.infrastructure.entities.Usuario;

import jakarta.transaction.Transactional;
import java.util.Optional;

/**
 * Repositório de persistência para a entidade {@link Usuario}.
 *
 * Extende {@link JpaRepository} para fornecer operações básicas de CRUD.
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    /**
     * Busca um usuário pelo e-mail.
     *
     * @param email E-mail do usuário.
     * @return Optional com o usuário encontrado, se existir.
     */
    Optional<Usuario> findByEmail(String email);

    /**
     * Busca um usuário pelo telefone.
     *
     * @param telefone Telefone do usuário.
     * @return Optional com o usuário encontrado, se existir.
     */
    Optional<Usuario> findByTelefone(String telefone);

    /**
     * Deleta um usuário pelo e-mail.
     *
     * @param email E-mail do usuário.
     */
    @Transactional
    void deleteByEmail(String email);

    /**
     * Deleta um usuário pelo telefone.
     *
     * @param telefone Telefone do usuário.
     */
    @Transactional
    void deleteByTelefone(String telefone);
}
