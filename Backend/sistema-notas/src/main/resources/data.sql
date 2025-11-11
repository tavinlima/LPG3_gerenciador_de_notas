CREATE DATABASE IF NOT EXISTS gerenciador_notas;
USE gerenciador_notas;

-- Status
CREATE TABLE IF NOT EXISTS tb_status (
  id_status INT AUTO_INCREMENT PRIMARY KEY,
  titulo VARCHAR(15) NOT NULL UNIQUE
);

-- Usuarios
CREATE TABLE IF NOT EXISTS tb_usuarios (
  id_usuario INT AUTO_INCREMENT PRIMARY KEY,
  perfil VARCHAR(50) NOT NULL,
  nome VARCHAR(100) NOT NULL,
  email VARCHAR(100) NOT NULL UNIQUE,
  senha VARCHAR(255) NOT NULL,
  id_status INT NOT NULL,
  CONSTRAINT fk_status FOREIGN KEY (id_status)
    REFERENCES tb_status (id_status)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  data_nascimento DATE NOT NULL
);

-- Disciplinas
CREATE TABLE IF NOT EXISTS tb_disciplinas (
  id_disciplina INT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(100) NOT NULL,
  id_professor INT NOT NULL,
  carga_horaria DECIMAL(5,2) NOT NULL,
  numero_aulas INT NOT NULL,
  periodo VARCHAR(10) NOT NULL,
  CONSTRAINT fk_disciplinas_professor FOREIGN KEY (id_professor)
    REFERENCES tb_usuarios (id_usuario)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
);

-- Matriculas
CREATE TABLE IF NOT EXISTS tb_matriculas (
  id_matricula INT AUTO_INCREMENT PRIMARY KEY,
  id_aluno INT NOT NULL,
  id_disciplina INT NOT NULL,
  data_matricula DATE NOT NULL,
  id_status INT NOT NULL,
  CONSTRAINT fk_status_matricula FOREIGN KEY (id_status)
    REFERENCES tb_status (id_status)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT fk_matriculas_aluno FOREIGN KEY (id_aluno)
    REFERENCES tb_usuarios (id_usuario)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT fk_matriculas_disciplina FOREIGN KEY (id_disciplina)
    REFERENCES tb_disciplinas (id_disciplina)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

-- Notas
CREATE TABLE IF NOT EXISTS tb_notas (
  id_nota INT AUTO_INCREMENT PRIMARY KEY,
  id_matricula INT NOT NULL,
  tipo_avaliacao VARCHAR(50) NOT NULL,
  nota DECIMAL(4,2) NOT NULL,
  data_lancamento TIMESTAMP NOT NULL,
  peso DECIMAL(4,2) NOT NULL DEFAULT 1.0,
  CONSTRAINT fk_notas_matricula FOREIGN KEY (id_matricula)
    REFERENCES tb_matriculas (id_matricula)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

INSERT INTO tb_status(titulo) VALUES ('matriculado'), ('ativo'), ('formado'), ('trancado'), ('aprovado');

-- Usuarios (senhas em bcrypt)
INSERT INTO tb_usuarios(perfil,nome,email,senha,id_status,data_nascimento)
VALUES
('Professor', 'Roberto','roberto@email.com','$2b$12$U6SbfHwS9voGRHO2Ta/LDeJISWEYm1zIeTDk5rR3NSGaU348mOwKy',1,'2000-01-01'),
('Aluno', 'Felipe','felipe@email.com','$2b$12$U6SbfHwS9voGRHO2Ta/LDeJISWEYm1zIeTDk5rR3NSGaU348mOwKy',3,'2005-06-01'),
('Aluno', 'Luís','luis@email.com','$2b$12$U6SbfHwS9voGRHO2Ta/LDeJISWEYm1zIeTDk5rR3NSGaU348mOwKy',4,'1995-01-12'),
('Coordenador', 'João','joao@email.com','$2b$12$U6SbfHwS9voGRHO2Ta/LDeJISWEYm1zIeTDk5rR3NSGaU348mOwKy',2,'1998-12-01'),
('Professor', 'Claudia','claudia@email.com','$2b$12$U6SbfHwS9voGRHO2Ta/LDeJISWEYm1zIeTDk5rR3NSGaU348mOwKy',2,'2000-01-01');

-- Disciplinas
INSERT INTO tb_disciplinas(nome, id_professor, carga_horaria, numero_aulas, periodo)
VALUES
('Lógica de Programação',1,40.5,19,'2025.1'),
('Desenvolvimento Web I',5,40.5,19,'2025.2'),
('Banco de dados', 5, 40.5, 19,'2025.2');

-- Matriculas
INSERT INTO tb_matriculas(id_aluno, id_disciplina, data_matricula, id_status)
VALUES
(2,1,'2025-01-02',5),
(3,2,'2025-07-02',1),
(2,3,'2025-07-03',3);

-- Notas
INSERT INTO tb_notas(id_matricula, tipo_avaliacao, nota, data_lancamento, peso)
VALUES
(1,'Prova',9.50,'2025-09-09 00:00:00',1.0),
(1,'Apresentação',10.00,'2025-09-09 00:00:00',1.0),
(1,'Prova',6.00,'2025-09-09 00:00:00',1.0),
(2,'Prova',8.50,'2025-10-09 00:00:00',1.0);
