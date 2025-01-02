<p align="center">
	<a href="http://www.datagear.tech"><img src="datagear-web/src/main/resources/org/datagear/web/static/theme/blue/image/logo.png" alt="DataGear" /></a>
</p>
<h1 align="center">
	数据可视化分析平台
</h1>
<h2 align="center">
	自由制作任何您想要的数据看板
</h2>

# 简介

DataGear是一款开源免费的数据可视化分析平台，自由制作任何您想要的数据看板，支持接入SQL、CSV、Excel、HTTP接口、JSON等多种数据源。

## [DataGear 4.5.1 已发布，欢迎官网下载使用！](http://www.datagear.tech)

## 系统特点

- 友好接入的数据源
<br>支持运行时接入任意提供JDBC驱动的数据库，包括MySQL、Oracle、PostgreSQL、SQL Server等关系数据库，以及Elasticsearch、ClickHouse、Hive等大数据引擎

- 多样动态的数据集
<br>支持创建SQL、CSV、Excel、HTTP接口、JSON数据集，并可设置为动态的参数化数据集，可定义文本框、下拉框、日期框、时间框等类型的数据集参数，灵活筛选满足不同业务需求的数据

- 强大丰富的数据图表
<br>数据图表可聚合绑定多个不同格式的数据集，轻松定义同比、环比图表，内置折线图、柱状图、饼图、地图、雷达图、漏斗图、散点图、K线图、桑基图等70+开箱即用的图表，并且支持自定义图表配置项，支持编写和上传自定义图表插件

- 自由开放的数据看板
<br>数据看板采用原生的HTML网页作为模板，支持导入任意HTML网页，支持以可视化方式进行看板设计和编辑，也支持使用JavaScript、CSS等web前端技术自由编辑看板源码，内置丰富的API，可制作图表联动、数据钻取、异步加载、交互表单等个性化的数据看板。

## 官网

[http://www.datagear.tech](http://www.datagear.tech)

## 文档

[http://www.datagear.tech/documentation](http://www.datagear.tech/documentation)

## 示例

[https://my.oschina.net/u/4035217](https://my.oschina.net/u/4035217)

## 源码

Gitee：[https://gitee.com/datagear/datagear](https://gitee.com/datagear/datagear)

Github：[https://github.com/datageartech/datagear](https://github.com/datageartech/datagear)

## 交流

QQ群：[916083747（已满）](https://jq.qq.com/?_wv=1027&k=ODxiKOOy)、[1128360199（已满）](https://jq.qq.com/?_wv=1027&k=XkQ4ARMY)、[541252568](https://jq.qq.com/?_wv=1027&k=F7dwDVLO)

留言板：[http://www.datagear.tech/messageboard](http://www.datagear.tech/messageboard/)

## 界面

数据源管理

![screenshot/datasource-manage.png](screenshot/datasource-manage.png)

SQL数据集

![screenshot/add-sql-dataset.png](screenshot/add-sql-dataset.png)

看板编辑

![screenshot/dashboard-visual-mode.gif](screenshot/dashboard-visual-mode.gif)

看板展示

![screenshot/template-006-dg.png](screenshot/template-006-dg.png)

看板展示-图表联动

![screenshot/dashboard-map-chart-link.gif](screenshot/dashboard-map-chart-link.gif)

看板展示-实时图表

![screenshot/dashboard-time-series-chart.gif](screenshot/dashboard-time-series-chart.gif)

看板展示-钻取

![screenshot/dashboard-map-chart-hierarchy.gif](screenshot/dashboard-map-chart-hierarchy.gif)

看板展示-表单

![screenshot/dashboard-form.gif](screenshot/dashboard-form.gif)

看板展示-联动异步加载图表

![screenshot/dashboard-link-load-chart.gif](screenshot/dashboard-link-load-chart.gif)


## 技术栈（前后端一体）

- 后端
  <br>
  Spring Boot、Mybatis、Freemarker、Derby、Jackson、Caffeine、Spring Security

- 前端
  <br>
  jQuery、Vue3、PrimeVue、CodeMirror、ECharts、DataTables

## 模块介绍

- datagear-analysis
  <br>数据分析底层模块，定义数据集、图表、看板API

- datagear-connection
  <br>数据库连接支持模块，定义可从指定目录加载JDBC驱动、新建连接的API

- datagear-dataexchange
  <br>数据导入/导出底层模块，定义导入/导出指定数据源数据的API

- datagear-management
  <br>系统业务服务模块，定义数据源、数据分析等功能的服务层API

- datagear-meta
  <br>数据源元信息底层模块，定义解析指定数据源表结构的API

- datagear-persistence
  <br>数据源数据管理底层模块，定义读取、编辑、查询数据源表数据的API

- datagear-util
  <br>系统常用工具集模块

- datagear-web
  <br>系统web模块，定义web控制器、操作页面

## 依赖

	Java 8+
	Servlet 3.1+

## 编译

### 准备单元测试环境

1. 安装 MySQL-8.0 数据库，并将`root`用户的密码设置为：`root`（或者修改`test/config/jdbc.properties`配置）

2. 新建测试数据库，名称取为：`dg_test`

3. 使用`test/sql/test-sql-script-mysql.sql`脚本初始化`dg_test`库

### 执行编译命令

	mvn clean package

或者，也可不准备单元测试环境，直接执行如下编译命令：

	mvn clean package -DskipTests

编译完成后，将在`datagear-web/target/datagear-[version]-packages/`内生成程序包。

## 调试
	
1. 将`datagear`以maven工程导入至IDE工具

2. 以调试模式运行datagear-web模块的启动类：`org.datagear.web.DataGearApplication`

3. 打开浏览器，输入：`http://localhost:50401`
	
## 调试注意

在调试开发分支前（`dev-*`），建议先备份DataGear工作目录（`[用户主目录]/.datagear`），
因为开发分支程序启动时会修改DataGear工作目录，可能会导致先前使用的正式版程序、以及后续发布的正式版程序无法正常启动。

系统启动时会根据当前版本号自动升级内置数据库（Derby数据库，位于`[用户主目录]/.datagear/derby`目录下），且成功后下次启动时不再自动执行，如果调试时遇到数据库异常，需要查看

	datagear-management/src/main/resources/org/datagear/management/ddl/datagear.sql

文件，从中查找需要更新的SQL语句，手动执行。

然后，手动执行下面更新系统版本号的SQL语句：

	UPDATE DATAGEAR_VERSION SET VERSION_VALUE='当前版本号'
	
例如，对于`4.1.0`版本，应执行：

	UPDATE DATAGEAR_VERSION SET VERSION_VALUE='4.1.0'

系统自带了一个可用于为内置数据库执行SQL语句的简单工具类`org.datagear.web.util.DerbySqlClient`，可以在IDE中直接运行。注意：运行前需要先停止DataGear程序。

## 入门
##### 1\. 启动 
下载完datagear-\[version\].zip后解压，进入`datagear-\[version\]`/目录，执行启动命令： Linux系统：
```
./startup.sh 
```
Windows系统： 
```
startup.bat 
```
启动前请确保已安装JDK 8+版本运行环境（点击[这里](http://www.datagear.tech/documentation/#installation)了解如何安装）。 
系统初始会内置一个管理员用户，用户名为：admin，初始密码为：admin。 
系统所有数据默认都存储在【操作系统用户主目录】`/.datagear`文件夹内，请在必要时做好此文件夹的备份工作。 
程序文件夹内的`config/application.properties`文件中存储了常用系统配置，您可以根据实际需要修改它们， 比如：系统数据主目录、是否禁用匿名用户、是否禁用注册功能、系统端口号等，修改后需要重启才会生效。 
##### 2\. 注册和登录 
打开浏览器，输入DataGear服务地址 http://\[IP地址\]:50401 例如： http://localhost:50401 点击右上角的【登录】链接，在打开的页面内点击【注册】链接，打开注册页面，完成注册后，登录系统。
##### 3\. 新建数据集 
新建SQL数据集（可选） 
点击主页左侧【数据源】导航，打开数据源管理页面，点击【+】按钮，添加一个数据源，例如MySQL：
![](http://www.datagear.tech/static/quickstart/add-datasource.png?v=1) 
点击主页左侧【数据集】导航，添加一个SQL数据集，执行【预览】后保存： 
![](http://www.datagear.tech/static/quickstart/add-sql-dataset.png?v=1) 
新建CSV数据集（可选） 
点击主页左侧【数据集】导航，添加一个CSV数据集，执行【预览】后保存： 
![](http://www.datagear.tech/static/quickstart/add-csv-dataset.png?v=1) 
##### 4\. 新建图表 
点击主页左侧【图表】导航，添加两个图表，一个柱状图，一个饼图（注意需点击选定数据集字段右侧的【+】按钮绑定数据标记）： 
![](http://www.datagear.tech/static/quickstart/add-bar-chart.png?v=1) ![](http://www.datagear.tech/static/quickstart/add-pie-chart.png?v=1) 
##### 5\. 展示图表 
点击上述添加图表页面的【保存并展示】按钮，即可看到图表展示效果： 
![](http://www.datagear.tech/static/quickstart/show-bar-chart.png?v=1) ![](http://www.datagear.tech/static/quickstart/show-pie-chart.png?v=1) 
##### 6\. 新建看板 
导入看板模板（可选）
打开DataGear官网[【模板】](http://www.datagear.tech/template/)链接，下载一个看板模板，然后在系统【看板】管理页面， 点击【添加】右侧下拉菜单的【导入】条目，导入下载的模板，点击【设计】按钮，打开看板设计页面，点击【可视模式】按钮，绑定图表，如下图所示： 
![](http://www.datagear.tech/static/screenshot/dashboard-impt-visual-mode.gif?v=2)新建空白看板（可选）
点击主页左侧【看板】导航，点击【添加】按钮，填写名称后，点击【保存并设计】按钮，打开看板设计页面，点击【可视模式】按钮，插入标题、布局、图表后，设置颜色和样式，如下图所示： 
![](http://www.datagear.tech/static/screenshot/dashboard-visual-mode.gif?v=2) 
##### 7\. 展示看板 
点击上述添加看板页面的【保存并展示】按钮，即可看到看板展示效果。 
![](http://www.datagear.tech/static/screenshot/dashboard-impt-visual-mode-show.png?v=1) 
![](http://www.datagear.tech/static/screenshot/dashboard-visual-mode-show.png?v=1) 
##### 8\. 添加交互操作（高级功能，可选） 
以上述新建空白看板为例，打开看板设计页面，切换至源码模式，插入HTML按钮元素，编写控制图表显示/隐藏的JavaScript脚本，如下所示： 
![](http://www.datagear.tech/static/screenshot/dashboard-src-mode.gif?v=1) 
保存并展示看板，交互效果如下所示： 
![](http://www.datagear.tech/static/screenshot/dashboard-src-mode-show.gif?v=1)

## 版权和许可

Copyright 2018-2023 datagear.tech

DataGear is free software: you can redistribute it and/or modify it under the terms of
the GNU Lesser General Public License as published by the Free Software Foundation,
either version 3 of the License, or (at your option) any later version.

DataGear is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
See the GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License along with DataGear.
If not, see <https://www.gnu.org/licenses/>.
