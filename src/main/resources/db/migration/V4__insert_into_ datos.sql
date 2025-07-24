-- Usuarios
INSERT INTO usuarios (nombre, email, contrasena) VALUES
('Ana Pérez', 'ana@example.com', '1234'),
('Luis Gómez', 'luis@example.com', 'abcd');

-- Cursos
INSERT INTO cursos (nombre, categoria) VALUES
('Java Backend', 'Programación'),
('Spring Boot', 'Framework');

-- Tópicos
INSERT INTO topicos (titulo, mensaje, fecha_creacion, status, autor_id, curso_id) VALUES
('¿Cómo usar JPA?', 'Tengo dudas con las anotaciones.', NOW(), 'ABIERTO', 1, 1),
('Error con Spring Boot DevTools', 'No me reinicia la app automáticamente.', NOW(), 'ABIERTO', 2, 2);
