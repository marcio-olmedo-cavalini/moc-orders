CREATE TABLE IF NOT EXISTS tb_order (
  id SERIAL,
  creation_date TIMESTAMP,
  item_id integer NOT NULL,
  quantity integer not null,
  user_id integer not null,
  order_status varchar(50),
  CONSTRAINT pk_tb_order PRIMARY KEY (id)
);

alter table tb_order add constraint fk_item_tb_order
foreign key (item_id) references tb_item (id);

alter table tb_order add constraint fk_user_id_tb_order
foreign key (user_id) references tb_user (id);