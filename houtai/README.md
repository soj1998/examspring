# examspring
exam question and answer


12.24创建本文件
# 每天日志
```
7.28 把examque 改成 有序号


```

# 近期工作
```
	（1） 搞机器学习
	（2） 搞c++
```


# 导出文件
```
	到pom文件所在文件夹下,运行命令mvn clean package -DskipTests
```

# pull push
```
	examspring git remote show origin git push origin master git pull origin master
```

# 搞首页
```
	（1）所有文章，包括专栏、试题、基础都要再存入shouyexinxi表
	（2）新增的时候存入，新增成功后再去存入，没成功则不存入
	（3）修改的时候修改shouyexinxi表，jpa的save方法有原来的id就是update
	（4）删除的时候记得删除shouyexinxi表
```

# 搞试题
```
	（1）试题的架构和文章不一样
	（2）导入形式可以多种 用excel docx 网页录入 都可以
	（3）数据库表 应该有 试题类型（多选、单选、判断、问答） 题目 选项 答案 解析 有效标志 涉及知识点 
	    涉及年份 学科大分类（税收、信息技术、高自考、信息安全、高等数学、大学物理） 
		学科小分类（税种-增值税、消费税，大学语文，c++，java,spring boot,vue)
	（4）用docx文件导入的话，需要考虑的格式化东西较多，但导入速度快，又要保存
	（5）用网页录入的话，保存东西细致，但过于繁琐。
	（6）还是按照基础类文章导入的方式搞
	（7）后台三个表，不用开始的表了，包含内容yxbz始终为Y，wzlx为习题类，sz是税种，
	文章来源要有，架构不用，录入时间，文件保存位置要有     
```