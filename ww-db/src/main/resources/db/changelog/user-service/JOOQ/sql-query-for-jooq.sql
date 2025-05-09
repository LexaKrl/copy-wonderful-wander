-- Получение всех друзей в порядке от старых к новым
-- (дата дружбы = поздняя дата из дат, подписок обоих людей друг на друга)
SELECT a.*
FROM account a
         INNER JOIN (SELECT ur1.target_user_id                       AS friend_id,
                            GREATEST(ur1.created_at, ur2.created_at) AS friendship_date
                     FROM user_relationships ur1
                              INNER JOIN user_relationships ur2
                                         ON ur1.target_user_id = ur2.user_id
                                             AND ur2.target_user_id = ur1.user_id
                     WHERE ur1.user_id = ?) AS friends
                    ON a.user_id = friends.friend_id
ORDER BY friends.friendship_date ASC
LIMIT ? OFFSET ?;

-- Получение всех подписчиков по дате подписки по возрастанию
SELECT a.*
FROM account a
         INNER JOIN (SELECT ur.target_user_id, ur.created_at
                     FROM user_relationships ur
                     WHERE ur.user_id = ?) AS followings ON a.user_id = followings.target_user_id
ORDER BY followings.created_at ASC
LIMIT ? OFFSET ?;

-- Получение всех подписок
SELECT a.*
FROM account a
         INNER JOIN (SELECT ur.user_id, ur.created_at
                     FROM user_relationships ur
                     WHERE ur.target_user_id = ?) AS follows
                    ON a.user_id = follows.user_id
ORDER BY follows.created_at ASC
LIMIT ? OFFSET ?;