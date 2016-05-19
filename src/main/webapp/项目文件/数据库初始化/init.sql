CREATE TABLE `user` (
`id`  int(11) NOT NULL AUTO_INCREMENT COMMENT '用户编号' ,
`name`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名称' ,
`age`  int(3) NOT NULL COMMENT '用户年龄' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='用户表'
AUTO_INCREMENT=126
ROW_FORMAT=COMPACT
;

CREATE TABLE `sys_conf` (
`id`  int(11) NOT NULL AUTO_INCREMENT COMMENT '唯一标示' ,
`confCode`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '系统参数key' ,
`confValue`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '系统参数value' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='系统参数表'
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;

insert into sys_conf(confCode,confValue) values('username','郑山');