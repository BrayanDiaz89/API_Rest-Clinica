
create table usuarios(
                    id bigint not null auto_increment,
                    login varchar(100) not null,
                    password varchar(300) not null,

                    primary key(id)

                    );