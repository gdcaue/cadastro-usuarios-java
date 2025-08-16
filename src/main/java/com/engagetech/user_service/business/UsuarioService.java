package com.engagetech.user_service.business;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.engagetech.user_service.infrastructure.repository.UsuarioRepository;
import com.engagetech.user_service.infrastructure.entitys.Usuario;

/**
 * Serviço responsável pela lógica de negócios relacionada à entidade {@link Usuario}.
 * 
 * Responsabilidades:
 * - Criptografar a senha do usuário antes de salvar.
 * - Buscar usuários por diferentes critérios (ID, e-mail ou telefone).
 * - Atualizar usuários existentes preservando dados que não forem alterados.
 * - Deletar usuários conforme o critério de busca.
 * 
 * Este service funciona como intermediário entre o {@link UsuarioController}
 * e o {@link UsuarioRepository}.
 */
@Service
public class UsuarioService {
    private final UsuarioRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * Construtor do serviço de usuários.
     *
     * @param repository Repositório de persistência de {@link Usuario}.
     */
    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    /**
     * Salva um novo usuário no banco de dados, criptografando sua senha.
     *
     * @param usuario Usuário a ser salvo.
     * @return Usuário persistido com ID gerado.
     */
    public Usuario salvarUsuario(Usuario usuario) {
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return repository.saveAndFlush(usuario);
    }

    /**
     * Enum que define os tipos de busca possíveis para encontrar um usuário.
     */
    public enum TipoBusca {
        EMAIL, TELEFONE, ID
    }

    /**
     * Busca um usuário no banco de dados conforme o tipo de critério informado.
     *
     * @param valor Valor usado na busca (e-mail, telefone ou ID).
     * @param tipo Tipo de busca a ser realizada.
     * @return Usuário encontrado.
     * @throws RuntimeException caso o usuário não seja encontrado.
     */
    public Usuario buscarUsuario(String valor, TipoBusca tipo) {
        return switch (tipo) {
            case EMAIL -> repository.findByEmail(valor)
                    .orElseThrow(() -> new RuntimeException("Usuário com e-mail " + valor + " não encontrado."));
            case TELEFONE -> repository.findByTelefone(valor)
                    .orElseThrow(() -> new RuntimeException("Usuário com telefone " + valor + " não encontrado."));
            case ID -> repository.findById(Integer.valueOf(valor))
                    .orElseThrow(() -> new RuntimeException("Usuário com ID " + valor + " não encontrado."));
        };
    }

    /**
     * Deleta um usuário conforme o critério de busca informado.
     *
     * @param valor Valor usado na busca (e-mail, telefone ou ID).
     * @param tipo Tipo de busca a ser realizada.
     * @throws RuntimeException caso o usuário não seja encontrado.
     */
    public void deletarUsuario(String valor, TipoBusca tipo) {
        switch (tipo) {
            case EMAIL -> repository.findByEmail(valor)
                    .ifPresentOrElse(repository::delete,
                            () -> { throw new RuntimeException("Usuário com e-mail " + valor + " não encontrado."); });
            case TELEFONE -> repository.findByTelefone(valor)
                    .ifPresentOrElse(repository::delete,
                            () -> { throw new RuntimeException("Usuário com telefone " + valor + " não encontrado."); });
            case ID -> {
                Integer id = Integer.valueOf(valor);
                if (repository.existsById(id)) {
                    repository.deleteById(id);
                } else {
                    throw new RuntimeException("Usuário com ID " + id + " não encontrado.");
                }
            }
            default -> throw new IllegalArgumentException("Tipo de busca inválido: " + tipo);
        }
    }

    /**
     * Atualiza um usuário existente por ID.
     *
     * @param id ID do usuário que será atualizado.
     * @param usuario Dados do usuário que irão sobrescrever os existentes.
     * @return Usuário atualizado.
     * @throws RuntimeException caso o usuário não seja encontrado.
     */
    public Usuario atualizarUsuarioPorId(Integer id, Usuario usuario) {
        Usuario usuarioAtual = repository.findById(id).orElseThrow(
                () -> new RuntimeException("Usuário não encontrado com ID " + id)
        );

        Usuario usuarioAtualizado = Usuario.builder()
                .id(usuarioAtual.getId())
                .nome(usuario.getNome() != null ? usuario.getNome() : usuarioAtual.getNome())
                .email(usuario.getEmail() != null ? usuario.getEmail() : usuarioAtual.getEmail())
                .telefone(usuario.getTelefone() != null ? usuario.getTelefone() : usuarioAtual.getTelefone())
                .senha(usuario.getSenha() != null
                        ? passwordEncoder.encode(usuario.getSenha())
                        : usuarioAtual.getSenha())
                .build();

        return repository.saveAndFlush(usuarioAtualizado);
    }
}
