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

重启服务之后,进入mysql命令行新增并授权 canal 链接 mysql账号具有作为 mysql slave 的权限




