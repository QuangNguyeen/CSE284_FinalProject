create table CSE284.HocSinhTHCS
(
    maHS        varchar(6)   null,
    HuongNghiep varchar(255) null,
    DoanVien    tinyint(1)   null,
    constraint hocsinhthcs_ibfk_1
        foreign key (maHS) references cse284.HocSinh (maHS)
            on delete cascade
);

create index maHS
    on CSE284.HocSinhTHCS (maHS);

