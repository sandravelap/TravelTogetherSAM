/*
Cambios Clave con respecto a la estructura relacional:

Desnormalización: Se ha eliminado la tabla Participacion.
Ahora los participantes son un simple array dentro del documento Viaje.
Esto cumple con la gestión de objetos estructurados.

Anidamiento de Etapas: Etapas dentro de Viaje,
recuperamos toda la información de la ruta en una sola lectura de disco.


Geopespacial: Conversión de las coordenadas X e Y a un objeto GeoJSON (Point),
permite realizar consultas por cercanía en el futuro.

Lógica de Triggers: MongoDB no usa triggers SQL tradicionales para validación simple.
La validación de que la fechaInicio sea posterior a la actual debe gestionarse
ahora en la capa de aplicación o mediante JSON Schema Validation en la colección.

 */

// docker run mongo >> docker run --name mongoTravel -it -p 27018:27017 -e MONGO_INITDB_ROOT_USERNAME="root" -e MONGO_INITDB_ROOT_PASSWORD="root" -d mongo
// Después ejecutar archivo en la flecha verde.

// Seleccionar o crear la base de datos
use('traveltogether');
// db.dropDatabase();

// 1. Colección de Usuarios
db.Usuario.insertMany([
    {
        "_id": 1,
        "alias": "carlos_picos",
        "nombre": "Carlos Rodríguez",
        "correo": "carlos.rodriguez@email.com",
        "pass": "$2b$12$K9R.B8u5fG8...",
        "tabaco": "fumador",
        "mascota": "compañia"
    },
    {
        "_id": 2,
        "alias": "ana_rioja",
        "nombre": "Ana Martínez",
        "correo": "ana.mtz@email.com",
        "pass": "$2b$12$L0X.A7v2hH1...",
        "tabaco": "tolerante",
        "mascota": "tolerante"
    },
    {
        "_id": 3,
        "alias": "javi_montaña",
        "nombre": "Javier López",
        "correo": "javi.lpz@email.com",
        "pass": "$2b$12$M1Y.C3w9jI2...",
        "tabaco": "intolerante",
        "mascota": "intolerante"
    },
    {
        "_id": 4,
        "alias": "marta_senderos",
        "nombre": "Marta Gómez",
        "correo": "marta.g@email.com",
        "pass": "$2b$12$S7E.I9c3pP8...",
        "tabaco": "null",
        "mascota": "compañia"
    },
    {
        "_id": 5,
        "alias": "lucia_furgo",
        "nombre": "Lucía García",
        "correo": "lucia.garcia@email.com",
        "pass": "$2b$12$N2Z.D4x8kJ3...",
        "tabaco": "fumador",
        "mascota": "compañia"
    },
    {
        "_id": 8,
        "alias": "pablo_camino",
        "nombre": "Pablo Herranz",
        "correo": "pablo.h@email.com",
        "pass": "$2b$12$T8F.J0d2qQ9...",
        "tabaco": "tolerante",
        "mascota": "null"
    },
    {
        "_id": 10,
        "alias": "marcos_aneto",
        "nombre": "Marcos Soler",
        "correo": "marcos.soler@email.com",
        "pass": "$2b$12$O3A.E5y7lK4...",
        "tabaco": "intolerante",
        "mascota": "intolerante"
    },
    {
        "_id": 12,
        "alias": "sergio_cadiz",
        "nombre": "Sergio Ruiz",
        "correo": "sergio.ruiz@email.com",
        "pass": "$2b$12$P4B.F6z6mL5...",
        "tabaco": "fumador",
        "mascota": "asistencia"
    },
    {
        "_id": 15,
        "alias": "raquel_winelover",
        "nombre": "Raquel Díaz",
        "correo": "raquel.d@email.com",
        "pass": "$2b$12$U9G.K1e1rR0...",
        "tabaco": "tolerante",
        "mascota": "tolerante"
    },
    {
        "_id": 21,
        "alias": "ivan_alpinista",
        "nombre": "Iván Torres",
        "correo": "ivan.t@email.com",
        "pass": "$2b$12$V0H.L2f0sS1...",
        "tabaco": "intolerante",
        "mascota": "null"
    },
    {
        "_id": 30,
        "alias": "elena_vinitos",
        "nombre": "Elena Sanz",
        "correo": "elena.sanz@email.com",
        "pass": "$2b$12$Q5C.G7a5nM6...",
        "tabaco": "null",
        "mascota": "compañia"
    },
    {
        "_id": 31,
        "alias": "pedro_rioja",
        "nombre": "Pedro Cano",
        "correo": "pedro.c@email.com",
        "pass": "$2b$12$W1I.M3g9tT2...",
        "tabaco": "tolerante",
        "mascota": "tolerante"
    },
    {
        "_id": 32,
        "alias": "sofia_gastronomia",
        "nombre": "Sofía Varga",
        "correo": "sofia.v@email.com",
        "pass": "$2b$12$X2J.N4h8uU3...",
        "tabaco": "fumador",
        "mascota": "compañia"
    },
    {
        "_id": 45,
        "alias": "david_alpin",
        "nombre": "David Peña",
        "correo": "david.p@email.com",
        "pass": "$2b$12$R6D.H8b4oN7...",
        "tabaco": "intolerante",
        "mascota": "intolerante"
    }

    // ... rest of users
]);

// 2. Colección de Destinos (Incluyendo sus recomendaciones anidadas)
db.Destino.insertMany([
    {
        "_id": 1,
        "nombre": "Picos de Europa",
        "descripcion": "Parque Nacional en los Picos de Europa",
        "ubicacion": { "type": "Point", "coordinates": [-4.820179, 43.189094] },
        "dificultad": 3,
        "recomendaciones": [
            { "nombre": "Guía de montaña certificado", "descuento": 10.00 }
        ]
    },
    {
        "_id": 8,
        "nombre": "Camino de Santiago",
        "descripcion": "Ruta de peregrinación desde Roncesvalles",
        "ubicacion": { "type": "Point", "coordinates": [-1.271104, 42.669149] },
        "dificultad": 2,
        "recomendaciones": [
            { "nombre": "Albergue del Peregrino", "descuento": 15.50 }
        ]
    },
    {
        "_id": 15,
        "nombre": "Tarifa",
        "descripcion": "Punto de encuentro entre el Atlántico y el Mediterráneo, ideal para deportes de viento.",
        "ubicacion": { "type": "Point", "coordinates": [-5.6033, 36.0127] },
        "dificultad": 1,
        "recomendaciones": [
            { "nombre": "Curso de iniciación al Kitesurf", "descuento": 15.00 },
            { "nombre": "Alquiler de neopreno", "descuento": 5.00 }
        ]
    },
    {
        "_id": 22,
        "nombre": "Playa de Bolonia",
        "descripcion": "Famosa por su duna gigante y las ruinas romanas de Baelo Claudia.",
        "ubicacion": { "type": "Point", "coordinates": [-5.7738, 36.0889] },
        "dificultad": 1,
        "recomendaciones": [
            { "nombre": "Entrada Museo Baelo Claudia", "descuento": 100.00 },
            { "nombre": "Pack Sombrilla + Hamaca", "descuento": 20.00 }
        ]
    },
    {
        "_id": 102,
        "nombre": "Refugio de la Renclusa",
        "descripcion": "Refugio base emblemático para las ascensiones en el macizo de la Maladeta.",
        "ubicacion": { "type": "Point", "coordinates": [0.6508, 42.6694] },
        "dificultad": 3,
        "recomendaciones": [
            { "nombre": "Media pensión en refugio", "descuento": 5.00 },
            { "nombre": "Mapa cartográfico del sector", "descuento": 10.00 }
        ]
    },
    {
        "_id": 103,
        "nombre": "Cima Aneto",
        "descripcion": "El punto más alto de los Pirineos (3.404m) cruzando el Glaciar y el Paso de Mahoma.",
        "ubicacion": { "type": "Point", "coordinates": [0.6568, 42.6321] },
        "dificultad": 5,
        "recomendaciones": [
            { "nombre": "Alquiler de crampones y piolet", "descuento": 12.00 },
            { "nombre": "Seguro de rescate en montaña", "descuento": 10.00 }
        ]
    },
    {
        "_id": 50,
        "nombre": "Calle Laurel (Logroño)",
        "descripcion": "La zona de pinchos y tapeo más famosa de la capital riojana.",
        "ubicacion": { "type": "Point", "coordinates": [-2.4474, 42.4655] },
        "dificultad": 1,
        "recomendaciones": [
            { "nombre": "Ticket 'Pincho + Corto' x5", "descuento": 15.00 }
        ]
    },
    {
        "_id": 55,
        "nombre": "Bodegas Haro",
        "descripcion": "Visita al Barrio de la Estación, cuna de las bodegas históricas de Rioja.",
        "ubicacion": { "type": "Point", "coordinates": [-2.8475, 42.5833] },
        "dificultad": 1,
        "recomendaciones": [
            { "nombre": "Cata premium de 3 vinos", "descuento": 20.00 },
            { "nombre": "Caja de 6 botellas reserva", "descuento": 10.00 }
        ]
    }
]);

// 3. Colección de Viajes (El "corazón" de la migración)
// En lugar de una tabla Participacion y Etapa, anidamos los arrays.
db.Viaje.insertMany([
    {
        "nombre": "Ruta por los Picos",
        "descripcion": "Excursión de 3 días por los Picos de Europa",
        "idCreador": 1,
        "maxParticipantes": 6,
        "fecha_inicio": ISODate("2026-01-23T09:00:00Z"),
        "fecha_fin": ISODate("2026-01-25T18:00:00Z"),
        "tabaco": true,
        "mascota": "TODAS",
        "participantes": [1, 2, 3, 4], // Array de IDs de usuarios (Referencia)
        "etapas": [ // Documentos anidados (Embedding)
            {
                "idDestino": 1,
                "nombreDestino": "Picos de Europa",
                "horaInicio": "09:00",
                "duracionMinutos": 300
            },
            {
                "idDestino": 8,
                "nombreDestino": "Camino de Santiago",
                "horaInicio": "14:00",
                "duracionMinutos": 180
            }
        ]
    },
    {
        "nombre": "Costa de la Luz en Furgo",
        "descripcion": "Recorrido por las playas de Cádiz y Huelva",
        "idCreador": 5,
        "maxParticipantes": 4,
        "fecha_inicio": ISODate("2026-02-10T10:00:00Z"),
        "fecha_fin": ISODate("2026-02-15T20:00:00Z"),
        "tabaco": true,
        "mascota": "PERROS_PEQUEÑOS",
        "participantes": [5, 12],
        "etapas": [
            {
                "idDestino": 15,
                "nombreDestino": "Tarifa",
                "horaInicio": "10:00",
                "duracionMinutos": 600
            },
            {
                "idDestino": 22,
                "nombreDestino": "Playa de Bolonia",
                "horaInicio": "11:00",
                "duracionMinutos": 240
            }
        ]
    },
    {
        "nombre": "Ascensión al Aneto",
        "descripcion": "Ruta de alta montaña para montañeros experimentados",
        "idCreador": 10,
        "maxParticipantes": 5,
        "fecha_inicio": ISODate("2026-05-15T07:00:00Z"),
        "fecha_fin": ISODate("2026-05-17T16:00:00Z"),
        "tabaco": false,
        "mascota": "NO",
        "participantes": [10, 3, 45, 21],
        "etapas": [
            {
                "idDestino": 102,
                "nombreDestino": "Refugio de la Renclusa",
                "horaInicio": "08:00",
                "duracionMinutos": 420
            },
            {
                "idDestino": 103,
                "nombreDestino": "Cima Aneto",
                "horaInicio": "05:00",
                "duracionMinutos": 540
            }
        ]
    },
    {
        "nombre": "Vinos y Tapas por la Rioja",
        "descripcion": "Fin de semana de enoturismo por Logroño y alrededores",
        "idCreador": 2,
        "maxParticipantes": 10,
        "fecha_inicio": ISODate("2026-03-20T18:00:00Z"),
        "fecha_fin": ISODate("2026-03-22T21:00:00Z"),
        "tabaco": false,
        "mascota": "TODAS",
        "participantes": [2, 8, 15, 30, 31, 32],
        "etapas": [
            {
                "idDestino": 50,
                "nombreDestino": "Calle Laurel (Logroño)",
                "horaInicio": "20:30",
                "duracionMinutos": 180
            },
            {
                "idDestino": 55,
                "nombreDestino": "Bodegas Haro",
                "horaInicio": "11:00",
                "duracionMinutos": 120
            }
        ]
    }
]);

// Creación de índices para optimizar búsquedas [cite: 79]
db.Usuario.createIndex({ "alias": 1 }, { unique: true });
db.Destino.createIndex({ "ubicacion": "2dsphere" }); // Índice geoespacial