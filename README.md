# Raízes do Nordeste — API Back-end

Projeto Multidisciplinar — Trilha Back-end (UNINTER, 2026)
API REST para gestão de pedidos de uma rede de lanchonetes, com suporte a múltiplos canais (App, Totem, Balcão, Pickup, Web), controle de estoque por unidade, autenticação JWT e simulação de pagamento (mock).

Repositório: https://github.com/eduardooflores/API-JAVA/tree/master

---

## Sumário

- [Requisitos](#requisitos)
- [Como configurar as variáveis de ambiente](#como-configurar-as-variáveis-de-ambiente)
- [Como instalar as dependências](#como-instalar-as-dependências)
- [Como criar o banco de dados e rodar as migrations](#como-criar-o-banco-de-dados-e-rodar-as-migrations)
- [Como iniciar a aplicação](#como-iniciar-a-aplicação)
- [Como acessar a documentação da API (Swagger)](#como-acessar-a-documentação-da-api-swagger)
- [Usuário de teste (seed)](#usuário-de-teste-seed)
- [Como testar a API (coleção Postman)](#como-testar-a-api-coleção-postman)
- [Arquitetura do projeto](#arquitetura-do-projeto)
- [Decisões técnicas relevantes](#decisões-técnicas-relevantes)

---

## Requisitos

- **Java 21** (JDK)
- **Maven** (o projeto já inclui o Maven Wrapper, `./mvnw`, então não é necessário instalar o Maven manualmente)
- **PostgreSQL 16+** instalado e em execução localmente
- **Postman** ou **Insomnia** (para testar os endpoints)

---

## Como configurar as variáveis de ambiente

O projeto utiliza variáveis de ambiente para manter credenciais fora do código-fonte versionado. Elas são carregadas a partir de um arquivo `.env` na raiz do projeto, através da dependência `spring-dotenv`.

1. Copie o arquivo de exemplo:
   ```bash
   cp .env.example .env
   ```

2. Preencha o `.env` com os valores reais:
   ```env
   DB_USERNAME=raizes_user
   DB_PASSWORD=root
   JWT_SECRET=Lo7Qy6juzHrV3HdQVtC+KQQEhhYtxi21nlKlOS89JsI=
   JWT_EXPIRATION=3600000
   ```

   - `DB_USERNAME` / `DB_PASSWORD`: credenciais do usuário PostgreSQL criado para o projeto (ver seção seguinte).
   - `JWT_SECRET`: chave secreta usada para assinar os tokens JWT (deve ser uma string longa em Base64).
   - `JWT_EXPIRATION`: tempo de expiração do token, em milissegundos (3600000 = 1 hora).

> **Nota:** o arquivo `.env` está listado no `.gitignore` e nunca é versionado. Apenas o `.env.example` (sem valores reais) faz parte do repositório.

### Observação sobre o carregamento do `.env`

O projeto utiliza a biblioteca `spring-dotenv` na versão **5.0.1**. Versões mais recentes dessa biblioteca apresentaram incompatibilidade com o Spring Boot 4.1.0 (a variável não era resolvida corretamente e a aplicação falhava ao subir). Caso o `.env` não seja carregado automaticamente, é possível exportar as variáveis manualmente antes de iniciar a aplicação:

```bash
export DB_USERNAME=raizes_user
export DB_PASSWORD=root
export JWT_SECRET=Lo7Qy6juzHrV3HdQVtC+KQQEhhYtxi21nlKlOS89JsI=
export JWT_EXPIRATION=3600000
```

---

## Como instalar as dependências

O projeto usa Maven. As dependências são baixadas automaticamente ao compilar ou rodar o projeto — não é necessário nenhum passo manual além de ter conexão com a internet na primeira execução.

Para apenas baixar as dependências e compilar, sem subir a aplicação:

```bash
./mvnw clean install
```

---

## Como criar o banco de dados e rodar as migrations

### 1. Instalar o PostgreSQL (caso ainda não tenha)

```bash
sudo apt update
sudo apt install postgresql postgresql-contrib
sudo systemctl start postgresql
sudo systemctl enable postgresql
```

### 2. Criar o usuário e o banco de dados

Acesse o terminal do PostgreSQL:

```bash
sudo -u postgres psql
```

Dentro do `psql`, execute:

```sql
CREATE USER raizes_user WITH PASSWORD 'root';
CREATE DATABASE raizesdonordeste OWNER raizes_user;
GRANT ALL PRIVILEGES ON DATABASE raizesdonordeste TO raizes_user;
\q
```

### 3. Rodar as migrations

As migrations são gerenciadas pelo **Flyway** e rodam **automaticamente** assim que a aplicação é iniciada — não é necessário nenhum comando manual. Elas estão localizadas em `src/main/resources/db/migration`:

- `V1__criacao_schema_inicial.sql` — cria todas as tabelas do sistema (usuário, unidade, produto, produto_unidade, pedido, item_pedido, estoque, pagamento, log_auditoria).
- `V2__seed_usuario_gerente.sql` — insere um usuário com perfil `GERENTE` para fins de teste (ver seção [Usuário de teste](#usuário-de-teste-seed)).

Ao iniciar a aplicação pela primeira vez, o Flyway detecta as migrations pendentes e as aplica automaticamente, criando o schema completo no banco `raizesdonordeste`.

---

## Como iniciar a aplicação

Com o PostgreSQL em execução e as variáveis de ambiente configuradas:

```bash
./mvnw spring-boot:run
```

A aplicação sobe por padrão na porta **8080**. Ao final da inicialização, o console deve exibir:

```
Started RaizesdonordesteApplication in X.XXX seconds
```

---

## Como acessar a documentação da API (Swagger)

Com a aplicação em execução, acesse:

```
http://localhost:8080/swagger-ui.html
```

A documentação é gerada automaticamente (via `springdoc-openapi`) a partir dos Controllers e DTOs do projeto, listando todos os endpoints disponíveis, seus parâmetros, contratos de request/response e códigos de status.

---

## Usuário de teste (seed)

Para permitir o teste imediato dos endpoints restritos ao perfil `GERENTE` (cadastro de unidade, produto, vínculo produto-unidade e movimentação de estoque), a migration `V2` já insere um usuário pronto para uso:

| Campo   | Valor                |
|---------|-----------------------|
| E-mail  | `gerente@teste.com`   |
| Senha   | `123456`              |
| Perfil  | `GERENTE`              |

Para autenticar com esse usuário:

```
POST /auth/login
Content-Type: application/json

{
  "email": "gerente@teste.com",
  "senha": "123456"
}
```

O `accessToken` retornado deve ser usado no header `Authorization: Bearer <token>` das requisições protegidas.

Para testar como cliente comum, é possível se cadastrar livremente através de:

```
POST /auth/cadastro
```

(todo cadastro público é criado automaticamente com perfil `CLIENTE`, por decisão de segurança — ver seção de [decisões técnicas](#decisões-técnicas-relevantes))

---

## Como testar a API (coleção Postman)

A coleção completa de testes está disponível em:

```
docs/postman/raizes-do-nordeste-api.postman_collection.json
```

Ela está organizada nas seguintes pastas, cobrindo cenários positivos e negativos:

- **Auth** — cadastro e login (cliente e gerente)
- **Unidades** — criação, incluindo cenários de erro (sem token, sem permissão)
- **Produtos** — criação, listagem, validação de dados
- **ProdutoUnidade** — vínculo de produto a unidade (cardápio por unidade)
- **Estoque** — entrada de estoque, consulta de saldo
- **Pedidos** — criação, listagem com filtro por canal, atualização de status (incluindo transições inválidas), estoque insuficiente
- **Pagamento** — processamento do pagamento mock (aprovado e recusado)
- **Erros** — cenários de segurança transversais (401, 403)

### Como importar e executar

1. Abra o Postman.
2. Clique em **Import** e selecione o arquivo `docs/postman/raizes-do-nordeste-api.postman_collection.json`.
3. Execute a pasta **Auth** primeiro (login como gerente e como cliente) para obter os tokens.
4. Nas demais requisições, defina o token obtido na aba **Authorization → Bearer Token**.
5. Siga a ordem sugerida: Unidades → Produtos → ProdutoUnidade → Estoque → Pedidos → Pagamento, já que os testes seguintes dependem de dados criados nos anteriores (IDs de unidade e produto).

> Alguns cenários (como pagamento recusado) dependem de uma simulação aleatória (80% de aprovação / 20% de recusa) — pode ser necessário executar a requisição mais de uma vez para reproduzir cada resultado.

---

## Arquitetura do projeto

O projeto segue uma organização em camadas, inspirada em Clean Architecture simplificada:

```
com.raizes.raizesdonordeste
├── domain
│   ├── model       → entidades JPA (Usuario, Pedido, Produto, Estoque, ...)
│   └── enums       → enums de domínio (CanalPedido, StatusPedido, ...)
├── application
│   ├── service     → regras de negócio e orquestração (PedidoService, AuthService, ...)
│   ├── dto         → objetos de request/response
│   └── exception   → exceções de negócio customizadas
├── infrastructure
│   ├── repository  → interfaces JpaRepository
│   └── security    → JWT, filtros, configuração do Spring Security
└── api
    └── controller  → endpoints REST
```

- **Domain**: entidades e enums, sem dependência de nenhuma outra camada.
- **Application**: regras de negócio (Services), contratos de entrada/saída (DTOs) e exceções de domínio.
- **Infrastructure**: acesso a dados (Repositories) e segurança (JWT, Spring Security).
- **API**: Controllers REST, responsáveis apenas por receber requisições e delegar para os Services.

---

## Decisões técnicas relevantes

Esta seção documenta decisões de projeto tomadas ao longo do desenvolvimento, incluindo pontos que ficaram fora do escopo implementado.

- **Cadastro público sempre cria perfil `CLIENTE`.** O DTO de cadastro não aceita o campo `perfil` vindo do cliente — isso evita que qualquer pessoa se autocadastre como `GERENTE` via API. Perfis administrativos devem ser criados por outro meio (seed, ou endpoint futuro restrito a administradores).
- **`clienteId` do pedido não vem no corpo da requisição.** Diferente do exemplo do roteiro, o cliente do pedido é extraído do usuário autenticado (via token JWT), não do JSON enviado — isso evita que um usuário crie pedidos em nome de outro.
- **Relação Pedido–Pagamento é 1:N**, não 1:1, permitindo múltiplas tentativas de pagamento por pedido (relevante no cenário de recusa seguida de nova tentativa).
- **Estoque é modelado como histórico de movimentações** (entrada/saída), não como um contador de saldo direto. O saldo disponível é sempre calculado a partir da soma dessas movimentações.
- **Preço do item de pedido é "congelado"** no momento da criação (copiado de `ProdutoUnidade`), e não recalculado a partir do preço atual do produto — evita inconsistência caso o preço mude após o pedido.
- **Transição de status do pedido segue uma máquina de estados simples**: `AGUARDANDO_PAGAMENTO → EM_PREPARO → PRONTO → ENTREGUE`, com `CANCELADO` possível a partir de qualquer estado não-final. Transições fora dessa ordem são bloqueadas (HTTP 409).
- **Fidelização e promoções/campanhas** foram tratadas apenas em nível conceitual nos requisitos e não foram implementadas no código, por não fazerem parte do fluxo crítico escolhido (Fluxo A: Pedido → Pagamento mock → Atualização de status). Ficam registradas como proposta para trabalho futuro.
- **Cadastro de usuário administrativo (Atendente, Cozinha, Gerente)** não possui endpoint próprio nesta entrega — o único usuário desses perfis disponível é o inserido via seed (migration `V2`), usado para fins de teste.
- **Contas de usuário não possuem lógica de expiração ou bloqueio** (`isAccountNonExpired`, `isAccountNonLocked`, `isCredentialsNonExpired`, `isEnabled` retornam sempre `true`) — simplificação consciente, fora do escopo de segurança avançada exigido.
- **Logs de auditoria são gravados, mas não expostos via endpoint de consulta** nesta entrega — a tabela `log_auditoria` e a entidade correspondente existem no schema, mas não há Controller/Service dedicados à sua consulta.
