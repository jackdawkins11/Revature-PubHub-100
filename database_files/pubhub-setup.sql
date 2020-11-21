drop table if exists books;

create table books (
  isbn_13 varchar (13) primary key,
  title varchar (100),
  author varchar (80),
  publish_date date,
  price decimal (6,2),
  content bytea
);

