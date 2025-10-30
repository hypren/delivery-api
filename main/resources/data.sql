-- DADOS DE EXEMPLO PARA TESTES

-- Inserir Clientes
-- Senha deve ser criptografada em produção, mas aqui usamos uma simples
INSERT INTO cliente (id, nome, email, telefone, ativo) VALUES (1, 'Ana Silva', 'ana.silva@teste.com', '11987654321', TRUE);
INSERT INTO cliente (id, nome, email, telefone, ativo) VALUES (2, 'Bruno Costa', 'bruno.costa@teste.com', '21999998888', TRUE);
INSERT INTO cliente (id, nome, email, telefone, ativo) VALUES (3, 'Cliente Inativo', 'inativo@teste.com', '3155554444', FALSE);

-- Inserir Restaurantes
INSERT INTO restaurante (id, nome, categoria, endereco, avaliacao, ativo) VALUES (1, 'Hamburgueria Gourmet', 'Lanches', 'Rua Principal, 100', 4.5, TRUE);
INSERT INTO restaurante (id, nome, categoria, endereco, avaliacao, ativo) VALUES (2, 'Sabor Oriental', 'Japonesa', 'Av. Central, 50', 4.8, TRUE);
INSERT INTO restaurante (id, nome, categoria, endereco, avaliacao, ativo) VALUES (3, 'Cantina Italiana', 'Massas', 'Rua B, 20', 3.9, FALSE); -- Inativo

-- Inserir Produtos
-- Produtos do Hamburgueria Gourmet (id=1)
INSERT INTO produto (id, nome, descricao, preco, categoria, disponivel, restaurante_id) VALUES (101, 'X-Bacon Supremo', 'Pão, carne, bacon, queijo', 25.00, 'Sanduíche', TRUE, 1);
INSERT INTO produto (id, nome, descricao, preco, categoria, disponivel, restaurante_id) VALUES (102, 'Batata Frita', 'Porção de batata frita média', 12.00, 'Acompanhamento', TRUE, 1);

-- Produtos do Sabor Oriental (id=2)
INSERT INTO produto (id, nome, descricao, preco, categoria, disponivel, restaurante_id) VALUES (201, 'Combinado Sushi (20p)', 'Salmão, atum, uramaki', 79.90, 'Sushi', TRUE, 2);
INSERT INTO produto (id, nome, descricao, preco, categoria, disponivel, restaurante_id) VALUES (202, 'Temaki Salmão', 'Cone de alga com salmão', 29.50, 'Temaki', TRUE, 2);

-- Inserir Pedidos (Simplificado, sem itens detalhados)
-- Pedido 1: Cliente 1 (Ana Silva) - STATUS: CONCLUIDO
INSERT INTO pedido (id, data_criacao, subtotal, taxa_entrega, valor_total, status, cliente_id) VALUES (1001, CURRENT_TIMESTAMP, 37.00, 5.00, 42.00, 'CONCLUIDO', 1);

-- Pedido 2: Cliente 2 (Bruno Costa) - STATUS: PENDENTE
INSERT INTO pedido (id, data_criacao, subtotal, taxa_entrega, valor_total, status, cliente_id) VALUES (1002, CURRENT_TIMESTAMP, 79.90, 5.00, 84.90, 'PENDENTE', 2);