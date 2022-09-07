--TABLES

--BOT_USERS
INSERT INTO bot_users (id, chat_id, email, login, domain_name, marked, created_at, updated_at)
VALUES (1, 0, 'apolyashov@spets.ru', 'apolyashov', 'apolyashov', false, '2022-01-01 08:30:00', '2022-01-01 08:30:00'),
        (2, 0, 'support@spets.ru', 'support', 'support', false, '2022-01-01 08:30:00', '2022-01-01 08:30:00'),
        (3, 0, 'user@spets.ru', 'user', 'user', false, '2022-01-01 08:30:00', '2022-01-01 08:30:00');

--ROLES
INSERT INTO roles (id, name, description, marked, created_at, updated_at)
VALUES (1, 'ADMIN', 'Администраторы', false, '2022-01-01 08:30:00', '2022-01-01 08:30:00'),
        (2, 'SUPPORT', 'Пользователи поддержки', false, '2022-01-01 08:30:00', '2022-01-01 08:30:00'),
        (3, 'MANAGER', 'Руководители', false, '2022-01-01 08:30:00', '2022-01-01 08:30:00'),
        (4, 'USER', 'Прочие пользователи', false, '2022-01-01 08:30:00', '2022-01-01 08:30:00');

--USER_ROLES
INSERT INTO user_roles (user_id, role_id)
VALUES (1, 1),(1, 2),(1,3),(1,4),
        (2, 2),
        (3, 4);


--ALLOW_SERVICES
INSERT INTO allow_services (id, name, description, marked, created_at, updated_at )
VALUES (1, 'errors_trade', 'Оповещение об ошибках в торговой базе', false, '2022-01-01 08:30:00', '2022-01-01 08:30:00'),
        (2, 'errors_wms', 'Оповещение об ошибках в системе WMS', false, '2022-01-01 08:30:00', '2022-01-01 08:30:00'),
        (3, 'errors_accounting', 'Оповещение об ошибках в бухгалтерской базе', false, '2022-01-01 08:30:00', '2022-01-01 08:30:00'),
        (4, 'errors_exchange', 'Оповещение об ошибках в системе обмена', false, '2022-01-01 08:30:00', '2022-01-01 08:30:00'),
        (5, 'alerts_system', 'Важные системные оповещения', false, '2022-01-01 08:30:00', '2022-01-01 08:30:00'),
        (6, 'alerts_common', 'Общие оповещения', false, '2022-01-01 08:30:00', '2022-01-01 08:30:00');

--SERVICES_ROLES
INSERT INTO services_roles (service_id, role_id)
VALUES (1, 1),(1, 2),
        (2, 1),(2, 2),
        (3, 1),(3, 2);




