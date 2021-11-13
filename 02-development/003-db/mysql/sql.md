

## 左链接

- 条件查询 + 左连接查询
    > 按左表中的条件(`m.id = 1`)查询结果然后与右表做一个左连接
    >>      select m.*, p.name parentName 
    >>      from sys_menus m left join sys_menus p 
    >>      on m.parentId = p.id
    >>      where m.id = 1