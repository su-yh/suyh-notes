


- > `collect_list` 将一分组中的一列合成一个`list`
    > - `SELECT col1, collect_list(col2) FROM tabelname GROUP BY col1;`
    >> - 结果大概为："A	('1','3','3')"

- > `collect_set` 将一分组中的一列合成一个`set`
    




