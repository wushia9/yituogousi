CREATE TABLE `Counters` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `count` int(11) NOT NULL DEFAULT '1',
  `createdAt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatedAt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8
CREATE TABLE user (
                      user_id INT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
                      username VARCHAR(255) NOT NULL COMMENT '用户名',
                      wechat_openid VARCHAR(255) NOT NULL UNIQUE COMMENT '微信OpenID',
                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                      is_deleted BOOLEAN DEFAULT FALSE COMMENT '是否删除'
) COMMENT '用户表';

CREATE TABLE bill (
                      bill_id INT PRIMARY KEY AUTO_INCREMENT COMMENT '账单ID',
                      user_id INT NOT NULL COMMENT '用户ID',
                      amount DECIMAL(10, 2) NOT NULL COMMENT '金额',
                      type VARCHAR(100) NOT NULL COMMENT '类型',
                      payment_method VARCHAR(100) COMMENT '支付方式',
                      time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '时间',
                      note TEXT COMMENT '备注',
                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                      is_deleted BOOLEAN DEFAULT FALSE COMMENT '是否删除',
                      FOREIGN KEY (user_id) REFERENCES user(user_id)
) COMMENT '账单表';