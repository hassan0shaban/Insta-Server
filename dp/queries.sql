use social_media;




delete from post where pid = 1;

alter table post alter pid DROP DEFAULT;

ALTER TABLE post alter pid SET DEFAULT 100;


ALTER TABLE post
    add constraint FK_UserPost
    FOREIGN KEY (uid) REFERENCES user(username);