CREATE TYPE role as ENUM("EMPLOYEE","ADMIN");

CREATE TABLE public.user(
    user_id SERIAL NOT NULL UNIQUE,
    first_name VARCHAR(60) NOT NULL,
    last_name VARCHAR(60) NOT NULL,
    email VARCHAR(60) NOT NULL UNIQUE,
    password VARCHAR(60),
    user_token VARCHAR(50) NOT NULL UNIQUE,
    user_role role NOT NULL,
    date_added DATE NOT NULL,
    CONSTRAINT user_pk PRIMARY KEY (user_id)
);

CREATE TABLE public.attendance(
    attendance_id SERIAL NOT NULL UNIQUE,
    attendance_date DATE NOT NULL,
    signin_time TIME,
    signout_time TIME,
    CONSTRAINT attendance_pk PRIMARY KEY (attendance_id),
    CONSTRAINT user_attendance_fk FOREIGN KEY(user_id) REFERENCES user(user_id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE

);