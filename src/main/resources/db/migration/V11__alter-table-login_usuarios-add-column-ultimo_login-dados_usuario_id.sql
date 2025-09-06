ALTER TABLE login_usuarios
ADD COLUMN ultimo_login TIMESTAMP NOT NULL,
ADD COLUMN dados_usuario_id BIGINT UNIQUE NOT NULL,
ADD CONSTRAINT fk_login_dados
    FOREIGN KEY (dados_usuario_id)
    REFERENCES dados_usuarios(id)
    ON DELETE CASCADE;
