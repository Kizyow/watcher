-- Tickets Database

CREATE TABLE IF NOT EXISTS ticket
(
    id_ticket     integer(11) PRIMARY KEY,
    id_user       bigint NOT NULL,
    reason        varchar(255),
    date_creaton  date,
    category_name varchar(50)
);

CREATE TABLE IF NOT EXISTS category
(
    category_name varchar(50) PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS staff
(
    id_staff bigint PRIMARY KEY,
    rank     integer(11)
);

CREATE TABLE IF NOT EXISTS archive
(
    id_archive   integer(11) PRIMARY KEY,
    date_archive date,
    id_ticket    integer(11),
    id_staff     bigint
);

CREATE TABLE IF NOT EXISTS logs
(
    message_id      bigint PRIMARY KEY,
    message_content varchar(255),
    message_date    date,
    user_id         bigint,
    id_ticket       integer(11)
);

CREATE TABLE IF NOT EXISTS ticket_taken
(
    id_ticket integer(11),
    id_staff  bigint,
    PRIMARY KEY (id_ticket, id_staff)
);

ALTER TABLE ticket
    ADD CONSTRAINT ticket_categoryFk FOREIGN KEY (category_name) REFERENCES category (category_name) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE ticket_taken
    ADD CONSTRAINT ticketTaken_idTicketFk FOREIGN KEY (id_ticket) REFERENCES ticket (id_ticket) ON DELETE CASCADE ON UPDATE CASCADE,
    ADD CONSTRAINT ticketTaken_idStaffFk FOREIGN KEY (id_staff) REFERENCES staff (id_staff) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE archive
    ADD CONSTRAINT archive_idStaffFk FOREIGN KEY (id_staff) REFERENCES staff (id_staff) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE logs
    ADD CONSTRAINT logs_idTicketFk FOREIGN KEY (id_ticket) REFERENCES ticket (id_ticket) ON DELETE CASCADE ON UPDATE CASCADE;