create table CSE284.HocSinhTHPT
(
    maHS       varchar(6)   null,
    ToHop      varchar(3)   null,
    DiemToHop  float        null,
    DangVien   tinyint(1)   null,
    NguyenVong varchar(255) null,
    constraint hocsinhthpt_ibfk_1
        foreign key (maHS) references cse284.HocSinh (maHS)
            on delete cascade
);

create index maHS
    on CSE284.HocSinhTHPT (maHS);

