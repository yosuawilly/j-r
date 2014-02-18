create table "admin"(
id_admin serial not null primary key,
username varchar(10),
"password" varchar(6)
);

create table bab(
id_bab serial not null primary key,
label_bab varchar(10) unique,
judul_bab varchar(30)
);

create table materi(
id_materi serial not null primary key,
judul varchar(30) not null unique,
isi_materi text,
id_bab int references bab on update cascade on delete cascade,
semester varchar(1) CHECK (semester='1' or semester='2'),
url varchar(50)
);

create table siswa(
id_siswa varchar(10) not null primary key,
username varchar(10) not null,
"password" varchar(6) not null,
nama varchar(30),
jenis_kelamin varchar(10),
alamat text
);

create table tugas(
id_tugas serial not null primary key,
judul_tugas varchar(30) not null,
catatan varchar(30)
);

create table soal_tugas(
isi_soal text,
id_tugas int references tugas on update cascade on delete cascade
);

create table table_version(
id_table_version serial not null primary key,
nama_table varchar(20) not null,
"version" varchar(10) not null
);

create table upload_tugas(
id_upload serial not null primary key,
id_siswa varchar(10) references siswa on update cascade on delete cascade,
id_tugas int references tugas on update cascade on delete cascade,
nama_file varchar(50) not null,
tgl_upload date not null
);

create table quiz(
id_quiz serial not null primary key,
soal_quiz varchar(30)
);

create table jawaban_quiz(
id_jawaban serial not null primary key,
jawaban varchar(30),
id_quiz int references quiz on update cascade on delete cascade,
benar boolean
);