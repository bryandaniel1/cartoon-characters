-- cartoons schema and tables creation

DROP SCHEMA IF EXISTS cartoons_schema CASCADE;

CREATE SCHEMA cartoons_schema

CREATE TABLE cartoons_schema.cartoon (
	cartoon_id BIGSERIAL,
	title VARCHAR(30),
	description VARCHAR(1000),
	CONSTRAINT PK_cartoon_id PRIMARY KEY (cartoon_id),
    	CONSTRAINT UK_cartoon_title UNIQUE (title)
);

CREATE TABLE cartoons_schema.cartoon_location (
	location_id BIGSERIAL,
	location_name VARCHAR(50),
	description VARCHAR(1000),
	cartoon_id BIGINT NOT NULL,
	CONSTRAINT PK_location_id PRIMARY KEY (location_id),
	CONSTRAINT FK_cartoon_id FOREIGN KEY (cartoon_id) REFERENCES cartoons_schema.cartoon (cartoon_id)
);

CREATE TABLE cartoons_schema.cartoon_character (
	character_id BIGSERIAL,
	character_name VARCHAR(50),
	description VARCHAR(1000),
	character_home BIGINT NOT NULL,
	CONSTRAINT PK_character_id PRIMARY KEY (character_id),
	CONSTRAINT FK_character_home FOREIGN KEY (character_home) REFERENCES cartoons_schema.cartoon_location (location_id)
);

CREATE TABLE cartoons_schema.character_quote (
	quote_id BIGSERIAL,
	character_id BIGINT NOT NULL,
	quote VARCHAR(100),
	CONSTRAINT PK_quote_id PRIMARY KEY (quote_id),
	CONSTRAINT FK_character_id FOREIGN KEY (character_id) REFERENCES cartoons_schema.cartoon_character (character_id)
);

CREATE TABLE cartoons_schema.cartoon_picture (
	picture_id BIGSERIAL,
	picture_location VARCHAR(100) NOT NULL,
	cartoon_id BIGINT,
	location_id BIGINT,
	character_id BIGINT,
	CONSTRAINT PK_picture_id PRIMARY KEY (picture_id),
	CONSTRAINT FK_picture_cartoon_id FOREIGN KEY (cartoon_id) REFERENCES cartoons_schema.cartoon (cartoon_id),
	CONSTRAINT FK_picture_location_id FOREIGN KEY (location_id) REFERENCES cartoons_schema.cartoon_location (location_id),
	CONSTRAINT FK_picture_character_id FOREIGN KEY (character_id) REFERENCES cartoons_schema.cartoon_character (character_id)
);

CREATE TABLE cartoons_schema.gender (
	gender VARCHAR(1) NOT NULL,
	description VARCHAR(20) NOT NULL,
	CONSTRAINT PK_gender PRIMARY KEY (gender)
);

CREATE TABLE cartoons_schema.character_demographic (
	demographic_id BIGSERIAL,
	gender VARCHAR(1) NOT NULL,
	villain BOOLEAN,
	character_id BIGINT,
	CONSTRAINT PK_demographic_id PRIMARY KEY (demographic_id),
	CONSTRAINT FK_demographic_gender FOREIGN KEY (gender) REFERENCES cartoons_schema.gender (gender),
	CONSTRAINT FK_demographic_character_id FOREIGN KEY (character_id) REFERENCES cartoons_schema.cartoon_character (character_id)
);

DROP USER IF EXISTS cartoons_schema;

CREATE USER cartoons_schema with password 'cartoon_user_password';

GRANT USAGE ON SCHEMA cartoons_schema TO cartoons_schema;

GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA cartoons_schema TO cartoons_schema;

GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA cartoons_schema TO cartoons_schema;
