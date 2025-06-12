INSERT INTO admin (username, password)
VALUES ('admin',
        '$2a$10$65TBBu3kqxa69xUVBlo1g.Erd0.vHVCzRiQ4syU.CBOCW74kbKy3C');

INSERT INTO books (title, author, publisher, published_year, price, available)
VALUES ('자바의 정석', '남궁성', '도우출판', 2021, 35000, true),
       ('토비의 스프링', '이일민', '에이콘출판사', 2020, 45000, true),
       ('클린 코드', '로버트 마틴', '인사이트', 2019, 30000, false),
       ('모두의 알고리즘', '허원석', '길벗', 2022, 22000, true),
       ('Effective Java', 'Joshua Bloch', 'Addison-Wesley', 2018, 40000, false);

