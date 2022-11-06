CREATE TABLE IF NOT EXISTS tb_log_stock_movement (
  id SERIAL,
  operation varchar(30),
  stock_movement_id integer,
  creation_date TIMESTAMP,
  quantity integer not null,
  CONSTRAINT pk_tb_log_stock_movement PRIMARY KEY (id)
);

alter table tb_log_stock_movement add constraint fk_log_tb_order_stock_movement
foreign key (stock_movement_id) references tb_stock_movement (id);