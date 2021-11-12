use social_media;



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

where (sender = '5' and receiver= '5')
   or (receiver = '5' and sender = '5')
order by time desc limit 1;



# or receiver = '1') and not message.sender != '1' and not message.receiver != '1' order by time;

SELECT sender, time
from message
where receiver = '1'
GROUP BY message.sender;


