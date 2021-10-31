drop table if exists gift_certificate;

CREATE TABLE IF NOT EXISTS gift_certificate
(
    id               SERIAL         NOT NULL,
    name             VARCHAR(300)   NOT NULL,
    description      TEXT           NULL DEFAULT NULL,
    price            DECIMAL(10, 2) NOT NULL,
    duration         INT            NOT NULL,
    create_date      timestamp      NOT NULL,
    last_update_date timestamp      NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date)
VALUES ('certificate of swimming pool', 'Visiting the pool', '20.5', 31, '2021-10-30T10:10:10-03:00',
        '2021-10-31T12:12:12-03:00'),
       ('certificate of SPA', 'Visiting the spa', '50.0', 40, '2021-08-30T8:10:10-03:00', '2021-09-30T9:12:12-03:00'),
       ('Certificate of Virtual Reality games', 'Visiting the Virtual Reality Place', '30.0', 10,
        '2021-07-20T14:10:10-03:00', '2021-07-25T16:12:12-03:00'),
       ('Certificate of computer club', 'back to 19xx', '20.0', 10, '2021-06-20T14:10:10-03:00',
        '2021-06-25T16:12:12-03:00'),
       ('Certificate of MASSAGE', 'Visiting the PLACE OF MASSAGE KINGS', '60.5', 30, '2020-07-20T14:10:10-03:00',
        '2020-07-25T16:12:12-03:00'),
       ('Certificate of fitness', 'WORLD OF FITNESS', '220', 90, '2021-02-20T14:10:10-03:00',
        '2020-02-25T16:12:12-03:00'),
       ('certificate of some food', 'You can buy some food for your cat', '15', 7, '2021-01-23T10:10:10-03:00',
        '2021-01-28T12:12:12-03:00'),
       ('Certificate of fitness place', 'Make your body become more slimmer than before', '250', 85,
        '2021-05-20T14:10:10-03:00', '2020-05-25T16:12:12-03:00');


drop table if exists tag;

CREATE TABLE IF NOT EXISTS tag
(
    id   SERIAL       NOT NULL,
    name VARCHAR(300) NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO tag(name)
VALUES ('SPA'),
       ('games'),
       ('massage'),
       ('swimmingPool'),
       ('fitness'),
       ('buns'),
       ('passage');

drop table if exists relationship_certificates_and_tags;

CREATE TABLE IF NOT EXISTS relationship_certificates_and_tags

(
    tag_id              BIGINT NOT NULL,
    gift_certificate_id BIGINT NOT NULL
);

INSERT INTO relationship_certificates_and_tags
VALUES (4, 1),
       (1, 2),
       (2, 3),
       (2, 4),
       (3, 5),
       (5, 6),
       (5, 7);

