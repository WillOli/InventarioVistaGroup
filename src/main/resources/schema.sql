CREATE TABLE IF NOT EXISTS unidades (
                                        id SERIAL PRIMARY KEY,
                                        nome VARCHAR(100) NOT NULL UNIQUE, -- UNIQUE adicionado para evitar duplicação no INSERT
    endereco VARCHAR(255),
    is_ativo BOOLEAN DEFAULT TRUE,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS categorias (
                                          id SERIAL PRIMARY KEY,
                                          nome VARCHAR(100) NOT NULL UNIQUE, -- UNIQUE adicionado para evitar duplicação no INSERT
    is_ativo BOOLEAN DEFAULT TRUE
    );

CREATE TABLE IF NOT EXISTS itens_inventario (
                                                id BIGSERIAL PRIMARY KEY,
                                                codigo_patrimonio VARCHAR(50) UNIQUE,
    nome VARCHAR(150) NOT NULL,
    descricao TEXT,
    modelo VARCHAR(100),
    codigo_fabricante VARCHAR(100),
    tensao VARCHAR(50),
    peso VARCHAR(50),
    marca VARCHAR(100),
    numero_serie VARCHAR(150),
    localizacao VARCHAR(100),
    preco DECIMAL(10, 2),
    nota_fiscal_url TEXT,
    data_aquisicao DATE,
    observacao TEXT,
    estado VARCHAR(50) DEFAULT 'Em uso',
    foto_url TEXT,

    -- Chaves Estrangeiras (Relacionamentos)
    unidade_id INT REFERENCES unidades(id),
    categoria_id INT REFERENCES categorias(id),

    -- Lógica de Soft Delete e Auditoria
    is_ativo BOOLEAN DEFAULT TRUE,
    data_desativacao TIMESTAMP,
    motivo_desativacao TEXT,

    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS historico_movimentacao (
                                                      id BIGSERIAL PRIMARY KEY,
                                                      item_id BIGINT REFERENCES itens_inventario(id) NOT NULL,
    unidade_origem_id INT REFERENCES unidades(id),
    unidade_destino_id INT REFERENCES unidades(id),
    motivo TEXT NOT NULL,
    data_movimentacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    usuario_responsavel VARCHAR(100)
    );