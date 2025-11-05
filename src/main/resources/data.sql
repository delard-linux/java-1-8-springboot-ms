-- ========================================
-- DATOS DE PRUEBA PARA H2
-- ========================================
-- Este archivo se ejecuta automáticamente al iniciar la aplicación
-- gracias a spring.sql.init.mode=always en application.properties

-- IMPORTANTE: Las tablas se crean automáticamente por Hibernate
-- con spring.jpa.hibernate.ddl-auto=create-drop

-- ========================================
-- EMPRESAS
-- ========================================

INSERT INTO empresas (id, razon_social, cif, email, telefono, sector, fecha_alta, activo, facturacion_anual, numero_empleados)
VALUES (1, 'Tech Solutions S.L.', 'B12345678', 'info@techsolutions.es', '912345678', 'Tecnología', '2020-01-15', true, 5000000.00, 50);

INSERT INTO empresas (id, razon_social, cif, email, telefono, sector, fecha_alta, activo, facturacion_anual, numero_empleados)
VALUES (2, 'Logística Global S.A.', 'A87654321', 'contacto@logisticaglobal.es', '934567890', 'Logística', '2018-03-20', true, 12000000.00, 120);

INSERT INTO empresas (id, razon_social, cif, email, telefono, sector, fecha_alta, activo, facturacion_anual, numero_empleados)
VALUES (3, 'Alimentación Fresca S.L.', 'B11223344', 'ventas@alimfresca.es', '955667788', 'Alimentación', '2019-06-10', true, 3500000.00, 35);

INSERT INTO empresas (id, razon_social, cif, email, telefono, sector, fecha_alta, activo, facturacion_anual, numero_empleados)
VALUES (4, 'Construcciones del Norte S.A.', 'A99887766', 'info@construccionesnorte.es', '944556677', 'Construcción', '2015-09-05', true, 8000000.00, 85);

INSERT INTO empresas (id, razon_social, cif, email, telefono, sector, fecha_alta, activo, facturacion_anual, numero_empleados)
VALUES (5, 'Textil Mediterráneo S.L.', 'B55443322', 'pedidos@textilmed.es', '963332211', 'Textil', '2021-02-28', false, 1500000.00, 20);

-- ========================================
-- SEDES
-- ========================================

-- Sedes de Tech Solutions S.L. (empresa_id = 1)
INSERT INTO sedes (id, nombre, direccion, ciudad, provincia, codigo_postal, pais, telefono, email, es_principal, capacidad_almacenamiento, horario_recepcion, empresa_id)
VALUES (1, 'Sede Central Madrid', 'Calle Gran Vía, 45', 'Madrid', 'Madrid', '28013', 'España', '912345678', 'madrid@techsolutions.es', true, 500.00, 'L-V: 8:00-18:00', 1);

INSERT INTO sedes (id, nombre, direccion, ciudad, provincia, codigo_postal, pais, telefono, email, es_principal, capacidad_almacenamiento, horario_recepcion, empresa_id)
VALUES (2, 'Delegación Barcelona', 'Avenida Diagonal, 123', 'Barcelona', 'Barcelona', '08019', 'España', '933445566', 'barcelona@techsolutions.es', false, 300.00, 'L-V: 9:00-17:00', 1);

INSERT INTO sedes (id, nombre, direccion, ciudad, provincia, codigo_postal, pais, telefono, email, es_principal, capacidad_almacenamiento, horario_recepcion, empresa_id)
VALUES (3, 'Centro I+D Valencia', 'Parque Tecnológico, Edificio 7', 'Valencia', 'Valencia', '46022', 'España', '963778899', 'valencia@techsolutions.es', false, 200.00, 'L-V: 8:00-16:00', 1);

-- Sedes de Logística Global S.A. (empresa_id = 2)
INSERT INTO sedes (id, nombre, direccion, ciudad, provincia, codigo_postal, pais, telefono, email, es_principal, capacidad_almacenamiento, horario_recepcion, empresa_id)
VALUES (4, 'Almacén Central Zaragoza', 'Polígono Industrial Plaza, Nave 15', 'Zaragoza', 'Zaragoza', '50197', 'España', '976112233', 'zaragoza@logisticaglobal.es', true, 5000.00, 'L-D: 24h', 2);

INSERT INTO sedes (id, nombre, direccion, ciudad, provincia, codigo_postal, pais, telefono, email, es_principal, capacidad_almacenamiento, horario_recepcion, empresa_id)
VALUES (5, 'Hub Logístico Sevilla', 'Carretera A-49, Km 5', 'Sevilla', 'Sevilla', '41010', 'España', '954223344', 'sevilla@logisticaglobal.es', false, 3500.00, 'L-D: 24h', 2);

INSERT INTO sedes (id, nombre, direccion, ciudad, provincia, codigo_postal, pais, telefono, email, es_principal, capacidad_almacenamiento, horario_recepcion, empresa_id)
VALUES (6, 'Centro de Distribución Bilbao', 'Zona Franca, Parcela 8', 'Bilbao', 'Vizcaya', '48920', 'España', '944556677', 'bilbao@logisticaglobal.es', false, 2800.00, 'L-S: 6:00-22:00', 2);

-- Sedes de Alimentación Fresca S.L. (empresa_id = 3)
INSERT INTO sedes (id, nombre, direccion, ciudad, provincia, codigo_postal, pais, telefono, email, es_principal, capacidad_almacenamiento, horario_recepcion, empresa_id)
VALUES (7, 'Centro de Producción Murcia', 'Calle Industria, 34', 'Murcia', 'Murcia', '30100', 'España', '968112233', 'murcia@alimfresca.es', true, 1200.00, 'L-V: 7:00-15:00', 3);

INSERT INTO sedes (id, nombre, direccion, ciudad, provincia, codigo_postal, pais, telefono, email, es_principal, capacidad_almacenamiento, horario_recepcion, empresa_id)
VALUES (8, 'Cámara Frigorífica Almería', 'Parque Agroalimentario, Sector 3', 'Almería', 'Almería', '04120', 'España', '950334455', 'almeria@alimfresca.es', false, 800.00, 'L-D: 6:00-14:00', 3);

-- Sedes de Construcciones del Norte S.A. (empresa_id = 4)
INSERT INTO sedes (id, nombre, direccion, ciudad, provincia, codigo_postal, pais, telefono, email, es_principal, capacidad_almacenamiento, horario_recepcion, empresa_id)
VALUES (9, 'Oficinas Centrales A Coruña', 'Avenida de la Marina, 78', 'A Coruña', 'A Coruña', '15001', 'España', '981223344', 'coruña@construccionesnorte.es', true, 150.00, 'L-V: 8:00-18:00', 4);

INSERT INTO sedes (id, nombre, direccion, ciudad, provincia, codigo_postal, pais, telefono, email, es_principal, capacidad_almacenamiento, horario_recepcion, empresa_id)
VALUES (10, 'Almacén de Materiales Vigo', 'Polígono O Porriño, Nave 23', 'Vigo', 'Pontevedra', '36400', 'España', '986445566', 'vigo@construccionesnorte.es', false, 2000.00, 'L-V: 7:00-19:00', 4);

INSERT INTO sedes (id, nombre, direccion, ciudad, provincia, codigo_postal, pais, telefono, email, es_principal, capacidad_almacenamiento, horario_recepcion, empresa_id)
VALUES (11, 'Base de Operaciones Oviedo', 'Calle Uría, 56', 'Oviedo', 'Asturias', '33003', 'España', '985667788', 'oviedo@construccionesnorte.es', false, 400.00, 'L-V: 8:00-17:00', 4);

-- Sedes de Textil Mediterráneo S.L. (empresa_id = 5 - Empresa INACTIVA)
INSERT INTO sedes (id, nombre, direccion, ciudad, provincia, codigo_postal, pais, telefono, email, es_principal, capacidad_almacenamiento, horario_recepcion, empresa_id)
VALUES (12, 'Fábrica Alicante', 'Polígono Industrial Les Atalaies', 'Alicante', 'Alicante', '03114', 'España', '965778899', 'alicante@textilmed.es', true, 900.00, 'L-V: 8:00-16:00', 5);

-- ========================================
-- RESUMEN DE DATOS INSERTADOS
-- ========================================
-- Total Empresas: 5 (4 activas, 1 inactiva)
-- Total Sedes: 12
-- 
-- Distribución de sedes por empresa:
-- - Tech Solutions S.L.: 3 sedes
-- - Logística Global S.A.: 3 sedes
-- - Alimentación Fresca S.L.: 2 sedes
-- - Construcciones del Norte S.A.: 3 sedes
-- - Textil Mediterráneo S.L.: 1 sede

