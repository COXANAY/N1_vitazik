# Oficina Mecânica - Sistema de Gerenciamento

## Identificação do Time

| Nome Completo | RA / Matrícula |
|---------------|----------------|
| NOME_COMPLETO_1 | RA_XXXXX |
| NOME_COMPLETO_2 | RA_XXXXX |
| NOME_COMPLETO_3 | RA_XXXXX |

> **Preencha a tabela acima com os dados dos integrantes do grupo.**

---

## Título do Trabalho

**Sistema de Gerenciamento de Oficina Mecânica**

---

## Descrição do Sistema

### O que o sistema faz

O sistema permite que uma oficina mecânica gerencie seus **clientes** e as **ordens de serviço** associadas a eles. O problema de negócio resolvido é a necessidade de controlar quais serviços foram solicitados por cada cliente, o status de cada reparo e o valor cobrado.

### Relacionamento entre as entidades

- **Cliente** (`/clientes`): armazena nome, CPF, telefone e e-mail do cliente.
- **OrdemServico** (`/ordens`): registra o veículo, o problema relatado, o status atual (`ABERTO`, `EM_ANDAMENTO`, `FINALIZADO`) e o valor do serviço. Cada ordem pertence a **um cliente** (relacionamento `@ManyToOne`).

### Fluxo principal de navegação

1. Acesse `http://localhost:8080` — página inicial com links para as duas telas.
2. **Tela de Clientes** (`/clientes.html`):
   - Preencha nome, CPF, telefone e (opcional) e-mail e clique em **Salvar** para cadastrar um cliente.
   - A tabela abaixo lista todos os clientes cadastrados.
   - Use **Editar** para alterar os dados de um cliente ou **Excluir** para removê-lo.
3. **Tela de Ordens de Serviço** (`/ordens.html`):
   - Selecione um cliente na lista suspensa, informe o veículo, o problema, o status e o valor, e clique em **Salvar**.
   - A tabela exibe todas as ordens com destaque visual por status.
   - Use o filtro de status para visualizar apenas ordens em um determinado estado.
   - Use **Editar** para atualizar ou **Excluir** para remover uma ordem.

---

## Guia Passo a Passo de Execução

### Pré-requisitos

| Ferramenta | Versão mínima |
|------------|---------------|
| Java JDK   | 21            |
| Maven      | 3.8+          |
| Docker Desktop | Qualquer versão recente (para Prometheus + Grafana) |

### 1. Clonar o repositório

```bash
git clone <URL_DO_REPOSITORIO>
cd oficina-mecanica
```

### 2. Subir o Prometheus e o Grafana (Docker)

```bash
docker-compose up -d
```

Aguarde os containers iniciarem. Você pode verificar com:

```bash
docker-compose ps
```

### 3. Iniciar a aplicação Spring Boot

```bash
./mvnw spring-boot:run
```

> No Windows, use: `mvnw.cmd spring-boot:run`

A aplicação estará disponível em `http://localhost:8080`.

### 4. URLs de acesso

| Recurso | URL |
|---------|-----|
| Tela inicial | http://localhost:8080 |
| Tela de Clientes | http://localhost:8080/clientes.html |
| Tela de Ordens de Serviço | http://localhost:8080/ordens.html |
| Swagger UI | http://localhost:8080/swagger-ui.html |
| Console H2 | http://localhost:8080/h2-console |
| Actuator (saúde) | http://localhost:8080/actuator/health |
| Métricas Prometheus | http://localhost:8080/actuator/prometheus |
| Prometheus | http://localhost:9090 |
| Grafana | http://localhost:3000 |

### 5. Credenciais padrão

| Sistema | Usuário | Senha |
|---------|---------|-------|
| Grafana | `admin` | `admin` |
| H2 Console | JDBC URL: `jdbc:h2:mem:oficina` / User: `sa` / Senha: *(vazio)* |

---

## Configuração do Grafana

### Adicionar o Prometheus como datasource

1. Acesse `http://localhost:3000` e faça login (`admin` / `admin`).
2. Vá em **Connections → Data sources → Add data source**.
3. Selecione **Prometheus**.
4. No campo **URL**, insira: `http://prometheus:9090`
5. Clique em **Save & test**.

### Criar o Dashboard

1. Clique em **+ → New dashboard → Add visualization**.
2. Selecione o datasource **Prometheus**.
3. Exemplos de queries para os painéis exigidos:

| Métrica | Query PromQL |
|---------|-------------|
| CPU usage | `process_cpu_usage{application="oficina-mecanica"}` |
| Memória JVM usada | `jvm_memory_used_bytes{area="heap"}` |
| Tempo médio de resposta HTTP | `rate(http_server_requests_seconds_sum[1m]) / rate(http_server_requests_seconds_count[1m])` |
| Status do banco de dados | `hikaricp_connections_active` |
| Total de requisições HTTP | `rate(http_server_requests_seconds_count[1m])` |

---

## Arquitetura

```
oficina-mecanica/
├── src/main/java/com/oficina/
│   ├── controller/          # REST Controllers (ClienteController, OrdemServicoController)
│   ├── service/             # Regras de negócio (ClienteService, OrdemServicoService)
│   ├── repository/          # Spring Data JPA (ClienteRepository, OrdemServicoRepository)
│   ├── model/               # Entidades JPA (Cliente, OrdemServico, StatusOrdem)
│   ├── dto/                 # DTOs de entrada e saída
│   └── exception/           # ResourceNotFoundException
├── src/main/resources/
│   ├── static/              # Frontend (index.html, clientes.html, ordens.html)
│   └── application.properties
├── docker-compose.yml       # Prometheus + Grafana
├── prometheus.yml           # Configuração de scraping
└── pom.xml
```
