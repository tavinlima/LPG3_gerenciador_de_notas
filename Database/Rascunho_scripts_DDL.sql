CREATE DATABASE IF NOT EXISTS gerenciador_notas;
USE gerenciador_notas;

-- Usuarios
CREATE TABLE tb_usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    perfil VARCHAR(50) NOT NULL,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL,
    data_nascimento DATE NOT NULL
) 

-- Disciplinas
CREATE TABLE tb_disciplinas (
    id_disciplina INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    id_professor INT NOT NULL,
    carga_horaria TIME NOT NULL CHECK (carga_horaria >= 0),
    periodo VARCHAR(10) NOT NULL,
    CONSTRAINT fk_disciplinas_professor FOREIGN KEY (id_professor)
        REFERENCES tb_usuarios (id_usuario)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
)

-- Tabela: Matriculas
CREATE TABLE tb_matriculas (
    id_matricula INT AUTO_INCREMENT PRIMARY KEY,
    id_aluno INT NOT NULL,
    id_disciplina INT NOT NULL,
    data_matricula DATE NOT NULL,
    status VARCHAR(50) NOT NULL,
    CONSTRAINT fk_matriculas_aluno FOREIGN KEY (id_aluno)
        REFERENCES tb_usuarios (id_usuario)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT fk_matriculas_disciplina FOREIGN KEY (id_disciplina)
        REFERENCES tb_disciplinas (id_disciplina)
        ON DELETE CASCADE
        ON UPDATE CASCADE
)

-- Tabela: Notas
CREATE TABLE tb_notas (
    id_nota INT AUTO_INCREMENT PRIMARY KEY,
    id_matricula INT NOT NULL,
    tipo_avaliacao VARCHAR(50) NOT NULL,
    nota DECIMAL(4,2) NOT NULL CHECK (nota >= 0 AND nota <= 10),
    data_lancamento TIMESTAMP NOT NULL,
    CONSTRAINT fk_notas_matricula FOREIGN KEY (id_matricula)
        REFERENCES tb_matriculas (id_matricula)
        ON DELETE CASCADE
        ON UPDATE CASCADE
)
