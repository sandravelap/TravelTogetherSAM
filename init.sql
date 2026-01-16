-- 1. Creación de la base de datos
DROP DATABASE IF EXISTS traveltogether;
CREATE DATABASE traveltogether CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;
USE traveltogether;

-- 2. Creación de la tabla Usuario
CREATE TABLE Usuario (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         alias VARCHAR(50) NOT NULL UNIQUE,
                         nombre VARCHAR(100) NOT NULL,
                         correo VARCHAR(100) NOT NULL UNIQUE,
                         pass VARCHAR(255) NOT NULL,
                         tabaco ENUM('FUMADOR', 'TOLERANTE', 'INTOLERANTE'),
                         mascota ENUM('COMPAÑÍA', 'ASISTENCIA', 'TOLERANTE', 'INTOLERANTE'),

    -- Restricción para validar formato de correo
                         CHECK (correo LIKE '%@%')
);

-- Índice para optimizar búsqueda por alias
CREATE INDEX idx_usuario_alias ON Usuario (alias);


-- 3. Creación de la tabla Viaje
CREATE TABLE Viaje (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       idCreador INT NOT NULL,
                       nombre VARCHAR(100) NOT NULL,
                       descripcion TEXT,
                       participantes INT NOT NULL,
                       fechaInicio DATE NOT NULL,
                       fechaFin DATE NOT NULL,
                       tabaco BOOLEAN NOT NULL,
                       mascota ENUM('TODAS', 'ASISTENCIA', 'NO') NOT NULL,

    -- Clave Foránea a Usuario
                       FOREIGN KEY (idCreador) REFERENCES Usuario(id) ON DELETE CASCADE,

    -- Restricciones de fechas
                       CHECK (fechaFin > fechaInicio)    -- Fecha de fin posterior a fecha de inicio
);

-- Índice para optimizar búsqueda por idCreador
CREATE INDEX idx_viaje_creador ON Viaje (idCreador);


-- 4. Creación de la tabla Destino
CREATE TABLE Destino (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         nombre VARCHAR(100) NOT NULL,
                         descripcion TEXT,
                         coordX DECIMAL(10, 8) NOT NULL, -- Coordenada X con 8 decimales
                         coordY DECIMAL(10, 8) NOT NULL, -- Coordenada Y con 8 decimales
                         dificultad TINYINT NOT NULL,

    -- Restricción de dificultad
                         CHECK (dificultad BETWEEN 0 AND 5)
);

-- Índice para optimizar búsqueda por nombre
CREATE INDEX idx_destino_nombre ON Destino (nombre);


-- 5. Creación de la tabla Participación (Relación N:M)
CREATE TABLE Participacion (
                               id INT AUTO_INCREMENT PRIMARY KEY,
                               idUsuario INT NOT NULL,
                               idViaje INT NOT NULL,

    -- Claves Foráneas
                               FOREIGN KEY (idUsuario) REFERENCES Usuario(id) ON DELETE CASCADE,
                               FOREIGN KEY (idViaje) REFERENCES Viaje(id) ON DELETE CASCADE,

    -- Restricción de unicidad para la combinación Usuario-Viaje
                               UNIQUE (idUsuario, idViaje)
);

-- Índices para optimizar la búsqueda
CREATE INDEX idx_participacion_usuario ON Participacion (idUsuario);
CREATE INDEX idx_participacion_viaje ON Participacion (idViaje);


-- 6. Creación de la tabla Etapa
CREATE TABLE Etapa (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       idViaje INT NOT NULL,
                       idDestino INT NOT NULL,
                       horaInicio TIME NOT NULL,
                       duracion INT NOT NULL, -- Duración en minutos

    -- Claves Foráneas
                       FOREIGN KEY (idViaje) REFERENCES Viaje(id) ON DELETE CASCADE,
                       FOREIGN KEY (idDestino) REFERENCES Destino(id) ON DELETE CASCADE
);

-- Índices para optimizar la búsqueda
CREATE INDEX idx_etapa_viaje ON Etapa (idViaje);
CREATE INDEX idx_etapa_destino ON Etapa (idDestino);


-- 7. Creación de la tabla Recomendación
CREATE TABLE Recomendacion (
                               id INT AUTO_INCREMENT PRIMARY KEY,
                               nombre VARCHAR(100) NOT NULL,
                               descuento DECIMAL(5, 2) NOT NULL,
                               idDestino INT NOT NULL,

    -- Clave Foránea
                               FOREIGN KEY (idDestino) REFERENCES Destino(id) ON DELETE CASCADE,

    -- Restricción de descuento
                               CHECK (descuento BETWEEN 0 AND 100)
);

-- Índice para optimizar la búsqueda por destino
CREATE INDEX idx_recomendacion_destino ON Recomendacion (idDestino);


-- 8. Creación del TRIGGER
-- El trigger inserta automáticamente al creador del viaje como participante.
DELIMITER $$
CREATE TRIGGER trg_participacion_creador
    AFTER INSERT ON Viaje
    FOR EACH ROW
BEGIN
    INSERT INTO Participacion (idUsuario, idViaje)
    VALUES (NEW.idCreador, NEW.id);
END$$
DELIMITER ;

-- 2. Trigger para validar la fecha de inicio (antes de la inserción/actualización)
DELIMITER $$
CREATE TRIGGER trg_validar_fecha_inicio_viaje
    BEFORE INSERT ON Viaje
    FOR EACH ROW
BEGIN
    IF NEW.fechaInicio <= CURDATE() THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'La fecha de inicio del viaje debe ser posterior a la fecha actual.';
    END IF;
END$$
DELIMITER ;

-- INSERTS PARA LA BASE DE DATOS TRAVELTOGETHER

-- 1. INSERT de Usuarios
INSERT INTO Usuario (alias, nombre, correo, pass, tabaco, mascota) VALUES
                                                                       ('viajero1', 'Carlos Rodríguez', 'carlos.rodriguez@email.com', SHA2('password123', 256), 'FUMADOR', 'COMPAÑÍA'),
                                                                       ('mariag', 'María González', 'maria.gonzalez@email.com', SHA2('securepass456', 256), 'TOLERANTE', 'ASISTENCIA'),
                                                                       ('pablo_v', 'Pablo Vélez', 'pablo.velez@email.com', SHA2('pablo2024', 256), 'INTOLERANTE', 'INTOLERANTE'),
                                                                       ('laura_t', 'Laura Torres', 'laura.torres@email.com', SHA2('laura789', 256), 'FUMADOR', 'TOLERANTE'),
                                                                       ('ana_m', 'Ana Martínez', 'ana.martinez@email.com', SHA2('anaPass!23', 256), NULL, NULL),
                                                                       ('juanito', 'Juan Pérez', 'juan.perez@email.com', SHA2('juanito456', 256), 'TOLERANTE', 'COMPAÑÍA'),
                                                                       ('sofi_a', 'Sofía Alonso', 'sofia.alonso@email.com', SHA2('sofiaSecure', 256), NULL, 'ASISTENCIA'),
                                                                       ('miguel92', 'Miguel Sánchez', 'miguel.sanchez@email.com', SHA2('miguel92pass', 256), 'INTOLERANTE', 'INTOLERANTE'),
                                                                       ('claudia_r', 'Claudia Ruiz', 'claudia.ruiz@email.com', SHA2('claudia2024', 256), 'FUMADOR', 'TOLERANTE'),
                                                                       ('david_m', 'David Morales', 'david.morales@email.com', SHA2('davidPass', 256), 'TOLERANTE', NULL);

-- 2. INSERT de Destinos (con coordenadas reales aproximadas)
INSERT INTO Destino (nombre, descripcion, coordX, coordY, dificultad) VALUES
                                                                          ('Picos de Europa', 'Parque Nacional en los Picos de Europa, ideal para senderismo', 43.189094, -4.820179, 3),
                                                                          ('Playa de la Concha', 'Famosa playa urbana en San Sebastián', 43.318334, -1.981231, 0),
                                                                          ('Alhambra de Granada', 'Palacio y fortaleza andalusí en Granada', 37.176744, -3.589901, 1),
                                                                          ('Teide', 'Volcán y Parque Nacional en Tenerife', 28.272951, -16.642788, 4),
                                                                          ('Cabo de Gata', 'Parque Natural con playas vírgenes en Almería', 36.723167, -2.191969, 2),
                                                                          ('Rías Baixas', 'Rías gallegas con excelente gastronomía y paisajes', 42.515760, -8.783535, 1),
                                                                          ('Sierra Nevada', 'Estación de esquí y montaña en Granada', 37.095169, -3.398192, 3),
                                                                          ('Camino de Santiago', 'Ruta de peregrinación desde Roncesvalles', 42.669149, -1.271104, 2),
                                                                          ('Ibiza', 'Isla famosa por sus playas y vida nocturna', 38.906733, 1.420598, 1),
                                                                          ('Covadonga', 'Santuario y Lagos de Covadonga en Asturias', 43.309883, -5.056594, 2);

-- 3. INSERT de Viajes (fechas futuras para pasar la validación del trigger)
-- Para probar, ajusta CURDATE() si es necesario
INSERT INTO Viaje (idCreador, nombre, descripcion, participantes, fechaInicio, fechaFin, tabaco, mascota) VALUES
                                                                                                              (1, 'Ruta por los Picos', 'Excursión de 3 días por los Picos de Europa', 6, DATE_ADD(CURDATE(), INTERVAL 10 DAY), DATE_ADD(CURDATE(), INTERVAL 12 DAY), 1, 'TODAS'),
                                                                                                              (2, 'Fin de semana en Granada', 'Visita a la Alhambra y Sierra Nevada', 4, DATE_ADD(CURDATE(), INTERVAL 15 DAY), DATE_ADD(CURDATE(), INTERVAL 17 DAY), 0, 'ASISTENCIA'),
                                                                                                              (3, 'Aventura en el Teide', 'Ascensión al Teide y observación astronómica', 8, DATE_ADD(CURDATE(), INTERVAL 20 DAY), DATE_ADD(CURDATE(), INTERVAL 25 DAY), 0, 'NO'),
                                                                                                              (4, 'Ruta del Camino', 'Etapa del Camino de Santiago de 5 días', 10, DATE_ADD(CURDATE(), INTERVAL 30 DAY), DATE_ADD(CURDATE(), INTERVAL 34 DAY), 1, 'TODAS'),
                                                                                                              (5, 'Relax en Cabo de Gata', 'Vacaciones de playa y senderismo suave', 5, DATE_ADD(CURDATE(), INTERVAL 25 DAY), DATE_ADD(CURDATE(), INTERVAL 30 DAY), 0, 'ASISTENCIA'),
                                                                                                              (1, 'Escapada a Ibiza', 'Puente de 4 días en Ibiza', 6, DATE_ADD(CURDATE(), INTERVAL 40 DAY), DATE_ADD(CURDATE(), INTERVAL 43 DAY), 1, 'NO'),
                                                                                                              (6, 'Rías Gallegas', 'Tour gastronómico por las Rías Baixas', 8, DATE_ADD(CURDATE(), INTERVAL 50 DAY), DATE_ADD(CURDATE(), INTERVAL 54 DAY), 0, 'TODAS'),
                                                                                                              (7, 'Nieve en Sierra Nevada', 'Fin de semana de esquí', 6, DATE_ADD(CURDATE(), INTERVAL 60 DAY), DATE_ADD(CURDATE(), INTERVAL 62 DAY), 0, 'ASISTENCIA'),
                                                                                                              (8, 'Asturias Verde', 'Ruta por Covadonga y los lagos', 4, DATE_ADD(CURDATE(), INTERVAL 45 DAY), DATE_ADD(CURDATE(), INTERVAL 47 DAY), 1, 'TODAS'),
                                                                                                              (9, 'San Sebastián gourmet', 'Viaje gastronómico a Donostia', 4, DATE_ADD(CURDATE(), INTERVAL 35 DAY), DATE_ADD(CURDATE(), INTERVAL 37 DAY), 0, 'NO');

-- 4. INSERT de Participaciones adicionales (los creadores ya están insertados por el trigger)
INSERT INTO Participacion (idUsuario, idViaje) VALUES
                                                   (2, 1), (3, 1), (4, 1),  -- Viaje 1 (Picos de Europa)
                                                   (1, 2), (3, 2), (5, 2),  -- Viaje 2 (Granada)
                                                   (1, 3), (2, 3), (4, 3), (6, 3),  -- Viaje 3 (Teide)
                                                   (2, 4), (3, 4), (6, 4), (7, 4), (8, 4),  -- Viaje 4 (Camino)
                                                   (1, 5), (3, 5), (6, 5),  -- Viaje 5 (Cabo de Gata)
                                                   (2, 6), (4, 6), (5, 6),  -- Viaje 6 (Ibiza)
                                                   (1, 7), (2, 7), (4, 7), (5, 7),  -- Viaje 7 (Rías)
                                                   (1, 8), (2, 8), (3, 8), (5, 8),  -- Viaje 8 (Sierra Nevada)
                                                   (1, 9), (2, 9), (5, 9),  -- Viaje 9 (Asturias)
                                                   (1, 10), (3, 10), (4, 10);  -- Viaje 10 (San Sebastián)

-- 5. INSERT de Etapas para los viajes
INSERT INTO Etapa (idViaje, idDestino, horaInicio, duracion) VALUES
-- Etapas para Viaje 1 (Picos de Europa)
(1, 1, '09:00:00', 300),  -- 5 horas de senderismo
(1, 8, '14:00:00', 180),  -- 3 horas visita cultural

-- Etapas para Viaje 2 (Granada)
(2, 3, '10:00:00', 240),  -- 4 horas en la Alhambra
(2, 7, '09:00:00', 360),  -- 6 horas en Sierra Nevada

-- Etapas para Viaje 3 (Teide)
(3, 4, '06:00:00', 480),  -- 8 horas de ascensión

-- Etapas para Viaje 4 (Camino de Santiago)
(4, 8, '08:00:00', 360),  -- 6 horas caminando
(4, 6, '15:00:00', 120),  -- 2 horas visita

-- Etapas para Viaje 5 (Cabo de Gata)
(5, 5, '10:00:00', 240),  -- 4 horas playa/senderismo

-- Etapas para Viaje 6 (Ibiza)
(6, 9, '11:00:00', 180),  -- 3 horas playa

-- Etapas para Viaje 7 (Rías Gallegas)
(7, 6, '10:00:00', 240),  -- 4 horas tour gastronómico

-- Etapas para Viaje 8 (Sierra Nevada)
(8, 7, '09:00:00', 480),  -- 8 horas de esquí

-- Etapas para Viaje 9 (Asturias)
(9, 10, '10:00:00', 300),  -- 5 horas en Covadonga

-- Etapas para Viaje 10 (San Sebastián)
(10, 2, '11:00:00', 240),  -- 4 horas en la playa
(10, 6, '20:00:00', 180);  -- 3 horas cena pintxos

-- 6. INSERT de Recomendaciones
INSERT INTO Recomendacion (nombre, descuento, idDestino) VALUES
                                                             ('Albergue del Peregrino', 15.50, 8),
                                                             ('Restaurante La Marina', 20.00, 6),
                                                             ('Guía de montaña certificado', 10.00, 1),
                                                             ('Tour Alhambra nocturna', 25.00, 3),
                                                             ('Alquiler equipo esquí', 30.00, 7),
                                                             ('Hotel Playa Concha', 15.00, 2),
                                                             ('Excursión en barco', 18.50, 5),
                                                             ('Observatorio astronómico', 12.00, 4),
                                                             ('Spa y wellness', 22.00, 9),
                                                             ('Visita guiada Covadonga', 10.00, 10);

-- CONSULTAS DE VERIFICACIÓN
SELECT '=== VERIFICACIÓN DE DATOS INSERTADOS ===' AS Info;

-- Verificar cantidad de registros por tabla
SELECT 'Usuarios' AS Tabla, COUNT(*) AS Cantidad FROM Usuario
UNION ALL
SELECT 'Destinos', COUNT(*) FROM Destino
UNION ALL
SELECT 'Viajes', COUNT(*) FROM Viaje
UNION ALL
SELECT 'Participaciones', COUNT(*) FROM Participacion
UNION ALL
SELECT 'Etapas', COUNT(*) FROM Etapa
UNION ALL
SELECT 'Recomendaciones', COUNT(*) FROM Recomendacion;

-- Mostrar algunos datos de ejemplo
SELECT '=== USUARIOS (primeros 3) ===' AS Info;
SELECT id, alias, nombre, tabaco, mascota FROM Usuario LIMIT 3;

SELECT '=== VIAJES PROGRAMADOS ===' AS Info;
SELECT v.id, v.nombre, u.alias AS Creador, v.fechaInicio, v.fechaFin, v.participantes
FROM Viaje v
         JOIN Usuario u ON v.idCreador = u.id
ORDER BY v.fechaInicio;

SELECT '=== PARTICIPACIONES POR VIAJE ===' AS Info;
SELECT v.nombre AS Viaje, COUNT(p.idUsuario) AS Participantes
FROM Viaje v
         LEFT JOIN Participacion p ON v.id = p.idViaje
GROUP BY v.id, v.nombre
ORDER BY Participantes DESC;

SELECT '=== DESTINOS CON RECOMENDACIONES ===' AS Info;
SELECT d.nombre AS Destino, r.nombre AS Recomendacion, r.descuento
FROM Destino d
         JOIN Recomendacion r ON d.id = r.idDestino
ORDER BY d.nombre;

-- Verificar que el trigger funcionó correctamente
SELECT '=== VERIFICACIÓN TRIGGER (creadores como participantes) ===' AS Info;
SELECT v.id AS ViajeID, v.nombre AS Viaje,
       u.id AS CreadorID, u.alias AS Creador,
       CASE WHEN p.id IS NOT NULL THEN 'SÍ' ELSE 'NO' END AS Participante
FROM Viaje v
         JOIN Usuario u ON v.idCreador = u.id
         LEFT JOIN Participacion p ON v.id = p.idViaje AND u.id = p.idUsuario
ORDER BY v.id;