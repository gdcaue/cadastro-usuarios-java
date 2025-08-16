package com.engagetech.user_service.infrastructure.entities;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entidade que representa um usuário no sistema.
 *
 * Mapeada para a tabela "usuarios" no banco de dados.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "usuarios")
@Entity
public class Usuario {

    /** Identificador único do usuário */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /** Nome completo do usuário */
    @Column(name = "nome", nullable = false)
    private String nome;

    /** E-mail do usuário (único) */
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    /** Telefone do usuário */
    @Column(name = "telefone", nullable = false)
    private String telefone;

    /** Senha do usuário (armazenada criptografada) */
    @Column(name = "senha", nullable = false)
    private String senha;
}
