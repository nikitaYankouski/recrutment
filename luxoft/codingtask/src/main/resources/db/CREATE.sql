CREATE SEQUENCE IF NOT EXISTS csv_id_seq;

CREATE TABLE "public"."csv" (
    "id" int8 NOT NULL DEFAULT nextval('csv_id_seq'::regclass),
    "primary_key" text,
    "name" text,
    "updated_timestamp" timestamp,
    "description" text,
    PRIMARY KEY ("id")
);