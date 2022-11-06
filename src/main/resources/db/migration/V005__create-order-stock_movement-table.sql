CREATE TABLE IF NOT EXISTS tb_order_stock_movement (
  id SERIAL,
  order_id integer,
  stock_movement_id integer,
  creation_date TIMESTAMP,
  quantity_attended integer not null,
  CONSTRAINT pk_tb_order_stock_movement PRIMARY KEY (id)
);

alter table tb_order_stock_movement add constraint fk_order_tb_order_stock_movement
foreign key (order_id) references tb_order (id);

alter table tb_order_stock_movement add constraint fk_stock_tb_order_stock_movement
foreign key (stock_movement_id) references tb_stock_movement (id);