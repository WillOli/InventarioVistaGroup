-- Inserindo as unidades iniciais (Ignora se já existir)
INSERT INTO unidades (nome, endereco) VALUES
                                          ('Vista Estoril', 'Endereço Estoril'),
                                          ('Vista Savassi', 'Endereço Savassi')
    ON CONFLICT (nome) DO NOTHING;

-- Inserindo categorias baseadas nas abas (Ignora se já existir)
INSERT INTO categorias (nome) VALUES
                                  ('Equip. da Cozinha'),
                                  ('Eletrônicos')
    ON CONFLICT (nome) DO NOTHING;