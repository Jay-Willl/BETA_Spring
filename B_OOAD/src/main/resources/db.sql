drop table if exists user;
create table user
(
    user_id     serial                  not null,
    user_name   varchar(30) primary key not null,
    user_passwd varchar(16)             not null,
    user_gold   integer,
    user_email  text,
    user_role   varchar(30)
);


drop table if exists enroll;
create table enroll
(
    enroll_id   serial  not null,
    user_id     integer not null,
    course_id   integer not null,
    enroll_gold integer not null,
    enroll_time timestamp
);


drop table if exists submit;
create table submit
(
    submit_id      serial primary key,
    pic_url        text,
    user_id        integer,
    user_name      varchar(50),
    course_id      integer,
    submit_index   integer, # 确定具体作业
    submit_type    varchar(10),
    submit_time    timestamp,
    submit_checked integer
);

drop table if exists notice;
create table notice
(
    notice_id      serial primary key,
    course_id      integer not null,
    user_id        integer not null,
    notice_content text,
    notice_time    timestamp,
    notice_email   boolean
);

drop table if exists lesson;
create table lesson
(
    lesson_id          varchar(50) primary key,
    lesson_index       integer,
    lesson_title       varchar(100),
    lesson_description text,
    lesson_viewcounts  integer,
    lesson_score       integer,
    chapter_id         integer
);

drop table if exists chapter;
create table chapter
(
    chapter_id    serial primary key,
    chapter_index integer      not null,
    chapter_title varchar(100) not null,
    course_id     integer      not null
);

drop table if exists course;
create table course
(
    course_id          serial primary key,
    course_title       varchar(100) not null,
    course_description text         not null,
    course_cover       varchar(500),
    course_price       integer      not null,
    course_score       integer, # 课程总分
    teacher_id         integer      not null,
    course_status      varchar(20),
    course_field       varchar(100),
    course_tag         varchar(100),
    course_bought      integer,
    course_comment_num integer
);

drop table if exists quiz;
create table quiz
(
    quiz_id      serial primary key,
    quiz_content text,
    course_id    integer not null,
    quiz_index   integer,
    quiz_time    timestamp
);

drop table if exists homework;
create table homework
(
    homework_id      serial primary key,
    homework_content text,
    course_id        integer not null,
    homework_index   integer,
    homework_time    timestamp
);

drop table if exists homework_grade;
create table homework_grade
(
    grade_id       serial primary key,
    user_id        integer,
    course_id      integer,
    homework_index integer,
    grade_score    integer,
    grade_time     timestamp,
    grade_content  text
);

drop table if exists video_grade;
create table video_grade
(
    grade_id      serial primary key,
    user_id       integer,
    course_id     integer,
    chapter_index integer,
    lesson_index  integer,
    grade_score   integer,
    grade_time    timestamp
);

drop table if exists quiz_grade;
create table quiz_grade
(
    grade_id    serial primary key,
    user_id     integer,
    course_id   integer,
    quiz_index  integer,
    grade_score integer,
    grade_time  timestamp
);

drop table if exists comment;
create table comment
(
    comment_id      serial primary key,
    user_id         integer   not null,
    course_id       integer   not null,
    comment_content text      not null,
    comment_time    timestamp not null
);

drop table if exists token;
create table token
(
    token_id        serial primary key,
    token_content   text not null,
    token_starttime long
);

drop table if exists pic;
create table pic
(
    pic_id  serial primary key,
    pic_url text
);

drop table if exists token_user;
create table token_user
(
    map_id   serial primary key,
    user_id  integer not null,
    token_id integer
);


drop table if exists field;
create table field(
                      field_id serial primary key,
                      field_name varchar(100),
                      field_full_name varchar(100)
);

INSERT INTO field(field_id, field_name, field_full_name) VALUES (null, 'CS', 'computer science');
INSERT INTO field(field_id, field_name, field_full_name) VALUES (null, 'ART', 'art');
INSERT INTO field(field_id, field_name, field_full_name) VALUES (null, 'ESP', 'e-sports');

drop table if exists tag;
create table tag(
                    tag_id serial primary key,
                    field_id integer,
                    tag_name varchar(100),
                    tag_color varchar(7)
);

drop table if exists order_info;
CREATE TABLE order_info (
                            id serial primary key ,
                            title varchar(256) DEFAULT NULL ,
                            order_no varchar(50) DEFAULT NULL ,
                            user_id integer DEFAULT NULL ,
                            total_fee integer DEFAULT NULL ,
                            order_status varchar(10) DEFAULT NULL
);

insert into user(user_id, user_name, user_passwd, user_gold, user_email, user_role)
values (null, 'admin', 'admin123', 0, '977482173@qq.com', 'admin');

INSERT INTO tag(tag_id, field_id, tag_name, tag_color) VALUES (null, 1, 'Artificial intelligence', '#F0F8FF');
INSERT INTO tag(tag_id, field_id, tag_name, tag_color) VALUES (null, 1, 'Computer vision', '#E6E6FA');
INSERT INTO tag(tag_id, field_id, tag_name, tag_color) VALUES (null, 1, 'Natural language processing', '#ADD8E6');
INSERT INTO tag(tag_id, field_id, tag_name, tag_color) VALUES (null, 1, 'Computer networks', '#87CEFA');
INSERT INTO tag(tag_id, field_id, tag_name, tag_color) VALUES (null, 1, 'Machine learning', '300BFFF');
INSERT INTO tag(tag_id, field_id, tag_name, tag_color) VALUES (null, 1, 'Computer architecture', '#1E90FF');
INSERT INTO tag(tag_id, field_id, tag_name, tag_color) VALUES (null, 1, 'Computer security', '#4682B4');
INSERT INTO tag(tag_id, field_id, tag_name, tag_color) VALUES (null, 1, 'Databases', '#7B68EE');
INSERT INTO tag(tag_id, field_id, tag_name, tag_color) VALUES (null, 1, 'Operating systems', '#6A5ACD');
INSERT INTO tag(tag_id, field_id, tag_name, tag_color) VALUES (null, 1, 'Software engineering', '#483D8B');
INSERT INTO tag(tag_id, field_id, tag_name, tag_color) VALUES (null, 1, 'Visualization', '#000080');
INSERT INTO tag(tag_id, field_id, tag_name, tag_color) VALUES (null, 1, 'Algorithms & complexity', '#00008B');

INSERT INTO tag(tag_id, field_id, tag_name, tag_color) VALUES (null, 11, 'LOL', '#E0FFFF');
INSERT INTO tag(tag_id, field_id, tag_name, tag_color) VALUES (null, 11, 'CSGO', '#00FFFF');
INSERT INTO tag(tag_id, field_id, tag_name, tag_color) VALUES (null, 11, 'DOTA2', '#7FFFD4');
INSERT INTO tag(tag_id, field_id, tag_name, tag_color) VALUES (null, 11, 'APEX', '#66CDAA');
INSERT INTO tag(tag_id, field_id, tag_name, tag_color) VALUES (null, 11, 'Genshin impact', '#AFEEEE');
INSERT INTO tag(tag_id, field_id, tag_name, tag_color) VALUES (null, 11, 'Minecraft', '#40E0D0');
INSERT INTO tag(tag_id, field_id, tag_name, tag_color) VALUES (null, 11, 'Celeste', '#00CED1');

