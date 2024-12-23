CREATE TABLE IF NOT EXISTS event (
   id SERIAL,
   type varchar(250) NOT NULL,
   created_on timestamp NOT NULL,
   device_id INT NOT NULL,
   user_id INT NOT NULL,
   version INT,
   PRIMARY KEY (id)
);
