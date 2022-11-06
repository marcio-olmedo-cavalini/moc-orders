CREATE TABLE IF NOT EXISTS tb_stock_movement (
  id SERIAL,
  creation_date TIMESTAMP,
  item_id integer NOT NULL,
  quantity integer not null,
  CONSTRAINT pk_stock_movement PRIMARY KEY (id)
);
alter table tb_stock_movement add constraint fk_item_stock_movement
foreign key (item_id) references tb_item (id);