create table CSE284.HocSinh
(
    maHS     varchar(6)   not null
        primary key,
    Ten      varchar(255) null,
    Lop      varchar(5)   null,
    NgaySinh date         null,
    GioiTinh varchar(5)   null,
    DiaChi   varchar(255) null,
    DiemTB   float        null,
    HanhKiem varchar(10)  null,
    Truong   varchar(255) null
);

