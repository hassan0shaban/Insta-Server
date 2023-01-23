use social_media;


SELECT user,authentication_string,plugin,host FROM mysql.user;

ALTER TABLE market.product alter adding_time SET DEFAULT ;

delete
from post
where pid = 1;

alter table post
    alter pid DROP DEFAULT;

ALTER TABLE post
    alter pid SET DEFAULT 100;


ALTER TABLE post
    add constraint FK_UserPost
        FOREIGN KEY (uid) REFERENCES user (username);

SELECT *
from message
         join user
where ((sender = '1')
    and user.username = message.receiver)
   or ((receiver = '1')
    and user.username = message.sender)
group by user.username;

SELECT *
from message

where (sender = '5' and receiver = '5')
   or (receiver = '5' and sender = '5')
order by time desc
limit 1;


SELECT *
from post inner join user

where (user.username = post.username and post.caption like '%hassan%');


SELECT name, post.image_url, post.pid, (count(`like`.username) - 1) as count, max(`like`.time) as time
from social_media.like
         join user
         join post
where (
                  user.username = `like`.username and
                  post.pid = `like`.pid and
                  post.username = '5'
          )
group by post.pid
limit 50;

SELECT *
from social_media.comment
         CROSS JOIN user
where (user.username = comment.username and
       comment.pid = 23)
limit 50;


SELECT name, post.image_url, post.pid, (count(comment.username) - 1) as count, max(comment.time) as time
from social_media.comment
         join user
         join post
where (
                  user.username = comment.username and
                  post.pid = comment.pid and
                  post.username = '5'
          )
group by post.pid
limit 50;

# order by social_media.like.time desc;


# or receiver = '1') and not message.sender != '1' and not message.receiver != '1' order by time;

SELECT sender, time
from message
where receiver = '1'
GROUP BY message.sender;


