ALTER TABLE store_user ADD COLUMN machine_code VARCHAR (20);
ALTER TABLE store_user ADD access_token varchar(40) NULL;
ALTER TABLE store_user ADD refresh_token varchar(40) NULL;