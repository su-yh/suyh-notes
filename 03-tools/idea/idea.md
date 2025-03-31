
# maven 更新
## 更新失败，长时间更新不下来
- > 清空缓存，然后全部重新拉取
  >
  > - `file` -> `Invalidate Caches/Restarts...`

# maven 项目识别
## 有时候项目没有被识别为maven 或者maven 项目没有三角形的启动图标时
- > 以maven 项目来识别
	>
	> - 右键项目中的`pom.xml` -> `Add as Maven Project`

# 常用
## 将非项目文件排除
- > 排除".idea" 目录
  >
  > - `file` -> `settings` -> `File Types` -> 在编辑框中添加
## 自动添加 `serialVersionUID` 的值
- > `file -> settings...`
  >
  > ![](md-png\2018051010040324.jpg)
  >
  > - 搜索 `inspections`
  > - 在右边框中继续搜索 `serialVersionUID`
  > > - 搜索结果会是: java -> Serialization issues
  > >> - 然后下面有4 个可选项
  >
  > >>>     Non-Serializable class with serialVersionUID
  > >>>     Serializable class without serialVersionUID
  > >>>     Serializable non-'static' inner class without serialVersionUID
  > >>>     serialVersionUID field not declared 'private static final long'
  > >> - 在上面的四个选项中勾选第二和第四个即可
  > >> - 然后在使用的时候，只要继承了`Serializable` 类，就可以使用快捷键`Alt + Enter` 就会弹出"Add 'serialVersionUID' field"

## 文件默认编码 UTF-8
- > 设置文件编码
  >
  > - `file` -> `settings` -> 搜索 `file encoding` -> `Global Encoding` `Project Encoding` `Properties files`

## 'if' statement can be simplified

- 解决示例

  > ![image-20250329112213359](idea.assets/image-20250329112213359.png)

- >`file` -> `Editor` -> `Inspections` -> `Redundant 'if' statement`
  >
  >![image-20250310200915700](idea.assets/image-20250310200915700.png)

# 快捷键

## 修改快捷键
- > 代码提示快捷键
    > - `file -> Settings...`
    > - 搜索 `Keymap` 并选中
    > - 搜索 `Cyclic Expand Word` 先将这个快捷键空出来
    > - 右击 选择 `Remove Alt+/`
    > - 重新搜索 `Basic`
    > - 找到 `Code -> Completion -> Basic` 
    > - 并右键选择 `Add Keyboard Shortcut`

- > 代码提示补充大小写忽略
    > - `file -> Settings...`
    > - 搜索 `Code Completion`
    > - `Match case` 取消勾选

- > 自动补全代码快捷键
    > - `Ctrl + Alt + t`
    >> - `if ... else `
    >> - `try ... catch ...`
	
- > 查看调用链
	>
	> - `Alt + F7` 或者 `Ctrl + Alt + h`


# 插件
## 常用插件
- > ### lombok
- > ### mybatisx
  >
  > SequenceDiagram 调用链 时序图
## 翻译
- > 添加翻译插件
    > -  `file -> Settings...`
    > - `Plugins`
    > - 搜索安装: `translation`
- > `p3c`
  >
  > - `NR Coding Style`
- > `mybatis plus`
  >
  > - `iBATIS/MyBatis mini-plugin`


# 导出jar

## 参考博客
- > `https://blog.csdn.net/chenpuzhen/article/details/82252449`
- > 步骤
    > - `file -> Project Structure...`
    > - 左侧选中 `Artifacts`
    > - 中间点击 `+` -> `Jar -> From modules with dependencies...`
    > - 在弹出框中选择要导出的`Module` 和`Main Class` 以及`META-INF/MANIFEST.MF` 这个指定为项目的根目录
    > - 指定打好的包的输出目录位置以及项目名称
    > - 勾选`Build on make`
    > - 将一些不需要的第三方包删除
    > - 点击OK结束配置
    > - 退出配置之后，构建项目  `Build  ->  Build Artifacts...` 
    > - 选择指定的构建项目名，然后选择`Build`即可

## install打包
- > 打包输出路径
  >
  > - 这个路径我不知道在哪里配置，但是使用 install 之后在下面的日志中会有提示打包在哪个位置去了。

# MYSQL
## 手动配置和下载MYSQL 驱动
- > ### 下载驱动
- >> 官方下载地址
    > - https://dev.mysql.com/downloads/connector/j/ 
    > - 选择 "Archives" 选择指定版本
    > - 选择 "platform independent" 与平台无关
    > - 然后下载、解压放到本地目录。
    >> - IDEA 有一个存放驱动的地址
    >> - 大概是: C:\Users\yhsud\.IntelliJIdea2019.2\config\jdbc-drivers
- > ### idea 中配置驱动
- >> 选择右边 "Database"  -->  选择有一个"+" 号的  -->  "Data Source"  -->  "Mysql" 
- >> 在 "Drivers" 下面选择 "MYSQL"  然后点击在右边的 "+" 选择我们本地的驱动包就可以了。
- >> ‘mysql-connector-java-8.0.13.jar’
- >> 'ojdbc8-19.3.0.0.jar'

# IntelliJ IDEA自动导入包去除星号（import xxx.*）
- > ### 打开设置 > Editor > Code Style > Java
	> - Scheme: Default
	> - Imports
  > > - ① 将Class count to use import with "*"改为99（导入同一个包的类超过这个数值自动变为 * ）
  > > - ② 将Names count to use static import with "*"改为99（同上，但这是静态导入的）
  > > - ③ 将Package to Use import with "*"删掉默认的这两个包（不管使用多少个类，只要在这个列表里都会变为 *(星) ）
	>
	> - PS：Scheme Default是针对全局的，你也可以只修改某个Project的

# IntelliJ IDEA 自定义类模板

- > ### 打开设置
    > - Editor > File and Code Templates
    > - Scheme: "Default"
    >> - 选择："Includes"
    >>> - 选择："File Header"文件
    >>>> - 在这个文件中添加一个类注释什么的
    >>>>> ```
    >>>>>     /**
    >>>>>   * @author suyh
    >>>>>   * @since ${YEAR}-${MONTH}-${DAY}
    >>>>>   */
    >>>>> ```
    >>> - 新建文件"FileCopyright"
    >>>> ```
    >>>>     /**
    >>>>   * Copyright XXXXXXXX......
    >>>>   */
    >>>> ```
    >> - 选择："Files"
    >>> - 找到并选定："Class" "Interface" "Enum" "AnnotationType"
    >>>> - 解析指定文件并插入到此位置
    >>>>> ```
    >>>>>     #parse("FileCopyright.java")
    >>>>>     #if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME};#end
    >>>>>     #parse("File Header.java")
    >>>>>     public class ${NAME} {
    >>>>>     }
    >>>>> ```

# dashboard 启动仪表盘

找到.idea下面的workspace.xml文件

# 修改搜索时忽略大小写

![image-20230915151739078](idea.assets/image-20230915151739078.png)



# idea 启动时，取消默认打开工程

每个版本都不一样

一般搜索`startup`

![image-20230915153303393](idea.assets/image-20230915153303393.png)