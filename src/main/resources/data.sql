INSERT INTO dieta(nombre_dieta, descripcion_dieta) VALUES('dieta 1', 'desc 1');
INSERT INTO dieta(nombre_dieta, descripcion_dieta) VALUES('dieta 2', 'desc 2');
INSERT INTO dieta(nombre_dieta, descripcion_dieta) VALUES('dieta 3', 'desc 3');

INSERT INTO entrenamiento(nombre_entrenamiento, descripcion_entrenamiento) VALUES('entrenamiento 1', 'desc 1');
INSERT INTO entrenamiento(nombre_entrenamiento, descripcion_entrenamiento) VALUES('entrenamiento 2', 'desc 2');
INSERT INTO entrenamiento(nombre_entrenamiento, descripcion_entrenamiento) VALUES('entrenamiento 3', 'desc 3');

INSERT INTO plan(descripcion, FK_DIETA, FK_ENTRENAMIENTO) VALUES ('desc 1', '2', '3');
INSERT INTO plan(descripcion, FK_DIETA, FK_ENTRENAMIENTO) VALUES ('desc 2', '1', '1');
INSERT INTO plan(descripcion, FK_DIETA, FK_ENTRENAMIENTO) VALUES ('desc 3', '3', '2');

INSERT INTO usuario(nombre, email, contraseña) VALUES('pedro', 'pedrodmm63@educastur.es', '1234');
INSERT INTO usuario(nombre, email, contraseña) VALUES('pelayo', 'pelayord76@educastur.es', '1234');
INSERT INTO usuario(nombre, email, contraseña) VALUES('alejandro', 'alejandrobp11@educastur.es', '1234');

INSERT INTO plan_usuario(plan_id, usuario_id) VALUES('1', '1');
INSERT INTO plan_usuario(plan_id, usuario_id) VALUES('2', '2');
INSERT INTO plan_usuario(plan_id, usuario_id) VALUES('3', '3');