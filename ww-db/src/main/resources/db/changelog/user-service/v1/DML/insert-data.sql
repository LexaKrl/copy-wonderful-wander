INSERT INTO account (user_id, username, email, firstname, lastname, password, bio, role, avatar_url, created_at,
                     updated_at, followers_count, following_count, friends_count, photo_visibility, walk_visibility)
VALUES ('11111111-1111-1111-1111-111111111111', 'user1', 'user1@example.com', 'Alice', 'Smith',
        '$2a$10$passwordhashexample1234.AbcdEfghIjklMnopQrstUvwx', 'Bio for user1',
        'ROLE_USER', 'http://example.com/avatar1.jpg', NOW(), NOW(), 5, 3, 2, 'PUBLIC', 'REMEMBER_ONLY'),
       ('22222222-2222-2222-2222-222222222222', 'user2', 'user2@example.com', 'Bob', 'Johnson',
        '$2a$10$passwordhashexample1234.AbcdEfghIjklMnopQrstUvwx', 'Bio for user2',
        'ROLE_USER', 'http://example.com/avatar2.jpg', NOW(), NOW(), 10, 7, 4, 'FRIENDS_ONLY', 'PRIVATE'),
       ('33333333-3333-3333-3333-333333333333', 'user3', 'user3@example.com', 'Charlie', 'Brown',
        '$2a$10$passwordhashexample1234.AbcdEfghIjklMnopQrstUvwx', 'Bio for user3',
        'ROLE_USER', 'http://example.com/avatar3.jpg', NOW(), NOW(), 8, 6, 3, 'PRIVATE', 'FRIENDS_ONLY'),
       ('44444444-4444-4444-4444-444444444444', 'user4', 'user4@example.com', 'David', 'Miller',
        '$2a$10$passwordhashexample1234.AbcdEfghIjklMnopQrstUvwx', 'Bio for user4',
        'ROLE_USER', 'http://example.com/avatar4.jpg', NOW(), NOW(), 12, 9, 5, 'PUBLIC', 'PUBLIC'),
       ('55555555-5555-5555-5555-555555555555', 'user5', 'user5@example.com', 'Eve', 'Davis',
        '$2a$10$passwordhashexample1234.AbcdEfghIjklMnopQrstUvwx', 'Bio for user5',
        'ROLE_USER', 'http://example.com/avatar5.jpg', NOW(), NOW(), 15, 11, 6, 'FRIENDS_ONLY', 'REMEMBER_ONLY'),
       ('66666666-6666-6666-6666-666666666666', 'user6', 'user6@example.com', 'Frank', 'Wilson',
        '$2a$10$passwordhashexample1234.AbcdEfghIjklMnopQrstUvwx', 'Bio for user6',
        'ROLE_USER', 'http://example.com/avatar6.jpg', NOW(), NOW(), 20, 14, 8, 'PRIVATE', 'PRIVATE'),
       ('77777777-7777-7777-7777-777777777777', 'user7', 'user7@example.com', 'Grace', 'Taylor',
        '$2a$10$passwordhashexample1234.AbcdEfghIjklMnopQrstUvwx', 'Bio for user7',
        'ROLE_USER', 'http://example.com/avatar7.jpg', NOW(), NOW(), 7, 5, 3, 'PUBLIC', 'FRIENDS_ONLY'),
       ('88888888-8888-8888-8888-888888888888', 'user8', 'user8@example.com', 'Hannah', 'Clark',
        '$2a$10$passwordhashexample1234.AbcdEfghIjklMnopQrstUvwx', 'Bio for user8',
        'ROLE_USER', 'http://example.com/avatar8.jpg', NOW(), NOW(), 9, 6, 4, 'FRIENDS_ONLY', 'PUBLIC'),
       ('99999999-9999-9999-9999-999999999999', 'user9', 'user9@example.com', 'Ian', 'Lewis',
        '$2a$10$passwordhashexample1234.AbcdEfghIjklMnopQrstUvwx', 'Bio for user9',
        'ROLE_USER', 'http://example.com/avatar9.jpg', NOW(), NOW(), 11, 8, 5, 'PRIVATE', 'REMEMBER_ONLY'),
       ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'user10', 'user10@example.com', 'Julia', 'Walker',
        '$2a$10$passwordhashexample1234.AbcdEfghIjklMnopQrstUvwx', 'Bio for user10',
        'ROLE_USER', 'http://example.com/avatar10.jpg', NOW(), NOW(), 13, 10, 6, 'PUBLIC', 'PRIVATE'),
       ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'user11', 'user11@example.com', 'Kevin', 'Hall',
        '$2a$10$passwordhashexample1234.AbcdEfghIjklMnopQrstUvwx', 'Bio for user11',
        'ROLE_USER', 'http://example.com/avatar11.jpg', NOW(), NOW(), 14, 12, 7, 'FRIENDS_ONLY', 'FRIENDS_ONLY'),
       ('cccccccc-cccc-cccc-cccc-cccccccccccc', 'user12', 'user12@example.com', 'Laura', 'Young',
        '$2a$10$passwordhashexample1234.AbcdEfghIjklMnopQrstUvwx', 'Bio for user12',
        'ROLE_USER', 'http://example.com/avatar12.jpg', NOW(), NOW(), 16, 13, 8, 'PRIVATE', 'PUBLIC'),
       ('dddddddd-dddd-dddd-dddd-dddddddddddd', 'user13', 'user13@example.com', 'Michael', 'King',
        '$2a$10$passwordhashexample1234.AbcdEfghIjklMnopQrstUvwx', 'Bio for user13',
        'ROLE_USER', 'http://example.com/avatar13.jpg', NOW(), NOW(), 18, 15, 9, 'PUBLIC', 'REMEMBER_ONLY'),
       ('eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', 'user14', 'user14@example.com', 'Nina', 'Wright',
        '$2a$10$passwordhashexample1234.AbcdEfghIjklMnopQrstUvwx', 'Bio for user14',
        'ROLE_USER', 'http://example.com/avatar14.jpg', NOW(), NOW(), 20, 17, 10, 'FRIENDS_ONLY', 'PRIVATE'),
       ('ffffffff-ffff-ffff-ffff-ffffffffffff', 'user15', 'user15@example.com', 'Oliver', 'Scott',
        '$2a$10$passwordhashexample1234.AbcdEfghIjklMnopQrstUvwx', 'Bio for user15',
        'ROLE_USER', 'http://example.com/avatar15.jpg', NOW(), NOW(), 22, 19, 11, 'PRIVATE', 'FRIENDS_ONLY'),
       ('12345678-1234-1234-1234-123456789abc', 'user16', 'user16@example.com', 'Peter', 'Evans',
        '$2a$10$passwordhashexample1234.AbcdEfghIjklMnopQrstUvwx', 'Bio for user16',
        'ROLE_USER', 'http://example.com/avatar16.jpg', NOW(), NOW(), 25, 22, 12, 'PUBLIC', 'PUBLIC'),
       ('abcdefab-abcd-abcd-abcd-abcdefabcdef', 'user17', 'user17@example.com', 'Quincy', 'Murphy',
        '$2a$10$passwordhashexample1234.AbcdEfghIjklMnopQrstUvwx', 'Bio for user17',
        'ROLE_USER', 'http://example.com/avatar17.jpg', NOW(), NOW(), 28, 25, 13, 'FRIENDS_ONLY', 'REMEMBER_ONLY'),
       ('1234abcd-1234-abcd-1234-abcd1234abcd', 'user18', 'user18@example.com', 'Rachel', 'Parker',
        '$2a$10$passwordhashexample1234.AbcdEfghIjklMnopQrstUvwx', 'Bio for user18',
        'ROLE_USER', 'http://example.com/avatar18.jpg', NOW(), NOW(), 30, 27, 14, 'PRIVATE', 'PRIVATE'),
       ('deadbeef-dead-beef-dead-beefdeadbeef', 'user19', 'user19@example.com', 'Sam', 'Edwards',
        '$2a$10$passwordhashexample1234.AbcdEfghIjklMnopQrstUvwx', 'Bio for user19',
        'ROLE_USER', 'http://example.com/avatar19.jpg', NOW(), NOW(), 32, 30, 15, 'PUBLIC', 'FRIENDS_ONLY'),
       ('cafebabe-cafe-babe-cafe-babecafebabe', 'user20', 'user20@example.com', 'Tina', 'Roberts',
        '$2a$10$passwordhashexample1234.AbcdEfghIjklMnopQrstUvwx', 'Bio for user20',
        'ROLE_USER', 'http://example.com/avatar20.jpg', NOW(), NOW(), 35, 33, 16, 'FRIENDS_ONLY', 'PUBLIC');

INSERT INTO user_relationships (user_id, target_user_id)
VALUES ('11111111-1111-1111-1111-111111111111', '22222222-2222-2222-2222-222222222222'), -- user1 -> user2
       ('11111111-1111-1111-1111-111111111111', '33333333-3333-3333-3333-333333333333'), -- user1 -> user3
       ('11111111-1111-1111-1111-111111111111', '44444444-4444-4444-4444-444444444444'), -- user1 -> user4
       ('22222222-2222-2222-2222-222222222222', '11111111-1111-1111-1111-111111111111'), -- user2 -> user1
       ('22222222-2222-2222-2222-222222222222', '33333333-3333-3333-3333-333333333333'), -- user2 -> user3
       ('22222222-2222-2222-2222-222222222222', '55555555-5555-5555-5555-555555555555'), -- user2 -> user5
       ('33333333-3333-3333-3333-333333333333', '11111111-1111-1111-1111-111111111111'), -- user3 -> user1
       ('33333333-3333-3333-3333-333333333333', '22222222-2222-2222-2222-222222222222'), -- user3 -> user2
       ('33333333-3333-3333-3333-333333333333', '44444444-4444-4444-4444-444444444444'), -- user3 -> user4
       ('44444444-4444-4444-4444-444444444444', '11111111-1111-1111-1111-111111111111'), -- user4 -> user1
       ('44444444-4444-4444-4444-444444444444', '22222222-2222-2222-2222-222222222222'), -- user4 -> user2
       ('44444444-4444-4444-4444-444444444444', '33333333-3333-3333-3333-333333333333'), -- user4 -> user3
       ('55555555-5555-5555-5555-555555555555', '11111111-1111-1111-1111-111111111111'), -- user5 -> user1
       ('55555555-5555-5555-5555-555555555555', '22222222-2222-2222-2222-222222222222'), -- user5 -> user2
       ('55555555-5555-5555-5555-555555555555', '33333333-3333-3333-3333-333333333333'); -- user5 -> user3