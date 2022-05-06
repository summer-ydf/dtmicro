### 开启MySQL的binlog

show variables like 'log_bin';

OFF：关闭,ON：开启

命令：mysql --help | grep 'Default options' -A 1

Default options are read from the following files in the given order:
/etc/my.cnf /etc/mysql/my.cnf /usr/etc/my.cnf ~/.my.cnf 

vim /etc/my.cnf

#### 配置信息
log-bin=mysql-bin  # 开启binlog
binlog-format=ROW  # 选择ROW模式
server_id=1        # 配置MySQL replaction需要定义，不和Canal的slaveId重复即可

#### 重启MySQL
systemctl stop mysqld.service
systemctl status mysqld.service
systemctl start mysqld.service


https://blog.csdn.net/qq_35551748/article/details/106410066

重启服务之后,进入mysql命令行新增并授权 canal 链接 mysql账号具有作为 mysql slave 的权限


SHOW VARIABLES LIKE 'validate_password%';

SELECT * FROM `user`


mysql> set global validate_password_length=0;
ERROR 1193 (HY000): Unknown system variable 'validate_password_length'
mysql> set global validate_password.policy=0;
Query OK, 0 rows affected (0.00 sec)

mysql> set global validate_password.length=1;
Query OK, 0 rows affected (0.00 sec)

mysql> CREATE USER canal IDENTIFIED BY 'canal';
Query OK, 0 rows affected (0.01 sec)

mysql> GRANT SELECT,UPDATE,INSERT,DELETE,REPLICATION SLAVE, REPLICATION CLIENT ON *.* TO 'canal'@'%';
Query OK, 0 rows affected (0.01 sec)

mysql> FLUSH PRIVILEGES;
Query OK, 0 rows affected (0.01 sec)


# 创建用户canal，密码为canal
CREATE USER canal IDENTIFIED BY 'canal';
# canal用户授权
GRANT SELECT,UPDATE,INSERT,DELETE,REPLICATION SLAVE, REPLICATION CLIENT ON *.* TO 'canal'@'%';
# 刷新权限
FLUSH PRIVILEGES;




