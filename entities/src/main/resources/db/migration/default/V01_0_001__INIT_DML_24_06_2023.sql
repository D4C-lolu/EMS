CREATE TYPE role as ENUM("EMPLOYEE","ADMIN");

CREATE TABLE public.user(
    user_id VARCHAR(60) NOT NULL UNIQUE,
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
    attendance_id VARCHAR(60) NOT NULL UNIQUE,
    attendance_date DATE NOT NULL,
    signin_time TIME,
    signout_time TIME,
    CONSTRAINT attendance_pk PRIMARY KEY (attendance_id),
    CONSTRAINT user_attendance_fk FOREIGN KEY(user_id) REFERENCES user(user_id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE

);

INSERT INTO public.user(user_id,first_name,last_name,email,password,user_token,user_role,date_added)
VALUES ("002020202","Admin","istrator", "admin@encentral.com", "admin","3rv2gy54yty54tvc4r","ADMIN",);