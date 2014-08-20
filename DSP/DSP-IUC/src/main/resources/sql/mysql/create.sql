
CREATE TABLE `security_organization` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(64) NOT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1DBDA7D2FCC01B00` (`parent_id`),
  CONSTRAINT `FK1DBDA7D2FCC01B00` FOREIGN KEY (`parent_id`) REFERENCES `security_organization` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

CREATE TABLE `security_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `email` varchar(128) DEFAULT NULL,
  `password` varchar(64) NOT NULL,
  `phone` varchar(32) DEFAULT NULL,
  `realname` varchar(32) NOT NULL,
  `salt` varchar(32) NOT NULL,
  `status` varchar(16) NOT NULL,
  `username` varchar(32) NOT NULL,
  `org_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKD607B56A453A1286` (`org_id`),
  CONSTRAINT `FKD607B56A453A1286` FOREIGN KEY (`org_id`) REFERENCES `security_organization` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;


CREATE TABLE `security_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

CREATE TABLE `security_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `priority` int(11) NOT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6DD3562BC592DFF7` (`role_id`),
  KEY `FK6DD3562B6ABDA3D7` (`user_id`),
  CONSTRAINT `FK6DD3562B6ABDA3D7` FOREIGN KEY (`user_id`) REFERENCES `security_user` (`id`),
  CONSTRAINT `FK6DD3562BC592DFF7` FOREIGN KEY (`role_id`) REFERENCES `security_role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;


CREATE TABLE `security_module` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(32) NOT NULL,
  `priority` int(11) NOT NULL,
  `sn` varchar(32) NOT NULL,
  `url` varchar(255) NOT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `type` varchar(20) NOT NULL,
  `dtype` varchar(10) NOT NULL,
  `sysid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;



CREATE TABLE `security_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(32) NOT NULL,
  `short_name` varchar(16) NOT NULL,
  `module_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKBA7A9C2E334A08F7` (`module_id`),
  CONSTRAINT `FKBA7A9C2E334A08F7` FOREIGN KEY (`module_id`) REFERENCES `security_module` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `security_role_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `permission_id` bigint(20) DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK679E223926E70397` (`permission_id`),
  KEY `FK679E2239C592DFF7` (`role_id`),
  CONSTRAINT `FK679E223926E70397` FOREIGN KEY (`permission_id`) REFERENCES `security_permission` (`id`),
  CONSTRAINT `FK679E2239C592DFF7` FOREIGN KEY (`role_id`) REFERENCES `security_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




CREATE TABLE `security_organization_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `priority` int(11) NOT NULL,
  `organization_id` bigint(20) DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK557CA4C3D069FDD7` (`organization_id`),
  KEY `FK557CA4C3C592DFF7` (`role_id`),
  CONSTRAINT `FK557CA4C3C592DFF7` FOREIGN KEY (`role_id`) REFERENCES `security_role` (`id`),
  CONSTRAINT `FK557CA4C3D069FDD7` FOREIGN KEY (`organization_id`) REFERENCES `security_organization` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

 

CREATE TABLE `security_outsys_join` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `sys_moudleid` bigint(20) NOT NULL,
    `appkey` varchar(100) NOT NULL,
    `appid` varchar(100) NOT NULL,
    `joinip` varchar(500) NOT NULL,
    `create_time` datetime DEFAULT NULL,
     PRIMARY KEY (`id`)
);

create table security_sort(
   `id` bigint(20) NOT NULL AUTO_INCREMENT,
   `name` varchar(50) NOT NULL,
    PRIMARY KEY (`id`)
);

create table security_sys_sort(
   `id` bigint(20) NOT NULL AUTO_INCREMENT,
   `sysid` bigint(20) NOT NULL,
   `sortid` bigint(20) NOT NULL,
    PRIMARY KEY (`id`)
);
 
 
INSERT INTO `security_sort` VALUES(1,'工作指导');
INSERT INTO `security_sort` VALUES(2,'内容管理');
INSERT INTO `security_sort` VALUES(3,'商城管理');
INSERT INTO `security_sort` VALUES(4,'数据报表');
INSERT INTO `security_sort` VALUES(5,'用户平台');
INSERT INTO `security_sort` VALUES(6,'金立调查问卷');
INSERT INTO `security_sort` VALUES(7,'portal');
 
INSERT INTO `security_module` VALUES (1,'所有模块的根节点，不能删除。','根模块',1,'Root','#',NULL,'i','ROOT',2);
 INSERT INTO `security_module` VALUES (2,'用户中心内部模块，不能删除。','内部根模块',1,'iRoot','#',1,'i','ROOT',0);
 INSERT INTO `security_module` VALUES(3,'外部系统模块，不能删除。','外部系统根模块',1,'oRoot','#',1,'o','ROOT',0);
 INSERT INTO `security_module` VALUES (4,'安全管理:包含权限管理、模块管理等。','安全管理',99,'Security','#',2,'i','MENU',2);
 INSERT INTO `security_module` VALUES (5,'','用户管理',99,'User','/management/security/user/list',4,'i','MENU',2);
 INSERT INTO `security_module` VALUES (6,'','角色管理',99,'Role','/management/security/role/list',4,'i','MENU',2);
 INSERT INTO `security_module` VALUES (7,'','模块管理',99,'Module','/management/security/module/tree',4,'i','MENU',2);
 INSERT INTO `security_module` VALUES (8,'','组织管理',99,'Organization','/management/security/organization/tree',4,'i','MENU',2);
 INSERT INTO `security_module` VALUES (9,'','用户中心模块管理',99,'iModule','/management/security/module/tree',7,'i','MENU',2);
 INSERT INTO `security_module` VALUES (10,'','外部系统管理',99,'oManager','/management/security/module/olist',7,'i','MENU',2);
 INSERT INTO `security_module` VALUES (11,'','系统管理',99,'oSys','/management/security/module/olist',10,'i','MENU',2);
 INSERT INTO `security_module` VALUES (12,'','模块管理',99,'oModule','/management/security/module/tree?type=o',10,'i','MENU',2);
 INSERT INTO `security_module` VALUES (13,'','系统接入管理',99,'sysJoin','/management/security/join/list',4,'i','MENU',2);
INSERT INTO `security_module` VALUES (14,'','缓存管理', '99','CacheManage','/management/security/cacheManage/index',4,'i','MENU',2);


INSERT INTO `security_permission` VALUES (1,'','增','save',5);
INSERT INTO `security_permission` VALUES(2,'','删','delete',5),(3,'','查','view',5);
INSERT INTO `security_permission` VALUES(4,'分配、撤销角色','授权','assign',5);
INSERT INTO `security_permission` VALUES(6,'重置密码、更新状态','重置','reset',5);
INSERT INTO `security_permission` VALUES(7,'','增','save',6);
INSERT INTO `security_permission` VALUES(8,'','删','delete',6);
INSERT INTO `security_permission` VALUES(9,'','查','view',6);
INSERT INTO `security_permission` VALUES(10,'','增','save',7);
INSERT INTO `security_permission` VALUES(11,'','删','delete',7);
INSERT INTO `security_permission` VALUES(12,'','查','view',7);
INSERT INTO `security_permission` VALUES(13,'','增','save',8);
INSERT INTO `security_permission` VALUES(14,'','删','delete',8);
INSERT INTO `security_permission` VALUES(15,'','查','view',8);
INSERT INTO `security_permission` VALUES(16,'','增','save',9);
INSERT INTO `security_permission` VALUES(17,'','删','delete',9);
INSERT INTO `security_permission` VALUES(18,'','查','view',9);
INSERT INTO `security_permission` VALUES(19,'','增','save',11);
INSERT INTO `security_permission` VALUES(20,'','删','delete',11);
INSERT INTO `security_permission` VALUES(21,'','查','view',11);
INSERT INTO `security_permission` VALUES(22,'','增','save',12);
INSERT INTO `security_permission` VALUES(23,'','删','delete',12);
INSERT INTO `security_permission` VALUES(24,'','查','view',12);
INSERT INTO `security_permission` VALUES(25,'','增','save',13);
INSERT INTO `security_permission` VALUES(26,'','删','delete',13);
INSERT INTO `security_permission` VALUES(27,'','查','view',13);
INSERT INTO `security_permission` VALUES(28,'','查','view',4);
INSERT INTO `security_permission` VALUES(30,'','查','view',10);
INSERT INTO `security_permission` VALUES (31, '', '查', 'view', 14);
INSERT INTO `security_permission` VALUES (32, '', '改', 'edit', 14);
INSERT INTO `security_permission` VALUES (33, '分配、撤销角色', '授权', 'assign', 8);
INSERT INTO `security_permission` VALUES ('134', '', '改', 'edit', '5');
INSERT INTO `security_permission` VALUES ('135', '', '改', 'edit', '6');
INSERT INTO `security_permission` VALUES ('136', '', '改', 'edit', '8');
INSERT INTO `security_permission` VALUES ('137', '', '改', 'edit', '7');
INSERT INTO `security_permission` VALUES ('138', '', '改', 'edit', '9');
INSERT INTO `security_permission` VALUES ('139', '', '改', 'edit', '11');
INSERT INTO `security_permission` VALUES ('140', '', '改', 'edit', '12');
INSERT INTO `security_permission` VALUES ('141', '', '改', 'edit', '13');

INSERT INTO `security_organization` VALUES (1,'不能删除。','根组织',NULL),(2,'','根组织',null);
 
INSERT INTO `security_user` VALUES (1,'2012-08-03 14:58:38','test@gionee.com','86ddd168b0c218ea7c7e77c7766a59bb81262419','13888888888','超级管理员','6d37bd5678f411f5','enabled','admin',1);

