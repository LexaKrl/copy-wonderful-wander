-- Main test account
INSERT INTO account (user_id, username, email, firstname, lastname, password, bio, role)
VALUES ('550e8400-e29b-41d4-a716-446655440000',
        'john_doe',
        'john.doe@example.com',
        'John',
        'Doe',
        '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG',
        'Love hiking and photography!',
        'ROLE_USER');

-- Auxiliary accounts for checking the logic of relationships
INSERT INTO account (user_id, username, email, firstname, lastname, password, bio, role, photo_visibility,
                     walk_visibility)
VALUES ('11111111-1111-1111-1111-111111111111', 'user1', 'user1@example.com', 'Alice', 'Smith',
        '$2a$10$passwordhashexample1234.AbcdEfghIjklMnopQrstUvwx', 'Bio for user1',
        'ROLE_USER', 'PUBLIC', 'REMEMBER_ONLY'),
       ('22222222-2222-2222-2222-222222222222', 'user2', 'user2@example.com', 'Bob', 'Johnson',
        '$2a$10$passwordhashexample1234.AbcdEfghIjklMnopQrstUvwx', 'Bio for user2',
        'ROLE_USER', 'FRIENDS_ONLY', 'PRIVATE'),
       ('33333333-3333-3333-3333-333333333333', 'user3', 'user3@example.com', 'Charlie', 'Brown',
        '$2a$10$passwordhashexample1234.AbcdEfghIjklMnopQrstUvwx', 'Bio for user3',
        'ROLE_USER', 'PRIVATE', 'FRIENDS_ONLY'),
       ('44444444-4444-4444-4444-444444444444', 'user4', 'user4@example.com', 'David', 'Miller',
        '$2a$10$passwordhashexample1234.AbcdEfghIjklMnopQrstUvwx', 'Bio for user4',
        'ROLE_USER', 'PUBLIC', 'PUBLIC'),
       ('55555555-5555-5555-5555-555555555555', 'user5', 'user5@example.com', 'Eve', 'Davis',
        '$2a$10$passwordhashexample1234.AbcdEfghIjklMnopQrstUvwx', 'Bio for user5',
        'ROLE_USER', 'FRIENDS_ONLY', 'REMEMBER_ONLY'),
       ('66666666-6666-6666-6666-666666666666', 'user6', 'user6@example.com', 'Frank', 'Wilson',
        '$2a$10$passwordhashexample1234.AbcdEfghIjklMnopQrstUvwx', 'Bio for user6',
        'ROLE_USER', 'PRIVATE', 'PRIVATE'),
       ('77777777-7777-7777-7777-777777777777', 'user7', 'user7@example.com', 'Grace', 'Taylor',
        '$2a$10$passwordhashexample1234.AbcdEfghIjklMnopQrstUvwx', 'Bio for user7',
        'ROLE_USER', 'PUBLIC', 'FRIENDS_ONLY'),
       ('88888888-8888-8888-8888-888888888888', 'user8', 'user8@example.com', 'Hannah', 'Clark',
        '$2a$10$passwordhashexample1234.AbcdEfghIjklMnopQrstUvwx', 'Bio for user8',
        'ROLE_USER', 'FRIENDS_ONLY', 'PUBLIC'),
       ('99999999-9999-9999-9999-999999999999', 'user9', 'user9@example.com', 'Ian', 'Lewis',
        '$2a$10$passwordhashexample1234.AbcdEfghIjklMnopQrstUvwx', 'Bio for user9',
        'ROLE_USER', 'PRIVATE', 'REMEMBER_ONLY'),
       ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'user10', 'user10@example.com', 'Julia', 'Walker',
        '$2a$10$passwordhashexample1234.AbcdEfghIjklMnopQrstUvwx', 'Bio for user10',
        'ROLE_USER', 'PUBLIC', 'PRIVATE'),
       ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'user11', 'user11@example.com', 'Kevin', 'Hall',
        '$2a$10$passwordhashexample1234.AbcdEfghIjklMnopQrstUvwx', 'Bio for user11',
        'ROLE_USER', 'FRIENDS_ONLY', 'FRIENDS_ONLY'),
       ('cccccccc-cccc-cccc-cccc-cccccccccccc', 'user12', 'user12@example.com', 'Laura', 'Young',
        '$2a$10$passwordhashexample1234.AbcdEfghIjklMnopQrstUvwx', 'Bio for user12',
        'ROLE_USER', 'PRIVATE', 'PUBLIC'),
       ('dddddddd-dddd-dddd-dddd-dddddddddddd', 'user13', 'user13@example.com', 'Michael', 'King',
        '$2a$10$passwordhashexample1234.AbcdEfghIjklMnopQrstUvwx', 'Bio for user13',
        'ROLE_USER', 'PUBLIC', 'REMEMBER_ONLY'),
       ('eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', 'user14', 'user14@example.com', 'Nina', 'Wright',
        '$2a$10$passwordhashexample1234.AbcdEfghIjklMnopQrstUvwx', 'Bio for user14',
        'ROLE_USER', 'FRIENDS_ONLY', 'PRIVATE'),
       ('ffffffff-ffff-ffff-ffff-ffffffffffff', 'user15', 'user15@example.com', 'Oliver', 'Scott',
        '$2a$10$passwordhashexample1234.AbcdEfghIjklMnopQrstUvwx', 'Bio for user15',
        'ROLE_USER', 'PRIVATE', 'FRIENDS_ONLY'),
       ('12345678-1234-1234-1234-123456789abc', 'user16', 'user16@example.com', 'Peter', 'Evans',
        '$2a$10$passwordhashexample1234.AbcdEfghIjklMnopQrstUvwx', 'Bio for user16',
        'ROLE_USER', 'PUBLIC', 'PUBLIC'),
       ('abcdefab-abcd-abcd-abcd-abcdefabcdef', 'user17', 'user17@example.com', 'Quincy', 'Murphy',
        '$2a$10$passwordhashexample1234.AbcdEfghIjklMnopQrstUvwx', 'Bio for user17',
        'ROLE_USER', 'FRIENDS_ONLY', 'REMEMBER_ONLY'),
       ('1234abcd-1234-abcd-1234-abcd1234abcd', 'user18', 'user18@example.com', 'Rachel', 'Parker',
        '$2a$10$passwordhashexample1234.AbcdEfghIjklMnopQrstUvwx', 'Bio for user18',
        'ROLE_USER', 'PRIVATE', 'PRIVATE'),
       ('deadbeef-dead-beef-dead-beefdeadbeef', 'user19', 'user19@example.com', 'Sam', 'Edwards',
        '$2a$10$passwordhashexample1234.AbcdEfghIjklMnopQrstUvwx', 'Bio for user19',
        'ROLE_USER', 'PUBLIC', 'FRIENDS_ONLY'),
       ('cafebabe-cafe-babe-cafe-babecafebabe', 'user20', 'user20@example.com', 'Tina', 'Roberts',
        '$2a$10$passwordhashexample1234.AbcdEfghIjklMnopQrstUvwx', 'Bio for user20',
        'ROLE_USER', 'FRIENDS_ONLY', 'PUBLIC');

INSERT INTO user_relationships(user_id, target_user_id, created_at)
VALUES ('550e8400-e29b-41d4-a716-446655440000', '22222222-2222-2222-2222-222222222222', '2023-01-15 08:30:45');
INSERT INTO user_relationships(user_id, target_user_id, created_at)
VALUES ('550e8400-e29b-41d4-a716-446655440000', '33333333-3333-3333-3333-333333333333', '2023-02-20 12:45:10');

INSERT INTO user_relationships(user_id, target_user_id, created_at)
VALUES ('550e8400-e29b-41d4-a716-446655440000', '44444444-4444-4444-4444-444444444444', '2023-07-22 16:44:55');
INSERT INTO user_relationships(user_id, target_user_id, created_at)
VALUES ('550e8400-e29b-41d4-a716-446655440000', '55555555-5555-5555-5555-555555555555', '2023-04-05 09:15:28');

INSERT INTO user_relationships(user_id, target_user_id, created_at)
VALUES ('550e8400-e29b-41d4-a716-446655440000', '66666666-6666-6666-6666-666666666666', '2023-05-12 14:50:03');
INSERT INTO user_relationships(user_id, target_user_id, created_at)
VALUES ('33333333-3333-3333-3333-333333333333', '550e8400-e29b-41d4-a716-446655440000', '2023-08-30 10:05:17');

INSERT INTO user_relationships(user_id, target_user_id, created_at)
VALUES ('44444444-4444-4444-4444-444444444444', '550e8400-e29b-41d4-a716-446655440000', '2023-03-10 18:22:37');

INSERT INTO user_relationships(user_id, target_user_id, created_at)
VALUES ('ffffffff-ffff-ffff-ffff-ffffffffffff', '550e8400-e29b-41d4-a716-446655440000', '2023-09-14 07:20:33');

INSERT INTO user_relationships(user_id, target_user_id, created_at)
VALUES ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '550e8400-e29b-41d4-a716-446655440000', '2023-10-01 13:11:49');

INSERT INTO user_relationships(user_id, target_user_id, created_at)
VALUES ('88888888-8888-8888-8888-888888888888', '550e8400-e29b-41d4-a716-446655440000', '2023-11-05 19:55:01');

INSERT INTO user_relationships(user_id, target_user_id, created_at)
VALUES ('cccccccc-cccc-cccc-cccc-cccccccccccc', '550e8400-e29b-41d4-a716-446655440000', '2023-12-12 22:40:38');
