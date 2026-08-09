CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE usuario (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    data_cadastro TIMESTAMP,
    perfil VARCHAR(50),
    senha_hash VARCHAR(255)
);

CREATE TABLE unidade (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nome VARCHAR(255) NOT NULL,
    endereco VARCHAR(255) NOT NULL,
    ativa BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE produto (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nome VARCHAR(255) NOT NULL,
    categoria VARCHAR(255),
    preco_base NUMERIC(10,2) NOT NULL
);

CREATE TABLE produto_unidade (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    produto_id UUID REFERENCES produto(id),
    unidade_id UUID REFERENCES unidade(id),
    disponivel BOOLEAN NOT NULL DEFAULT TRUE,
    preco NUMERIC(10,2) NOT NULL
);

CREATE TABLE pedido (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    cliente_id UUID REFERENCES usuario(id),
    unidade_id UUID REFERENCES unidade(id),
    canal_pedido VARCHAR(50) NOT NULL,
    status_pedido VARCHAR(50) DEFAULT 'AGUARDANDO_PAGAMENTO',
    total NUMERIC(10,2),
    created_at TIMESTAMP
);

CREATE TABLE item_pedido (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    pedido_id UUID REFERENCES pedido(id),
    produto_id UUID REFERENCES produto(id),
    quantidade INTEGER NOT NULL,
    preco_unitario NUMERIC(10,2)
);

CREATE TABLE estoque (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    produto_id UUID REFERENCES produto(id),
    unidade_id UUID REFERENCES unidade(id),
    tipo_movimentacao_estoque VARCHAR(50) NOT NULL,
    quantidade INTEGER NOT NULL,
    data_movimentacao TIMESTAMP
);

CREATE TABLE pagamento (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    pedido_id UUID REFERENCES pedido(id),
    status_pagamento VARCHAR(50) NOT NULL DEFAULT 'PENDENTE',
    valor_pago NUMERIC(10,2),
    data_pagamento TIMESTAMP,
    payload TEXT
);

CREATE TABLE log_auditoria (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    usuario_id UUID REFERENCES usuario(id),
    acao VARCHAR(255) NOT NULL,
    entidade_afetada VARCHAR(255),
    entidade_id UUID,
    data_hora TIMESTAMP
);