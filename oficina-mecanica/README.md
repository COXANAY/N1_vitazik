# Oficina Mecânica

Esta é a pasta do código-fonte e dos arquivos de execução do projeto.

📄 **A documentação completa (identificação do time, descrição do sistema, guia de execução,
URLs, credenciais e observabilidade) está no [README da raiz do repositório](../README.md).**

## Resumo rápido

```bash
# Tudo em Docker (app + PostgreSQL + Prometheus + Grafana)
make prod-up

# Ou: infra em Docker + app no host (desenvolvimento)
make dev-local-up
make dev
```

| Recurso | URL |
|---------|-----|
| Aplicação | http://localhost:8080 |
| Swagger UI | http://localhost:8080/swagger-ui.html |
| Grafana | http://localhost:3000 (`admin` / `admin`) |
| Prometheus | http://localhost:9090 |

Stack: Spring Boot 3.3 · Java 21 · PostgreSQL 16 · Spring Data JPA · SpringDoc/Swagger ·
Actuator + Micrometer/Prometheus · Grafana · Docker Compose.
