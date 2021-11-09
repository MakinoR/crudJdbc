DROP TABLE IF EXISTS test_table;

CREATE TABLE test_table
(
   id INT NOT NULL AUTO_INCREMENT,
   title VARCHAR(50) NOT NULL,
   content VARCHAR(300) NOT NULL,
   PRIMARY KEY(id)
);