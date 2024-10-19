create table CSE284.HocSinhTieuHoc
(
    maHS              varchar(6)   null,
    HoatDongNgoaiKhoa varchar(255) null,
    constraint hocsinhtieuhoc_ibfk_1
        foreign key (maHS) references cse284.HocSinh (maHS)
            on delete cascade
);

create index maHS
    on CSE284.HocSinhTieuHoc (maHS);

