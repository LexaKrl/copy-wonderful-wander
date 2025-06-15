-- Main test account
INSERT INTO account (user_id, username, email, firstname, lastname, password, bio, role)
VALUES ('550e8400-e29b-41d4-a716-446655440000',
        'john_doe',
        'john.doe@example.com',
        'John',
        'Doe',
        '$2a$12$KyCJ1co4GF5mI1TeMUuiRuime9RLD57ZJzX/qUHB8DfEKEFTDtNMS',
        'Love hiking and photography!',
        'ROLE_USER');

-- Auxiliary accounts for checking the logic of relationships
INSERT INTO account (user_id, username, email, firstname, lastname, password, bio, role, photo_visibility,
                     walk_visibility)
VALUES ('11111111-1111-1111-1111-111111111111', 'user1', 'user1@example.com', 'Alice', 'Smith',
        '$2a$12$KyCJ1co4GF5mI1TeMUuiRuime9RLD57ZJzX/qUHB8DfEKEFTDtNMS', 'Bio for user1',
        'ROLE_USER', 'PUBLIC', 'REMEMBER_ONLY'),
       ('22222222-2222-2222-2222-222222222222', 'user2', 'user2@example.com', 'Bob', 'Johnson',
        '$2a$12$KyCJ1co4GF5mI1TeMUuiRuime9RLD57ZJzX/qUHB8DfEKEFTDtNMS', 'Bio for user2',
        'ROLE_USER', 'FRIENDS_ONLY', 'PRIVATE'),
       ('33333333-3333-3333-3333-333333333333', 'user3', 'user3@example.com', 'Charlie', 'Brown',
        '$2a$12$KyCJ1co4GF5mI1TeMUuiRuime9RLD57ZJzX/qUHB8DfEKEFTDtNMS', 'Bio for user3',
        'ROLE_USER', 'PRIVATE', 'FRIENDS_ONLY'),
       ('44444444-4444-4444-4444-444444444444', 'user4', 'user4@example.com', 'David', 'Miller',
        '$2a$12$KyCJ1co4GF5mI1TeMUuiRuime9RLD57ZJzX/qUHB8DfEKEFTDtNMS', 'Bio for user4',
        'ROLE_USER', 'PUBLIC', 'PUBLIC'),
       ('55555555-5555-5555-5555-555555555555', 'user5', 'user5@example.com', 'Eve', 'Davis',
        '$2a$12$KyCJ1co4GF5mI1TeMUuiRuime9RLD57ZJzX/qUHB8DfEKEFTDtNMS', 'Bio for user5',
        'ROLE_USER', 'FRIENDS_ONLY', 'REMEMBER_ONLY'),
       ('66666666-6666-6666-6666-666666666666', 'user6', 'user6@example.com', 'Frank', 'Wilson',
        '$2a$12$KyCJ1co4GF5mI1TeMUuiRuime9RLD57ZJzX/qUHB8DfEKEFTDtNMS', 'Bio for user6',
        'ROLE_USER', 'PRIVATE', 'PRIVATE'),
       ('77777777-7777-7777-7777-777777777777', 'user7', 'user7@example.com', 'Grace', 'Taylor',
        '$2a$12$KyCJ1co4GF5mI1TeMUuiRuime9RLD57ZJzX/qUHB8DfEKEFTDtNMS', 'Bio for user7',
        'ROLE_USER', 'PUBLIC', 'FRIENDS_ONLY'),
       ('88888888-8888-8888-8888-888888888888', 'user8', 'user8@example.com', 'Hannah', 'Clark',
        '$2a$12$KyCJ1co4GF5mI1TeMUuiRuime9RLD57ZJzX/qUHB8DfEKEFTDtNMS', 'Bio for user8',
        'ROLE_USER', 'FRIENDS_ONLY', 'PUBLIC'),
       ('99999999-9999-9999-9999-999999999999', 'user9', 'user9@example.com', 'Ian', 'Lewis',
        '$2a$12$KyCJ1co4GF5mI1TeMUuiRuime9RLD57ZJzX/qUHB8DfEKEFTDtNMS', 'Bio for user9',
        'ROLE_USER', 'PRIVATE', 'REMEMBER_ONLY'),
       ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'user10', 'user10@example.com', 'Julia', 'Walker',
        '$2a$12$KyCJ1co4GF5mI1TeMUuiRuime9RLD57ZJzX/qUHB8DfEKEFTDtNMS', 'Bio for user10',
        'ROLE_USER', 'PUBLIC', 'PRIVATE'),
       ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'user11', 'user11@example.com', 'Kevin', 'Hall',
        '$2a$12$KyCJ1co4GF5mI1TeMUuiRuime9RLD57ZJzX/qUHB8DfEKEFTDtNMS', 'Bio for user11',
        'ROLE_USER', 'FRIENDS_ONLY', 'FRIENDS_ONLY'),
       ('cccccccc-cccc-cccc-cccc-cccccccccccc', 'user12', 'user12@example.com', 'Laura', 'Young',
        '$2a$12$KyCJ1co4GF5mI1TeMUuiRuime9RLD57ZJzX/qUHB8DfEKEFTDtNMS', 'Bio for user12',
        'ROLE_USER', 'PRIVATE', 'PUBLIC'),
       ('dddddddd-dddd-dddd-dddd-dddddddddddd', 'user13', 'user13@example.com', 'Michael', 'King',
        '$2a$12$KyCJ1co4GF5mI1TeMUuiRuime9RLD57ZJzX/qUHB8DfEKEFTDtNMS', 'Bio for user13',
        'ROLE_USER', 'PUBLIC', 'REMEMBER_ONLY'),
       ('eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', 'user14', 'user14@example.com', 'Nina', 'Wright',
        '$2a$12$KyCJ1co4GF5mI1TeMUuiRuime9RLD57ZJzX/qUHB8DfEKEFTDtNMS', 'Bio for user14',
        'ROLE_USER', 'FRIENDS_ONLY', 'PRIVATE'),
       ('ffffffff-ffff-ffff-ffff-ffffffffffff', 'user15', 'user15@example.com', 'Oliver', 'Scott',
        '$2a$12$KyCJ1co4GF5mI1TeMUuiRuime9RLD57ZJzX/qUHB8DfEKEFTDtNMS', 'Bio for user15',
        'ROLE_USER', 'PRIVATE', 'FRIENDS_ONLY'),
       ('12345678-1234-1234-1234-123456789abc', 'user16', 'user16@example.com', 'Peter', 'Evans',
        '$2a$12$KyCJ1co4GF5mI1TeMUuiRuime9RLD57ZJzX/qUHB8DfEKEFTDtNMS', 'Bio for user16',
        'ROLE_USER', 'PUBLIC', 'PUBLIC'),
       ('abcdefab-abcd-abcd-abcd-abcdefabcdef', 'user17', 'user17@example.com', 'Quincy', 'Murphy',
        '$2a$12$KyCJ1co4GF5mI1TeMUuiRuime9RLD57ZJzX/qUHB8DfEKEFTDtNMS', 'Bio for user17',
        'ROLE_USER', 'FRIENDS_ONLY', 'REMEMBER_ONLY'),
       ('1234abcd-1234-abcd-1234-abcd1234abcd', 'user18', 'user18@example.com', 'Rachel', 'Parker',
        '$2a$12$KyCJ1co4GF5mI1TeMUuiRuime9RLD57ZJzX/qUHB8DfEKEFTDtNMS', 'Bio for user18',
        'ROLE_USER', 'PRIVATE', 'PRIVATE'),
       ('deadbeef-dead-beef-dead-beefdeadbeef', 'user19', 'user19@example.com', 'Sam', 'Edwards',
        '$2a$12$KyCJ1co4GF5mI1TeMUuiRuime9RLD57ZJzX/qUHB8DfEKEFTDtNMS', 'Bio for user19',
        'ROLE_USER', 'PUBLIC', 'FRIENDS_ONLY'),
       ('cafebabe-cafe-babe-cafe-babecafebabe', 'user20', 'user20@example.com', 'Tina', 'Roberts',
        '$2a$12$KyCJ1co4GF5mI1TeMUuiRuime9RLD57ZJzX/qUHB8DfEKEFTDtNMS', 'Bio for user20',
        'ROLE_USER', 'FRIENDS_ONLY', 'PUBLIC');
INSERT INTO account (user_id, username, email, firstname, lastname, password, bio, role, photo_visibility,
                     walk_visibility)
VALUES ('1a2b3c4d-1a2b-1a2b-1a2b-1a2b3c4d5e6f', 'user21', 'user21@example.com', 'Uma', 'Turner',
        '$2a$12$KyCJ1co4GF5mI1TeMUuiRuime9RLD57ZJzX/qUHB8DfEKEFTDtNMS', 'Bio for user21',
        'ROLE_USER', 'PRIVATE', 'REMEMBER_ONLY'),
       ('2b3c4d5e-2b3c-2b3c-2b3c-2b3c4d5e6f7a', 'user22', 'user22@example.com', 'Victor', 'White',
        '$2a$12$KyCJ1co4GF5mI1TeMUuiRuime9RLD57ZJzX/qUHB8DfEKEFTDtNMS', 'Bio for user22',
        'ROLE_USER', 'PUBLIC', 'FRIENDS_ONLY'),
       ('3c4d5e6f-3c4d-3c4d-3c4d-3c4d5e6f7a8b', 'user23', 'user23@example.com', 'Wendy', 'Harris',
        '$2a$12$KyCJ1co4GF5mI1TeMUuiRuime9RLD57ZJzX/qUHB8DfEKEFTDtNMS', 'Bio for user23',
        'ROLE_USER', 'FRIENDS_ONLY', 'PUBLIC'),
       ('4d5e6f7a-4d5e-4d5e-4d5e-4d5e6f7a8b9c', 'user24', 'user24@example.com', 'Xander', 'Lewis',
        '$2a$12$KyCJ1co4GF5mI1TeMUuiRuime9RLD57ZJzX/qUHB8DfEKEFTDtNMS', 'Bio for user24',
        'ROLE_USER', 'PRIVATE', 'PRIVATE'),
       ('5e6f7a8b-5e6f-5e6f-5e6f-5e6f7a8b9c0d', 'user25', 'user25@example.com', 'Yvonne', 'Lee',
        '$2a$12$KyCJ1co4GF5mI1TeMUuiRuime9RLD57ZJzX/qUHB8DfEKEFTDtNMS', 'Bio for user25',
        'ROLE_USER', 'PUBLIC', 'REMEMBER_ONLY'),
       ('6f7a8b9c-6f7a-6f7a-6f7a-6f7a8b9c0d1e', 'user26', 'user26@example.com', 'Zach', 'Allen',
        '$2a$12$KyCJ1co4GF5mI1TeMUuiRuime9RLD57ZJzX/qUHB8DfEKEFTDtNMS', 'Bio for user26',
        'ROLE_USER', 'FRIENDS_ONLY', 'FRIENDS_ONLY'),
       ('7a8b9c0d-7a8b-7a8b-7a8b-7a8b9c0d1e2f', 'user27', 'user27@example.com', 'Aaron', 'Young',
        '$2a$12$KyCJ1co4GF5mI1TeMUuiRuime9RLD57ZJzX/qUHB8DfEKEFTDtNMS', 'Bio for user27',
        'ROLE_USER', 'PRIVATE', 'PUBLIC'),
       ('8b9c0d1e-8b9c-8b9c-8b9c-8b9c0d1e2f3a', 'user28', 'user28@example.com', 'Bella', 'Scott',
        '$2a$12$KyCJ1co4GF5mI1TeMUuiRuime9RLD57ZJzX/qUHB8DfEKEFTDtNMS', 'Bio for user28',
        'ROLE_USER', 'PUBLIC', 'PRIVATE'),
       ('9c0d1e2f-9c0d-9c0d-9c0d-9c0d1e2f3a4b', 'user29', 'user29@example.com', 'Chris', 'King',
        '$2a$12$KyCJ1co4GF5mI1TeMUuiRuime9RLD57ZJzX/qUHB8DfEKEFTDtNMS', 'Bio for user29',
        'ROLE_USER', 'FRIENDS_ONLY', 'REMEMBER_ONLY'),
       ('0d1e2f3a-0d1e-0d1e-0d1e-0d1e2f3a4b5c', 'user30', 'user30@example.com', 'Diana', 'Adams',
        '$2a$12$KyCJ1co4GF5mI1TeMUuiRuime9RLD57ZJzX/qUHB8DfEKEFTDtNMS', 'Bio for user30',
        'ROLE_USER', 'PRIVATE', 'FRIENDS_ONLY');

INSERT INTO account (user_id, username, email, firstname, lastname, password, bio, role, photo_visibility, walk_visibility)
VALUES
    ('a1b2c3d4-a1b2-a1b2-a1b2-a1b2c3d4e5f6', 'user31', 'user31@example.com', 'Ethan', 'Turner',
     '$2a$12$KyCJ1co4GF5mI1TeMUuiRuime9RLD57ZJzX/qUHB8DfEKEFTDtNMS', 'Bio for user31',
     'ROLE_USER', 'PUBLIC', 'FRIENDS_ONLY'),
    ('b2c3d4e5-b2c3-b2c3-b2c3-b2c3d4e5f6a7', 'user32', 'user32@example.com', 'Fiona', 'Mitchell',
     '$2a$12$KyCJ1co4GF5mI1TeMUuiRuime9RLD57ZJzX/qUHB8DfEKEFTDtNMS', 'Bio for user32',
     'ROLE_USER', 'FRIENDS_ONLY', 'PRIVATE'),
    ('c3d4e5f6-c3d4-c3d4-c3d4-c3d4e5f6a7b8', 'user33', 'user33@example.com', 'George', 'Baker',
     '$2a$12$KyCJ1co4GF5mI1TeMUuiRuime9RLD57ZJzX/qUHB8DfEKEFTDtNMS', 'Bio for user33',
     'ROLE_USER', 'PRIVATE', 'REMEMBER_ONLY'),
    ('d4e5f6a7-d4e5-d4e5-d4e5-d4e5f6a7b8c9', 'user34', 'user34@example.com', 'Holly', 'Gonzalez',
     '$2a$12$KyCJ1co4GF5mI1TeMUuiRuime9RLD57ZJzX/qUHB8DfEKEFTDtNMS', 'Bio for user34',
     'ROLE_USER', 'PUBLIC', 'PUBLIC'),
    ('e5f6a7b8-e5f6-e5f6-e5f6-e5f6a7b8c9d0', 'user35', 'user35@example.com', 'Ian', 'Nelson',
     '$2a$12$KyCJ1co4GF5mI1TeMUuiRuime9RLD57ZJzX/qUHB8DfEKEFTDtNMS', 'Bio for user35',
     'ROLE_USER', 'FRIENDS_ONLY', 'FRIENDS_ONLY'),
    ('f6a7b8c9-f6a7-f6a7-f6a7-f6a7b8c9d0e1', 'user36', 'user36@example.com', 'Julia', 'Carter',
     '$2a$12$KyCJ1co4GF5mI1TeMUuiRuime9RLD57ZJzX/qUHB8DfEKEFTDtNMS', 'Bio for user36',
     'ROLE_USER', 'PRIVATE', 'PUBLIC'),
    ('a7b8c9d0-a7b8-a7b8-a7b8-a7b8c9d0e1f2', 'user37', 'user37@example.com', 'Kevin', 'Reed',
     '$2a$12$KyCJ1co4GF5mI1TeMUuiRuime9RLD57ZJzX/qUHB8DfEKEFTDtNMS', 'Bio for user37',
     'ROLE_USER', 'PUBLIC', 'PRIVATE'),
    ('b8c9d0e1-b8c9-b8c9-b8c9-b8c9d0e1f2a3', 'user38', 'user38@example.com', 'Laura', 'Torres',
     '$2a$12$KyCJ1co4GF5mI1TeMUuiRuime9RLD57ZJzX/qUHB8DfEKEFTDtNMS', 'Bio for user38',
     'ROLE_USER', 'FRIENDS_ONLY', 'REMEMBER_ONLY'),
    ('c9d0e1f2-c9d0-c9d0-c9d0-c9d0e1f2a3b4', 'user39', 'user39@example.com', 'Matthew', 'Peterson',
     '$2a$12$KyCJ1co4GF5mI1TeMUuiRuime9RLD57ZJzX/qUHB8DfEKEFTDtNMS', 'Bio for user39',
     'ROLE_USER', 'PRIVATE', 'FRIENDS_ONLY'),
    ('d0e1f2a3-d0e1-d0e1-d0e1-d0e1f2a3b4c5', 'user40', 'user40@example.com', 'Natalie', 'Gray',
     '$2a$12$KyCJ1co4GF5mI1TeMUuiRuime9RLD57ZJzX/qUHB8DfEKEFTDtNMS', 'Bio for user40',
     'ROLE_USER', 'PUBLIC', 'REMEMBER_ONLY');

-- Взаимные подписки
INSERT INTO user_relationships(user_id, target_user_id, created_at)
VALUES ('550e8400-e29b-41d4-a716-446655440000', '11111111-1111-1111-1111-111111111111', '2023-01-15 08:30:45');
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
VALUES ('550e8400-e29b-41d4-a716-446655440000', '77777777-7777-7777-7777-777777777777', '2023-01-15 08:30:45');
INSERT INTO user_relationships(user_id, target_user_id, created_at)
VALUES ('550e8400-e29b-41d4-a716-446655440000', '88888888-8888-8888-8888-888888888888', '2023-02-20 12:45:10');
INSERT INTO user_relationships(user_id, target_user_id, created_at)
VALUES ('550e8400-e29b-41d4-a716-446655440000', '99999999-9999-9999-9999-999999999999', '2023-07-22 16:44:55');
INSERT INTO user_relationships(user_id, target_user_id, created_at)
VALUES ('550e8400-e29b-41d4-a716-446655440000', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '2023-04-05 09:15:28');
INSERT INTO user_relationships(user_id, target_user_id, created_at)
VALUES ('550e8400-e29b-41d4-a716-446655440000', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '2023-05-12 14:50:03');
INSERT INTO user_relationships(user_id, target_user_id, created_at)
VALUES ('550e8400-e29b-41d4-a716-446655440000', 'cccccccc-cccc-cccc-cccc-cccccccccccc', '2023-03-10 11:22:33');
INSERT INTO user_relationships(user_id, target_user_id, created_at)
VALUES ('550e8400-e29b-41d4-a716-446655440000', 'dddddddd-dddd-dddd-dddd-dddddddddddd', '2023-06-01 08:45:12');
INSERT INTO user_relationships(user_id, target_user_id, created_at)
VALUES ('550e8400-e29b-41d4-a716-446655440000', 'eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', '2023-08-14 19:05:37');
INSERT INTO user_relationships(user_id, target_user_id, created_at)
VALUES ('550e8400-e29b-41d4-a716-446655440000', 'ffffffff-ffff-ffff-ffff-ffffffffffff', '2023-09-19 13:27:44');
INSERT INTO user_relationships(user_id, target_user_id, created_at)
VALUES ('550e8400-e29b-41d4-a716-446655440000', '12345678-1234-1234-1234-123456789abc', '2023-02-28 10:11:58');
INSERT INTO user_relationships(user_id, target_user_id, created_at)
VALUES ('550e8400-e29b-41d4-a716-446655440000', 'abcdefab-abcd-abcd-abcd-abcdefabcdef', '2023-11-03 17:33:21');
INSERT INTO user_relationships(user_id, target_user_id, created_at)
VALUES ('550e8400-e29b-41d4-a716-446655440000', '1234abcd-1234-abcd-1234-abcd1234abcd', '2023-12-25 12:00:45');
INSERT INTO user_relationships(user_id, target_user_id, created_at)
VALUES ('550e8400-e29b-41d4-a716-446655440000', 'deadbeef-dead-beef-dead-beefdeadbeef', '2023-10-11 09:42:10');
INSERT INTO user_relationships(user_id, target_user_id, created_at)
VALUES ('550e8400-e29b-41d4-a716-446655440000', 'cafebabe-cafe-babe-cafe-babecafebabe', '2023-01-01 00:01:02');
INSERT INTO user_relationships(user_id, target_user_id, created_at)
VALUES ('550e8400-e29b-41d4-a716-446655440000', '1a2b3c4d-1a2b-1a2b-1a2b-1a2b3c4d5e6f', '2023-04-18 15:20:30');
INSERT INTO user_relationships(target_user_id, user_id, created_at)
VALUES ('550e8400-e29b-41d4-a716-446655440000', '11111111-1111-1111-1111-111111111111', '2023-01-15 08:30:45');
INSERT INTO user_relationships(target_user_id, user_id, created_at)
VALUES ('550e8400-e29b-41d4-a716-446655440000', '22222222-2222-2222-2222-222222222222', '2023-01-15 08:30:45');
INSERT INTO user_relationships(target_user_id, user_id, created_at)
VALUES ('550e8400-e29b-41d4-a716-446655440000', '33333333-3333-3333-3333-333333333333', '2023-02-20 12:45:10');
INSERT INTO user_relationships(target_user_id, user_id, created_at)
VALUES ('550e8400-e29b-41d4-a716-446655440000', '44444444-4444-4444-4444-444444444444', '2023-07-22 16:44:55');
INSERT INTO user_relationships(target_user_id, user_id, created_at)
VALUES ('550e8400-e29b-41d4-a716-446655440000', '55555555-5555-5555-5555-555555555555', '2023-04-05 09:15:28');
INSERT INTO user_relationships(target_user_id, user_id, created_at)
VALUES ('550e8400-e29b-41d4-a716-446655440000', '66666666-6666-6666-6666-666666666666', '2023-05-12 14:50:03');
INSERT INTO user_relationships(target_user_id, user_id, created_at)
VALUES ('550e8400-e29b-41d4-a716-446655440000', '77777777-7777-7777-7777-777777777777', '2023-01-15 08:30:45');
INSERT INTO user_relationships(target_user_id, user_id, created_at)
VALUES ('550e8400-e29b-41d4-a716-446655440000', '88888888-8888-8888-8888-888888888888', '2023-02-20 12:45:10');
INSERT INTO user_relationships(target_user_id, user_id, created_at)
VALUES ('550e8400-e29b-41d4-a716-446655440000', '99999999-9999-9999-9999-999999999999', '2023-07-22 16:44:55');
INSERT INTO user_relationships(target_user_id, user_id, created_at)
VALUES ('550e8400-e29b-41d4-a716-446655440000', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '2023-04-05 09:15:28');
INSERT INTO user_relationships(target_user_id, user_id, created_at)
VALUES ('550e8400-e29b-41d4-a716-446655440000', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '2023-05-12 14:50:03');
INSERT INTO user_relationships(target_user_id, user_id, created_at)
VALUES ('550e8400-e29b-41d4-a716-446655440000', 'cccccccc-cccc-cccc-cccc-cccccccccccc', '2023-03-10 11:22:33');
INSERT INTO user_relationships(target_user_id, user_id, created_at)
VALUES ('550e8400-e29b-41d4-a716-446655440000', 'dddddddd-dddd-dddd-dddd-dddddddddddd', '2023-06-01 08:45:12');
INSERT INTO user_relationships(target_user_id, user_id, created_at)
VALUES ('550e8400-e29b-41d4-a716-446655440000', 'eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', '2023-08-14 19:05:37');
INSERT INTO user_relationships(target_user_id, user_id, created_at)
VALUES ('550e8400-e29b-41d4-a716-446655440000', 'ffffffff-ffff-ffff-ffff-ffffffffffff', '2023-09-19 13:27:44');
INSERT INTO user_relationships(target_user_id, user_id, created_at)
VALUES ('550e8400-e29b-41d4-a716-446655440000', '12345678-1234-1234-1234-123456789abc', '2023-02-28 10:11:58');
INSERT INTO user_relationships(target_user_id, user_id, created_at)
VALUES ('550e8400-e29b-41d4-a716-446655440000', 'abcdefab-abcd-abcd-abcd-abcdefabcdef', '2023-11-03 17:33:21');
INSERT INTO user_relationships(target_user_id, user_id, created_at)
VALUES ('550e8400-e29b-41d4-a716-446655440000', '1234abcd-1234-abcd-1234-abcd1234abcd', '2023-12-25 12:00:45');
INSERT INTO user_relationships(target_user_id, user_id, created_at)
VALUES ('550e8400-e29b-41d4-a716-446655440000', 'deadbeef-dead-beef-dead-beefdeadbeef', '2023-10-11 09:42:10');
INSERT INTO user_relationships(target_user_id, user_id, created_at)
VALUES ('550e8400-e29b-41d4-a716-446655440000', 'cafebabe-cafe-babe-cafe-babecafebabe', '2023-01-01 00:01:02');
INSERT INTO user_relationships(target_user_id, user_id, created_at)
VALUES ('550e8400-e29b-41d4-a716-446655440000', '1a2b3c4d-1a2b-1a2b-1a2b-1a2b3c4d5e6f', '2023-04-18 15:20:30');

-- Подписки
INSERT INTO user_relationships(user_id, target_user_id, created_at)
VALUES ('550e8400-e29b-41d4-a716-446655440000', 'a1b2c3d4-a1b2-a1b2-a1b2-a1b2c3d4e5f6', '2023-03-05 10:20:30');
INSERT INTO user_relationships(user_id, target_user_id, created_at)
VALUES ('550e8400-e29b-41d4-a716-446655440000', 'b2c3d4e5-b2c3-b2c3-b2c3-b2c3d4e5f6a7', '2023-04-12 15:45:00');
INSERT INTO user_relationships(user_id, target_user_id, created_at)
VALUES ('550e8400-e29b-41d4-a716-446655440000', 'c3d4e5f6-c3d4-c3d4-c3d4-c3d4e5f6a7b8', '2023-06-18 09:30:15');
INSERT INTO user_relationships(user_id, target_user_id, created_at)
VALUES ('550e8400-e29b-41d4-a716-446655440000', 'd4e5f6a7-d4e5-d4e5-d4e5-d4e5f6a7b8c9', '2023-07-23 14:10:45');
INSERT INTO user_relationships(user_id, target_user_id, created_at)
VALUES ('550e8400-e29b-41d4-a716-446655440000', 'e5f6a7b8-e5f6-e5f6-e5f6-e5f6a7b8c9d0', '2023-09-10 18:05:20');

-- Подписчики
INSERT INTO user_relationships(target_user_id, user_id, created_at)
VALUES ('550e8400-e29b-41d4-a716-446655440000', 'f6a7b8c9-f6a7-f6a7-f6a7-f6a7b8c9d0e1', '2023-02-14 09:05:12');
INSERT INTO user_relationships(target_user_id, user_id, created_at)
VALUES ('550e8400-e29b-41d4-a716-446655440000', 'a7b8c9d0-a7b8-a7b8-a7b8-a7b8c9d0e1f2', '2023-05-19 13:45:30');
INSERT INTO user_relationships(target_user_id, user_id, created_at)
VALUES ('550e8400-e29b-41d4-a716-446655440000', 'b8c9d0e1-b8c9-b8c9-b8c9-b8c9d0e1f2a3', '2023-08-22 17:20:45');
INSERT INTO user_relationships(target_user_id, user_id, created_at)
VALUES ('550e8400-e29b-41d4-a716-446655440000', 'c9d0e1f2-c9d0-c9d0-c9d0-c9d0e1f2a3b4', '2023-10-01 11:10:55');
INSERT INTO user_relationships(target_user_id, user_id, created_at)
VALUES ('550e8400-e29b-41d4-a716-446655440000', 'd0e1f2a3-d0e1-d0e1-d0e1-d0e1f2a3b4c5', '2023-12-12 18:30:22');