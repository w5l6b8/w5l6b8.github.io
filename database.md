# 常用数据库语句和配置



>## MYSQL

### mysql日期修改

	UPDATE t_mcinslip mci,t_customer cr 

	SET mci.validity_date = date_add(mci.validity_date,INTERVAL -1 DAY) 

	WHERE mci.state = '99' 

	AND mci.crId = cr.crId AND cr.agtId = '2c94819460c9534a0160ce8538fc4b71' 

	AND mci.mcisId = '2c948195628aa91801628ae354660ee9'

### 查重和去重语句

	SELECT user_id,

	count(*) as num

	FROM 表名

	WHERE 条件  GROUP BY user_id HAVING count(*)>1;

	select user_id,
	
	create_date,

	row_number() over(partition by user_id order by create_date desc) as date_n 

	FROM 表名 

	WHERE date_n=1                 

	AND 条件; 

### mysql 跳过密码直接登陆

	修改MySQL的登录设置：

	vi /etc/my.cnf

	在[mysqld]的段中加上一句：skip-grant-tables

	例如：

	[mysqld]

	datadir=/var/lib/mysql

	socket=/var/lib/mysql/mysql.sock

	skip-name-resolve

	skip-grant-tables

	保存并且退出vi。

	3．重新启动mysqld

 	/etc/init.d/mysqld restart

	Stopping MySQL: [ OK ]

	Starting MySQL: [ OK ]

	4．登录并修改MySQL的root密码

	 /usr/bin/mysql

	Welcome to the MySQL monitor. Commands end with ; or \g.

	Your MySQL connection id is 3 to server version: 3.23.56

	Type ‘help;’ or ‘\h’ for help. Type ‘\c’ to clear the buffer.

	mysql> USE mysql ;

	Reading table information for completion of table and column names

	You can turn off this feature to get a quicker startup with -A

	Database changed

	mysql> UPDATE user SET Password = password ( ‘new-password’ ) WHERE User = ‘root’ ;

	Query OK, 0 rows affected (0.00 sec)

	Rows matched: 2 Changed: 0 Warnings: 0

	mysql> flush privileges ;

	Query OK, 0 rows affected (0.01 sec)

	mysql> quit

	Bye
	
	5．将MySQL的登录设置修改回来

 	vi /etc/my.cnf

	将刚才在[mysqld]的段中加上的skip-grant-tables删除

	保存并且退出vi。

	6．重新启动mysqld

 	/etc/init.d/mysqld restart

	topping MySQL: [ OK ]

	Starting MySQL: [ OK ]

### Table is marked as crashed and should be repaired 解决办法

遇到这个问题几个敲命令轻松搞定

1、首先进入mysql命令台：

mysql -u root -p 回车  输入密码

2、查询所有的库

mysql> show databases; 

3、进入数据库“eduyun_2015_sp1”是库名

mysql> use eduyun_2015_sp1;

4、check table newabout（newabout--出现错误的表）用来检查出现问题的表的状态，出现错误就正常

5、然后用repair table newabout

6、再用check table newabout 检查一下就ok了
 
7、ok 搞定

### mysql 查询表字段信息（字段名、描述、类型、长度）

SELECT 
  COLUMN_NAME 列名, 
  COLUMN_TYPE 数据类型, 
    if(IS_NULLABLE='YES','是','否') 是否为空,
  COLUMN_DEFAULT 默认值,
  COLUMN_COMMENT 备注
FROM 
 INFORMATION_SCHEMA.COLUMNS 
where 
table_schema ='guns'    -- 数据库名称 
AND 
table_name  = 'sys_user'    -- 表名

