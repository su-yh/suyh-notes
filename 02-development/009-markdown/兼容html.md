# markdown 的一些基本用法

## Markdown 兼容HTML
- 下面是一个HTML 格式的表格的示例
- 这下面如果有一个缩进则整个表格会往后缩进一段
    <table>
        <tr>
            <td>Array 老师</td>
            <td>一个学生</td>
        </tr>
        <tr>
            <td>第二行</td>
            <td>第二列</td>
        </tr>
    </table>
- 如果没有缩进，顶格写的话这个整体就顶格显示
<table>
    <tr>
        <td>Array 老师</td>
        <td>一个学生</td>
    </tr>
    <tr>
        <td>第二行</td>
        <td>第二列</td>
    </tr>
</table>

- HTML 里面的有两个字符( < 和 & )需要特殊处理，但是Markdown 里面可以直接写，不需要进行转义。
    - 4 < 5  -- 这个在markdown 中原样输出
    - 4 &lt; 5 -- 这是HTML 的语法需要将 "<" 写成"&lt ;"

## 自动转换
- "&copy" 将会被转义为一个符号: &copy;



