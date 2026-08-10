INSERT INTO usuario (
    id,
    nome,
    email,
    data_cadastro,
    perfil,
    senha_hash
)
VALUES (
           gen_random_uuid(),
           'Gerente Teste',
           'gerente@teste.com',
           CURRENT_TIMESTAMP,
           'GERENTE',
           '$2a$10$DGjuHyMxvTw3mat5wn5pP.HphaJTRUxs8xba8Us5qjTQBaTJQrf3O'
       );