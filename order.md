# 常用命令


>## Linux

### 解包和打包

	解包：tar zxvf FileName.tar　　
	打包：tar czvf FileName.tar DirName
### 新建删除文件夹和文件
	
	直接rm就可以了，不过要加两个参数-rf 即：rm -rf 目录名字

	-r 就是向下递归，不管有多少级目录，一并删除；

	-f 就是直接强行删除，不作任何提示的意思。

	例如：删除文件夹实例：

	rm -rf /var/log/httpd/access

	将会删除/var/log/httpd/access目录以及其下所有文件、文件夹

	(这里可能出现个问题，如果直接如此使用的话，系统可能不会授权这个操作，并出来 Permission denied(没有权限) 的提示。

	这时需要在 rm -rf 前补充 sudo 作为授权操作的许可，即：sudo rm -rf 文件夹的名字)

	例如：删除文件实例：

	rmdirm -f /var/log/httpd/access.log

	将会强制删除/var/log/httpd/access.log这个文件


	需要提醒的是：使用这个rm -rf的时候一定要格外小心，linux没有回收站的。

	当然，rm还有更多的其他参数和用法，man rm就可以查看了。


	还有一种方法也挺好用：

	mkdir a　　创建目录a

	rmdir a　　删除目录a注意：rmdir只能删除空目录，如果目录里面有文件，那么删除失败

### sftp 批量下载文件:

	get -r /NewMissionsBackup/2019/06/

### 执行.sh脚本命令
	
	 sh test-deploy.sh 201908301447 test-wjt

### 查找当前端口号
	
	sudo lsof -i:端口号

### 终极杀器
	
	kill -9

### ssh命令无法识别对方机器的时候

	可以用命令    ssh -p 端口号 账号@ip地址 

### 查找文件命令

	whereis 文件名
	或者  ps -ef|grep redis
	得到了进程号 xxxx 
	然后 ls -l /proc/xxxx/cwd


### 查看tomcat日志
	
	tail -f   

	-f 循环读取

	-q 不显示处理信息

	-v 显示详细的处理信息

	-c<数目> 显示的字节数

	-n<行数> 显示文件的尾部 n 行内容

	--pid=PID 与-f合用,表示在进程ID,PID死掉之后结束

	-q, --quiet, --silent 从不输出给出文件名的首部

	-s, --sleep-interval=S 与-f合用,表示在每次反复的间隔休眠S秒

### 从linux服务器上传文件到ftp
		
	put   /linux filepath    /ftp filepath

### ftp被动模式变主动模式

	passive mode on


### mac从远程服务器进行文件下载

	sftp服务器：get -r  文件名绝对路径或文件夹相对路径

	例子:get -r  20180822/

	linux服务器:

	scp 用户名@服务器域名:文件绝对路径或文件夹相对路径  本地文件夹绝对路径

	例子:scp -r ebao@mms1.ebaolife.net:/ebaolife/20180822  /Users/wujiantao/Documents/

	mac 从linux上传，下载文件时直接在终端运行命令就可以，不需要在linux上运行命令,这是因为已经用命令登陆linux服务器了，再在linux上运行的话会出现找不到目录的情况

### 使用服务器直接访问对方sftp服务器或ftp服务器

	sftp -P 端口号 用户名@域名或IP地址

	ftp 域名或IP地址

### 【nginx】修改nginx配置文件
	
	进入nginx.conf：

	vi /usr/local/nginx/conf/nginx.conf

	修改配置文件：
	
	不保存退出（不强制退出）： esc -->:q! --> 回车（强制退出）

	esc --> :q --> 回车 （不强制退出）

	保存退出：esc --> :wq --> 回车

	测试是否修改成功：

	/usr/local/nginx/sbin/nginx -t

	重新加载：

	/usr/local/nginx/sbin/nginx -s reload	

### Linux防火墙规则的查看、添加、删除和修改
	
	1、查看

	iptables -nvL –line-number

	-L查看当前表的所有规则，默认查看的是filter表，如果要查看NAT表，可以加上-t NAT参数。

	-n不对ip地址进行反查，加上这个参数显示速度会快很多。

	-v输出详细信息，包含通过该规则的数据包数量，总字节数及相应的网络接口。

	–line-number显示规则的序列号，这个参数在删除或修改规则时会用到。

	2、添加

	添加规则有两个参数：-A和-I。其中-A是添加到规则的末尾；-I可以插入到指定位置，没有指定位置的话默认插入到规则的首部

	当前规则：

	[root@test ~]# iptables -nL --line-number

	Chain INPUT (policy ACCEPT)

	num  target     prot opt source               destination

	1    DROP       all  --  192.168.1.1          0.0.0.0/0

	2    DROP       all  --  192.168.1.2          0.0.0.0/0

	3    DROP       all  --  192.168.1.4          0.0.0.0/0

	添加一条规则到尾部：

	[root@test ~]# iptables -A INPUT -s 192.168.1.5 -j DROP

	再插入一条规则到第三行，将行数直接写到规则链的后面：

	[root@test ~]# iptables -I INPUT 3 -s 192.168.1.3 -j DROP


	查看：

	[root@test ~]# iptables -nL --line-number

	Chain INPUT (policy ACCEPT)

	num  target     prot opt source               destination

	1    DROP       all  --  192.168.1.1          0.0.0.0/0

	2    DROP       all  --  192.168.1.2          0.0.0.0/0

	3    DROP       all  --  192.168.1.3          0.0.0.0/0

	4    DROP       all  --  192.168.1.4          0.0.0.0/0

	5    DROP       all  --  192.168.1.5          0.0.0.0/0

	可以看到192.168.1.3插入到第三行，而原来的第三行192.168.1.4变成了第四行。


	3、删除


	删除用-D参数

	删除之前添加的规则iptables -A INPUT -s 192.168.1.5 -j DROP：

 
	[root@test ~]# iptables -D INPUT -s 192.168.1.5 -j DROP

	有时候要删除的规则太长，删除时要写一大串，既浪费时间又容易写错，这时我们可以先使用--line-number找出该条规则的行号，再通过行号删除规则。

 
	[root@test ~]# iptables -nv --line-number

	iptables v1.4.7: no command specified

	Try `iptables -h' or 'iptables --help' for more information.

	[root@test ~]# iptables -nL --line-number

	Chain INPUT (policy ACCEPT)

	num  target     prot opt source               destination

	1    DROP       all  --  192.168.1.1          0.0.0.0/0

	2    DROP       all  --  192.168.1.2          0.0.0.0/0

	3    DROP       all  --  192.168.1.3          0.0.0.0/0

	删除第二行规则

	[root@test ~]# iptables -D INPUT 2

	4、修改

 
	修改使用-R参数


	先看下当前规则：

	[root@test ~]# iptables -nL --line-number

	Chain INPUT (policy ACCEPT)

	num  target     prot opt source               destination

	1    DROP       all  --  192.168.1.1          0.0.0.0/0

	2    DROP       all  --  192.168.1.2          0.0.0.0/0

	3    DROP       all  --  192.168.1.5          0.0.0.0/0

	将第三条规则改为ACCEPT：

	[root@test ~]# iptables -R INPUT 3 -j ACCEPT

	再查看下：

 
	[root@test ~]# iptables -nL --line-number

	Chain INPUT (policy ACCEPT)

	num  target     prot opt source               destination

	1    DROP       all  --  192.168.1.1          0.0.0.0/0

	2    DROP       all  --  192.168.1.2          0.0.0.0/0

	3    ACCEPT     all  --  0.0.0.0/0            0.0.0.0/0

	第三条规则的target已改为ACCEPT

### 设置Linux服务器下开放端口

一、查询所有开放端口信息

		netstat -anp 

二、关闭端口号:

		iptables -A OUTPUT -p tcp --dport 端口号-j DROP

三、打开端口号：

		iptables -A INPUT -ptcp --dport  8099 -j ACCEPT

		service iptables save 保存设置

四、以下是linux打开端口命令的使用方法:

		nc -lp 23 &(打开23端口，即telnet) 

		netstat -an | grep 23 (查看是否打开23端口) 

六、测试用例

五、linux打开端口命令每一个打开的端口，都需要有相应的监听程序才可以


方式一

CentOS：

1、开启防火墙 

    systemctl start firewalld

2、开放指定端口

      firewall-cmd --zone=public --add-port=1935/tcp --permanent

 命令含义：

--zone #作用域
--add-port=1935/tcp  #添加端口，格式为：端口/通讯协议
--permanent  #永久生效，没有此参数重启后失效

3、重启防火墙

      firewall-cmd --reload

4、查看端口号

	netstat -ntlp   //查看当前所有tcp端口·

	netstat -ntulp |grep 1935   //查看所有1935端口使用情况

方式二
 
开放端口:8080

	/sbin/iptables -I INPUT -p tcp --dport 8080 -j ACCEPT

 
方式三

	-A INPUT -m state --state NEW -m tcp -p tcp --dport 8080 -j ACCEPT

	service iptables restart

ubuntu 没有安装 iptables的参考如下 

	https://www.cnblogs.com/kxm87/p/9561054.html 



### Linux导入、导出 MySQL数据库命令

	一、导出数据库

	1、导出完整数据：表结构+数据

	Linux 下可以使用 mysqldump 命令来导出数据库，语法格式如下：

	mysqldump -u用户名 -p 数据库名 > 数据库名.sql

	以下命令可以导出 abc 数据库的数据和表结构：

	# /usr/local/mysql/bin/mysqldump -uroot -p abc > abc.sql

	按下回车键后会提示输入密码，输入即可。

	注意：mysqldump 命令路径是根据你 MySQL 安装路径决定的。

	2、只导出表结构

	如果你只需要导出 MySQL 的数据表结构，可以使用 -d 选项，格式如下：

	mysqldump -u用户名 -p -d 数据库名 > 数据库名.sql

	以下命令可以导出 abc 数据库的表结构：

	#/usr/local/mysql/bin/mysqldump -uroot -p -d abc > abc.sql

	二、导入数据库

	使用 mysql 命令导入

	使用 mysql 命令导入数据库格式如下：

	mysql -u用户名 -p 数据库名 < 数据库名.sql

	以下实例将备份的数据库 abc.sql 导入到数据库中：

	# mysql -uroot -p123456 < abc.sql

	使用 source 命令导入

	使用 source 命令需要我们先登录到 mysql 中，并创建一个空的数据库：

	mysql> create database abc;      # 创建数据库
	mysql> use abc;                  # 使用已创建的数据库 
	mysql> set names utf8;           # 设置编码
	mysql> source /home/abc/abc.sql  # 导入备份数据库

### 【nginx】修改nginx配置文件
	
进入nginx.conf：

	vi /usr/local/nginx/conf/nginx.conf

修改配置文件：

不保存退出（不强制退出）： 
	
	esc -->  :q! --> 回车（强制退出）

    esc --> :q --> 回车 （不强制退出）

保存退出：
	
	esc --> :wq --> 回车

测试是否修改成功：

	/usr/local/nginx/sbin/nginx -t

重新加载：

	/usr/local/nginx/sbin/nginx -s reload


### linux 安装redis 完整步骤

安装：

1.获取redis资源

	wget http://download.redis.io/releases/redis-4.0.8.tar.gz

2.解压

	tar xzvf redis-4.0.8.tar.gz

3.安装

	cd redis-4.0.8

	make

	cd src

	make install PREFIX=/usr/local/redis

4.移动配置文件到安装目录下

	cd ../

	mkdir /usr/local/redis/etc

	mv redis.conf /usr/local/redis/etc

5.配置redis为后台启动

	vi /usr/local/redis/etc/redis.conf //将daemonize no 改成daemonize yes

6.将redis加入到开机启动

	vi /etc/rc.local //在里面添加内容：/usr/local/redis/bin/redis-server /usr/local/redis/etc/redis.conf (意思就是开机调用这段开启redis的命令)

7.开启redis

	/usr/local/redis/bin/redis-server /usr/local/redis/etc/redis.conf 

8.将redis-cli,redis-server拷贝到bin下，让redis-cli指令可以在任意目录下直接使用

	cp /usr/local/redis/bin/redis-server /usr/local/bin/

	cp /usr/local/redis/bin/redis-cli /usr/local/bin/

9.设置redis密码

　　a.运行命令：

	redis-cli

　　b.查看现有的redis密码(可选操作，可以没有)

　　　　运行命令：

		config get requirepass 如果没有设置过密码的话运行结果会如下图所示

　　c.设置redis密码

　　　　运行命令：
	
		config set requirepass ****(****为你要设置的密码)，设置成功的话会返回‘OK’字样

　　d.测试连接

　　　　重启redis服务

		//（redis-cli -h 127.0.0.1 -p 6379 -a ****（****为你设置的密码））

　　　　输入 redis-cli 进入命令模式，使用 auth '*****' （****为你设置的密码）登陆　　　　　 

 10.让外网能够访问redis

　　a.配置防火墙:  

		firewall-cmd --zone=public --add-port=6379/tcp --permanent（开放6379端口）
		
		systemctl restart firewalld（重启防火墙以使配置即时生效）

　　　　 查看系统所有开放的端口：
		
		firewall-cmd --zone=public --list-ports	

 	b.此时 虽然防火墙开放了6379端口，但是外网还是无法访问的，因为redis监听的是127.0.0.1：6379，并不监听外网的请求。

 		（一）把文件夹目录里的redis.conf配置文件里的bind 127.0.0.1前面加#注释掉

　　　　 （二）命令：redis-cli连接到redis后，通过 

			config get  daemonize 
   
			config get  protected-mode 

   是不是都为no，如果不是，就用config set 配置名 属性 改为no
	
11.常用命令　　

启动redis:

	redis-server /usr/local/redis/etc/redis.conf

或者

	redis-server &

加上`&`号使redis以后台程序方式运行

	redis-server


停止redis:

	pkill redis	

卸载redis:
	
1.删除安装目录
	
	rm -rf /usr/local/redis

2.删除所有redis相关命令脚本

	rm -rf /usr/bin/redis-* 

3.删除redis解压文件夹

	rm -rf /root/download/redis-4.0.4

检测后台进程是否存在

	ps -ef |grep redis

检测6379端口是否在监听

	netstat -lntp | grep 6379

有时候会报redis已经启动的异常

关掉Redis,重启即可：

	1.redis-cli shutdown
 
	2.redis-server


### Linux系统下设置redis的密码

Linux系统下设置redis的密码：

1、进入redis操作的命令行

运行命令：
	
	redis-cli

2、查看现有的redis密码(可选操作，可以没有)

运行命令：

	config get requirepass

如果没有设置过密码的话运行结果会显示：config get requirepass


3、设置redis密码

运行命令：
	
	config set requirepass ****(****为你要设置的密码)，设置成功的话会返回‘OK’字样

 

4、重启redis服务

退出当前的命令行模式后运行命令：

	redis-cli -h 127.0.0.1 -p 6379 -a ****（****为你心设置的密码
	

### nginx完全卸载删除

第一步：输入以下指令全局查找nginx相关的文件：

	sudo find / -name nginx*

如果第一步报错，试试这个:

	sudo find / -name "nginx*"

第二步：删除查找出来的所有nginx相关文件

	sudo rm -rf file 此处跟查找出来的nginx文件

	全局查找往往会查出很多相关文件,但是前缀基本都是相同，后面不同的部分可以用*代替，方便快速删除。

举例说明：

	sudo rm -rf file /usr/local/nginx*




### linux日志查找命令

    1.cat -n test.log |grep "地形"  得到关键日志的行号

得到"地形"关键字所在的行号是102行. 此时如果我想查看这个关键字前10行和后10行的日志:

    cat -n test.log |tail -n +92|head -n 20

    tail -n +92表示查询92行之后的日志

    head -n 20 则表示在前面的查询结果里再查前20条记录

2

    sed -n '/2014-12-17 16:17:20/,/2014-12-17 16:17:36/p'  test.log

特别说明:上面的两个日期必须是日志中打印出来的日志,否则无效.

    grep '2014-12-17 16:17:20' test.log 来确定日志中是否有该时间点,以确保第4步可以拿到日志

这个根据时间段查询日志是非常有用的命令.

3

如果我们查找的日志很多,打印在屏幕上不方便查看, 有两个方法:

(1)使用more和less命令, 如: cat -n test.log |grep "地形" |more     这样就分页打印了,通过点击空格键翻页

(2)使用 >xxx.txt 将其保存到文件中,到时可以拉下这个文件分析.如:

    cat -n test.log |grep "地形"  >xxx.tx


### cat查找文件中的指定内容，并复制到新文件中进行查看

    cat error.log | grep 'adam' > ./test.log
以上命令为查询error.log文件中, 出现'adam'的日志行。并将查询到的日志复制并新建到当前目录下的test.log文件

### grep -c查找文件中的指定内容的出现次数
    grep -c 'adam' test.log
以上命令为查询test.log出现'adam'的次数

### tail方式显示 error.log 文件的最后 10 行
    tail error.log         # 默认显示最后 10 行

### 要跟踪名为 error.log 的文件的增长情况
    tail -f error.log
此命令显示 error.log 文件的最后 10 行。当将某些行添加至 error.log 文件时，tail 命令会继续显示这些行。 显示一直继续，直到您按下（Ctrl-C）组合键停止显示。



### 显示文件 error.log 的内容，从第38行至文件末尾
    tail -n +38 error.log

### tail方式显示 error.log 文件的最后 10 个字符
    tail -c 10 error.log

### tail方式查看 error.log 文件的最后300行, 适合快速查看大文件内容
    tail -n300 error.log

### less方式从 error.log 文件的110行开始查看
不显示行号
    
    less +110 error.log

显示行号

    less +110 -N error.log

### sed方式显示 error.log 文件的指定行数/指定范围

显示 error.log 文件的321536行

    sed -n '321536'p error.log

显示 error.log 文件的321536行至321580行
    
    sed -n '321536,321580'p error.log

显示 error.log 文件的321536行至最后

    sed -n '320123,$'p error.log














>## Mac Os

### mac下redis启动连接命令

	Mac 下redis启动命令

	redis-server

	Mac 下redis连接

	redis-cli


### mac下更改配置文件.bash_profile

	1、打开terminal(终端)

	2、cd ~ ( 进入当前用户的home目录)

	3、open .bash_profile (打开.bash_profile文件，如果文件不存在就  创建文件：touch .bash_profile  编辑文件：open -e bash_profile)

	4、直接更改弹出的.bash_profile文件内容

	5、command + s 保存文件，然后关闭

	6、在terminal(终端)中输入 source .bash_profile (使用刚才更新之后的内容)

### mac版IDEA常用快捷键
	

    1. command+N

    1）光标在代码编辑界面时,生成setter getter等方法

	2）光标在左侧工程结构时,创建新类/包等

    2. command+delete 删除当前行

    3. command+D 复制当前行

    4. command+alt+M 将当前选中到代码块抽取为方法

    5. command+E 最近浏览的文体

    6. alt+command+L 格式化代码

    7. alt+enter 生成局部变量（introduce local variable）

    8. double shift （快速按两次shift键）快速查找

    9. shift+alt+⬇️ 将当前代码整体下移一行（上移同理）

    10. shift+ctrl+R 编译并执行

    11. fn+左键 将光标定位到当前代码行最左侧(最右侧同理 )

    12. command+R 查找和替换

    13. command+alt+U 在当前类中，查看继承关系视图

    14. command+alt+左键/右键 将光标返回到上次查看代码的地方

    15. command+F12 查找当前类的方法

    16. shift*2 快速查找

    17. shift+F6 选中当前对象，重命名

    18. shift+alt+⬇️ 将当前代码整体下移一行


### Mac OS下使用rz和sz

Mac下使用scp命令可以完成文件的上传和下载功能，但如果开发机的登陆需要经过跳板机时，scp命令就没有办法正常使用了。

下面介绍一下Mac OS下如何配置rz，sz。

1.安装brew
安装方法自行百度。
Homebrew安装成功后，会自动创建目录 /usr/local/Cellar 来存放Homebrew安装的程序。

2. 安装iTerm2
iTerm是一个Mac下的终端工具，非常好用的命令行工具。Mac自带的终端是不支持lrzsz的，所以需要先下载支持它的iterms。
安装方法自行百度。

3. 安装lrzsz
lrzsz是一款在linux里可代替ftp上传和下载的程序。通过下载它来使用rz，sz。

    brew install lrzsz
4.安装wget
Mac默认不安装wget，可以通过brew安装。

    brew install wget
5.下载iterm2-zmodem
在iTerm2中使用Zmodem传输文件, 我们使用wget下载iterm2-zmodem。

    cd /usr/local/bin

wget https://raw.github.com/mmastrac/iterm2-zmodem/master/iterm2-send-zmodem.sh

wget https://raw.github.com/mmastrac/iterm2-zmodem/master/iterm2-recv-zmodem.sh

    chmod 777 /usr/local/bin/iterm2-*

如果命令需要权限，尝试在命令前面加上sudo。

7. 设置设置iTem2 添加trigger
菜单栏 iTerm --> Preferences


1.png
Profiles –> Default –> Advanced –> Triggers的Edit按钮


2.png
左下角点+后双击表格添加2行，如下图所示，完成后点击右下角close


3.png
添加triggers。

Regular expression	Action	Action
**B0100	Run Silent Coprocess	/usr/local/bin/iterm2-send-zmodem.sh
**B00000000000000	Run Silent Coprocess	/usr/local/bin/iterm2-recv-zmodem.sh
8.使用
重启iterm2，连接远程Linux，输入rz命令尝试一下。




### Maven环境变量配置（Mac）

1 下载Maven
地址：http://maven.apache.org/download.cgi

下载tar.gz文件，并将其解压到usr/local/maven目录下。

2 配置Maven环境变量
iTerm终端打开下面配置文件：

    vi ~/.bash_profile 
按 i 键进入编辑模式，输入如下内容：

    export M2_HOME=/usr/local/maven/apache-maven-3.3.3

    export PATH=$PATH:$M2_HOME/bin
点击 esc 按钮退出编辑模式，输入 :wq 保存退出。

输入如下命令使配置生效：

    source ~/.bash_profile 
重新打开一个终端，输入：

mvn -v
出现Maven版本、Maven home等信息，说明配置成功。

### mac与iphone信息不同步的问题
打开信息->偏好设置->注销后重新登陆



>## Git
	
### 多次提交失败后重置到某次提交
	
	reset branch to this commit->soft merge(软合并)

### 本地改动和远程仓库的改动有冲突时该怎么解决

	1.git stash,暂存本地改动
	2.git pull将远程改动拉取到本地仓库
	3.git stash pop将本地改动取出
	4.解决冲突

### 解决：Changes not staged for commit:

提交时加上参数：-a ，表示新增

    git commit -am "提交说明"

### mac上配置多git源

公司搭建私有git服务，平常开发都上传代码在上面。这个时候如果想用gitlab托管，就要进行一些配置（假设公司的git服务已经配置好）：

生成gitlab的sshkey
	
    ssh-keygen -t rsa -C "mr_ktl@163.com"
	
这个时候就会提示生成公钥、私钥路径：
	Enter file in which to save the key (/Users/ketl/.ssh/id_rsa): /Users/ketl/.ssh/id_rsa_gitlab

默认路径是~/.ssh/id_rsa,id_rsa存放的是公司私有git私钥，不能覆盖。重命名比如：id_rsa_github中。
添加私钥

    ssh-add ~/.ssh/id_rsa_github
    
修改config
        
    vi ~/.ssh/config

添加如下：
        # github
        Host github.com
        HostName github.com
        PreferredAuthentications publickey
        IdentityFile ~/.ssh/id_rsa_github

现在可以通过ssh -T git@github.com命令查看是否设置成功！！！




	
>## Windows 

### windows下修改host不生效的解决方法

修改hosts的host mapping不生效解决
 
在 windows 环境下，修改hosts，添加新host mapping，如下：
192.168.128. 128   test
执行ping 192.168.128.128 ，可以ping通
但执行ping test，死活都不通
 
最后才找到方法解决，mark下。
 
方法一：命令行(cmd)运行:
		
		ipconfig /flushdns     #清除DNS缓存内容。
		ps:ipconfig /displaydns    //显示DNS缓存内容

方法二：修改注册表：
		
		HKeyCurrentUser\SOFTWARE\Microsoft\Windows\CurrentVersion\Internet Settings，

新建：DnsCacheEnabled  0x0 (REG_DWORD)
DnsCacheTimeout 0x0 (REG_DWORD)
ServerInfoTimeOut 0x0 (REG_DWORD)这三个DWORD。
方法三：打开：控制面板-管理工具– 服务， 在其中找到“DNS Client”　将其停用并改为手动模式（做了这个后，运行ipconfig /flushdns就没必要了，也运行不成功）


方法四：

1. 打开本地连接-》Internet protocol version 4(TCP/IPv4)->Advanced->Wins tab
 
2. 选中Enable LMHOSTS lookup前面的checkbox，然后点击import LMHOSTS, 选择%WINDOWS%\system32\drivers\etc\lmhosts.sam（hosts）文件，就可以了。






