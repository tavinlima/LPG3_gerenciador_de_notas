# 📦 Guia de Instalação e Estrutura do Projeto

## Arquivo de Configuração

📁 `src/main/resources/`
- `application.properties`

**Conteúdo do application.properties:**
```properties
spring.application.name=gerenciador-notas
spring.datasource.url=jdbc:mysql://localhost:3306/gerenciador_notas
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

jwt.secret=sua-chave-secreta-muito-segura-aqui-com-pelo-menos-256-bits
jwt.expiration=86400000

springdoc.api-docs.path=/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
```

## 🚀 Passos para Executar

### Passo 1: Preparar o Banco de Dados

1. Instale o MySQL (se ainda não tiver)
2. Crie o banco de dados executando o script SQL fornecido:

```sql
CREATE DATABASE IF NOT EXISTS gerenciador_notas;
USE gerenciador_notas;
CREATE DATABASE IF NOT EXISTS sistema_notas;

USE sistema_notas;

-- Status
CREATE TABLE tb_status (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           titulo VARCHAR(15) NOT NULL UNIQUE
);

-- Usuarios
CREATE TABLE tb_usuarios (
                             id INT AUTO_INCREMENT PRIMARY KEY,
                             perfil VARCHAR(50) NOT NULL,
                             nome VARCHAR(100) NOT NULL,
                             email VARCHAR(100) NOT NULL UNIQUE,
                             senha VARCHAR(255) NOT NULL,
                             id_status INT NOT NULL,
                             CONSTRAINT fk_status FOREIGN KEY (id_status)
                                 REFERENCES tb_status (id)
                                 ON DELETE CASCADE
                                 ON UPDATE CASCADE,
                             data_nascimento DATE NOT NULL
);

-- Disciplinas
CREATE TABLE tb_disciplinas (
                                id INT AUTO_INCREMENT PRIMARY KEY,
                                nome VARCHAR(100) NOT NULL,
                                id_professor INT NOT NULL,
                                carga_horaria DECIMAL(5,2) NOT NULL CHECK (carga_horaria >= 0),
                                numero_aulas INT NOT NULL,
                                periodo VARCHAR(10) NOT NULL,
                                CONSTRAINT fk_disciplinas_professor FOREIGN KEY (id_professor)
                                    REFERENCES tb_usuarios (id)
                                    ON DELETE RESTRICT
                                    ON UPDATE CASCADE
);

-- Tabela: Matriculas
CREATE TABLE tb_matriculas (
                               id INT AUTO_INCREMENT PRIMARY KEY,
                               id_aluno INT NOT NULL,
                               id_disciplina INT NOT NULL,
                               data_matricula DATE NOT NULL,
                               id_status INT NOT NULL,
                               CONSTRAINT fk_status_matricula FOREIGN KEY (id_status)
                                   REFERENCES tb_status (id)
                                   ON DELETE CASCADE
                                   ON UPDATE CASCADE,
                               CONSTRAINT fk_matriculas_aluno FOREIGN KEY (id_aluno)
                                   REFERENCES tb_usuarios (id)
                                   ON DELETE CASCADE
                                   ON UPDATE CASCADE,
                               CONSTRAINT fk_matriculas_disciplina FOREIGN KEY (id_disciplina)
                                   REFERENCES tb_disciplinas (id)
                                   ON DELETE CASCADE
                                   ON UPDATE CASCADE
);

-- Tabela: Notas
CREATE TABLE tb_notas (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          id_matricula INT NOT NULL,
                          tipo_avaliacao VARCHAR(50) NOT NULL,
                          nota DECIMAL(4,2) NOT NULL CHECK (nota >= 0 AND nota <= 10),
                          data_lancamento TIMESTAMP NOT NULL,
                          peso DECIMAL(4,2) NOT NULL DEFAULT 1.0,
                          CONSTRAINT fk_notas_matricula FOREIGN KEY (id_matricula)
                              REFERENCES tb_matriculas (id)
                              ON DELETE CASCADE
                              ON UPDATE CASCADE
);

INSERT INTO tb_status(titulo)
VALUES ('matriculado'), ('ativo'), ('formado'), ('trancado'), ('aprovado'),('reprovado'),('recuperação');

INSERT into tb_usuarios (perfil,nome,email,senha,id_status,data_nascimento)
VALUES
    ('Professor', 'Roberto','roberto@email.com','senha123',1,'2000-01-01'),
    ('Aluno', 'Felipe','felipe@email.com','senha123',3,'2005-06-01'),
    ('Aluno', 'Luís','luis@email.com','senha123',4,'1995-01-12'),
    ('Coordenador', 'João','joao@email.com','senha123',2,'1998-12-01'),
    ('Professor', 'Claudia','claudia@email.com','senha123',2,'2000-01-01');

INSERT INTO tb_disciplinas(nome, id_professor, carga_horaria, numero_aulas, periodo)
VALUES
    ('Lógica de Programação',1,40.5,19,2025.1),
    ('Desenvolvimento Web I',2,40.5,19,2025.2),
    ('Banco de dados', 2, 40.5, 19, 2025.2);

INSERT INTO tb_matriculas(id_aluno, id_disciplina, data_matricula, id_status)
VALUES
    (2,1,'2025-01-02',5),
    (3,2,'2025-07-02',1),
    (2,3,'2025-07-03',3);

INSERT INTO tb_notas(id_matricula, tipo_avaliacao, nota, data_lancamento)
VALUES
    (1,'Prova',9.5,'2025-09-09'),
    (1,'Apresentação',10,'2025-09-09'),
    (1,'Prova',6,'2025-09-09'),
    (2,'Prova',8.5,'2025-10-09');

SELECT * FROM tb_usuarios;
SELECT * FROM tb_status;
SELECT * FROM tb_disciplinas;
SELECT * FROM tb_matriculas;
SELECT * FROM tb_notas;
```

### Passo 2: Configurar o Projeto

1. Abra o projeto na sua IDE (IntelliJ IDEA, Eclipse, VS Code)
2. Aguarde o Maven baixar todas as dependências
3. Configure o `application.properties` com suas credenciais do MySQL

### Passo 3: Compilar o Projeto

**No terminal (dentro da pasta do projeto):**

```bash
# Linux/Mac
./mvnw clean install

# Windows
mvnw.cmd clean install
```

**Ou se tiver Maven instalado:**
```bash
mvn clean install
```

### Passo 4: Executar a Aplicação

```bash
# Linux/Mac
./mvnw spring-boot:run

# Windows
mvnw.cmd spring-boot:run

# Ou com Maven instalado
mvn spring-boot:run
```

### Passo 5: Verificar se está funcionando

Acesse no navegador:
- **Swagger UI**: http://localhost:8080/swagger-ui.html

## 🛠️ Usando IDEs

### IntelliJ IDEA

1. File → New → Project from Existing Sources
2. Selecione a pasta do projeto
3. Escolha "Maven" como tipo de projeto
4. Aguarde a indexação e download de dependências
5. Clique com botão direito em `GerenciadorNotasApplication.java`
6. Selecione "Run 'GerenciadorNotasApplication'"

### Eclipse

1. File → Import → Maven → Existing Maven Projects
2. Selecione a pasta do projeto
3. Aguarde o build do projeto
4. Clique com botão direito no projeto → Run As → Spring Boot App

## Problemas Comuns e Soluções

### Erro: "Package does not exist"
**Solução:** Execute `mvn clean install` para baixar dependências

### Erro: "Port 8080 already in use"
**Solução:** 
1. Mate o processo na porta 8080
2. Ou adicione no application.properties: `server.port=8081`

### Erro: "Cannot connect to database"
**Solução:**
1. Verifique se MySQL está rodando
2. Confirme usuário/senha no application.properties
3. Teste conexão: `mysql -u root -p`

### Erro de compilação do Lombok
**Solução:**
1. Instale plugin Lombok na IDE
2. IntelliJ: Settings → Plugins → Procure "Lombok"
3. Eclipse: Download lombok.jar e execute

## 📊 Testando a API

### 1. Fazer Login

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "joao@email.com",
    "senha": "senha123"
  }'
```

### 2. Usar o Token Retornado

```bash
curl -X GET http://localhost:8080/api/usuarios \
  -H "Authorization: Bearer SEU_TOKEN_AQUI"
```

### 3. Ou use o Swagger

Acesse http://localhost:8080/swagger-ui.html e teste diretamente pela interface!

### Postman
Nesse arquivo encontra-se uma coleção Postman pronta para uso: [GerenciadorNotas.postman_collection.json](./collection_postman.json)

### Regras de negócio:
- Professores podem gerenciar disciplinas e lançar notas
- Alunos podem ver suas notas e status
- Coordenadores podem gerenciar usuários e matrículas
  