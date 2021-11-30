create table if not exists social_user
(
    uid            int auto_increment,
    username       varchar(80) default 'username' not null,
    name           varchar(45)                    null,
    user_image_url varchar(300)                   null,
    bio            varchar(300)                   null,
    email          varchar(120)                   null,
    phone_number   varchar(45)                    null,
    token          varchar(400)                   null,
    constraint `PRIMARY`
        primary key (uid, username)
);

create table if not exists user
(
    uid            int auto_increment,
    email          varchar(120)                   not null,
    username       varchar(80) default 'username' not null,
    name           varchar(45)                    null,
    user_image_url varchar(300)                   null,
    token          varchar(3000)                  null,
    bio            varchar(300)                   null,
    phone_number   varchar(45)                    null,
    password       varchar(60)                    not null,
    constraint `PRIMARY`
        primary key (uid, email, username)
);
create index username_user_idx
    on user (username);

SET FOREIGN_KEY_CHECKS=1;

create table if not exists connection
(
    user1 varchar(80) not null,
    user2 varchar(80) not null,
    time  timestamp   null,
    constraint user1_user
        foreign key (user1)
            references user (username)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    constraint user2_user
        foreign key (user2)
            references user (username)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);
create index user1_user_idx
    on connection (user1);

create index user2_user_idx
    on connection (user2);

create table if not exists connectionrequest
(
    follower_username varchar(80) not null,
    username          varchar(80) not null,
    time              datetime    null,
    constraint connection_request_follower_username
        foreign key (follower_username)
            references user (username)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    constraint connection_request_username
        foreign key (username)
            references user (username)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);

create index connection_request_to_user_idx
    on connectionrequest (username);

create index connection_request_user_idx
    on connectionrequest (follower_username);

create table if not exists notifications
(
    uid     int          not null,
    n_type  varchar(60)  null,
    message varchar(200) null,
    constraint notification_user
        foreign key (uid) references user (uid)
);

create index notification_user_idx
    on notifications (uid);

create table if not exists post
(
    pid       int auto_increment primary key,
    caption   varchar(320) null,
    username  varchar(80)  not null,
    time      datetime     not null,
    image_url varchar(300) null,
    constraint FK_PostUser
        foreign key (username)
            references user (username)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);

create index post_user_idx
    on post (username);

create table if not exists comment
(
    pid             int          not null,
    username        varchar(80)  not null,
    comment_content varchar(120) null,
    time            timestamp    null,
    constraint comment_post
        foreign key (pid) references post (pid)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    constraint comment_user
        foreign key (username) references user (username)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);

create index comment_post_idx
    on comment (pid);
create index comment_user_idx
    on comment (username);

create table if not exists `like`
(
    username varchar(80) not null,
    time     timestamp   null,
    pid      int         not null,
    constraint like_post
        foreign key (pid) references post (pid),
    constraint like_user
        foreign key (username) references user (username)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);

create index like_post_idx
    on `like` (pid);

create index like_user_idx
    on `like` (username);

create index FK_PostUser_idx
    on post (username);

create table if not exists share
(
    uid     int         not null,
    pid     int         not null,
    time    timestamp   null,
    caption varchar(45) null,
    constraint share_post
        foreign key (pid) references post (pid),
    constraint share_user
        foreign key (uid) references user (uid)
);

create index share_post_idx
    on share (pid);

create index share_user_idx
    on share (uid);

create table if not exists socialconnection
(
    uid               varchar(80)  not null,
    social_type       varchar(60)  null,
    user_profile_link varchar(500) null,
    constraint social_user
        foreign key (uid) references user (username)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);

create index social_user_idx
    on socialconnection (uid);

create index FK_User_idx
    on user (username);

