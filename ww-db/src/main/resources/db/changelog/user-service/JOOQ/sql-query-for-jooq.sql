-- Получение всех друзей
SELECT *
FROM account
WHERE user_id IN (SELECT CASE
                             WHEN ur.user_id = ? THEN ur.target_user_id
                             ELSE ur.user_id
                             END AS friend_id
                  FROM user_relationships ur
                  WHERE EXISTS (SELECT 1
                                FROM user_relationships ur2
                                WHERE ur.user_id = ur2.target_user_id
                                  AND ur.target_user_id = ur2.user_id)
                    AND (ur.user_id = ? OR ur.target_user_id = ?));

-- Получение всех подписчиков
SELECT *
FROM account
WHERE user_id IN (SELECT ur.user_id
                  FROM user_relationships ur
                  WHERE ur.target_user_id = '11111111-1111-1111-1111-111111111111');

-- Получение всех подписок
SELECT *
FROM account
WHERE user_id IN (SELECT ur.target_user_id
                  FROM user_relationships ur
                  WHERE ur.user_id = '11111111-1111-1111-1111-111111111111');