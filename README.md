# Oficina Mecânica API

CRUD de ordens de serviço feito com Spring Boot e banco H2 em memória.

## Tecnologias

- Java 21, Spring Boot 3.3, Spring Data JPA, H2, Lombok

## Como rodar

```bash
cd oficina-mecanica
mvn spring-boot:run
```

A API sobe em `http://localhost:8080`. Os dados somem ao reiniciar pois o banco é em memória.

## Endpoints

| Método | URL | Descrição |
|--------|-----|-----------|
| POST | /ordens | Criar ordem |
| GET | /ordens | Listar todas |
| GET | /ordens/{id} | Buscar por ID |
| PUT | /ordens/{id} | Atualizar |
| DELETE | /ordens/{id} | Deletar |
| GET | /ordens/status/{status} | Filtrar por status |

Status disponíveis: `ABERTO`, `EM_ANDAMENTO`, `FINALIZADO`

## Exemplo de body (POST/PUT)

```json
{
  "cliente": "João Silva",
  "veiculo": "Honda Civic 2020",
  "problema": "Troca de óleo",
  "status": "ABERTO",
  "valor": 150.00
}
```

## Console H2

Acesse `http://localhost:8080/h2-console` com a JDBC URL `jdbc:h2:mem:oficina`, usuário `sa` e senha em branco.
