drop table if exists book_tags;

create table book_tags (
	isbn_13 varchar(13) references books(isbn_13),
	tag_name varchar(100)
);

