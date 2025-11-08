



## 查看磁盘信息

![image-20251108141433161](ubuntu-磁盘调整.assets/image-20251108141433161.png)

```shell
suyh@suyh:~$ lsblk
NAME                      MAJ:MIN RM  SIZE RO TYPE MOUNTPOINTS
sda                         8:0    0   80G  0 disk 
├─sda1                      8:1    0    1M  0 part 
├─sda2                      8:2    0    2G  0 part /boot
└─sda3                      8:3    0   68G  0 part 
  └─ubuntu--vg-ubuntu--lv 252:0    0   68G  0 lvm  /
sr0                        11:0    1 1024M  0 rom  
suyh@suyh:~$ 

```

```txt
从输出来看，sda 显示为80G，表示 总磁盘为80G
sda1 1M
sda2 2G
sda3 68G

所以还有10G未分配
```



## 调整磁盘大小

```shell
sudo cfdisk
```

1. 选择 /dev/sd3 然后resize

   ![image-20251108142020339](ubuntu-磁盘调整.assets/image-20251108142020339.png)

2. New size

   ![image-20251108142108281](ubuntu-磁盘调整.assets/image-20251108142108281.png)

   这里默认就会显示所有剩余空间，一般不用动，直接回车，记得确认，输入："==yes=="

3. Write

   ![image-20251108142208825](ubuntu-磁盘调整.assets/image-20251108142208825.png)

   这时候可以看到sd3 的磁盘已经调整为78G了，然后选择下面的 ==Write== ，之后再 ==Quit==。

4. 继续

   ![image-20251108142735094](ubuntu-磁盘调整.assets/image-20251108142735094.png)

   ```shell
   sudo pvresize /dev/sda3
   ```

5. 继续

   ![image-20251108143458839](ubuntu-磁盘调整.assets/image-20251108143458839.png)

   ```shell
   sudo lvextend -l +100%FREE /dev/mapper/ubuntu--vg-ubuntu--lv
   ```

   这里的 `/dev/mapper/ubuntu--vg-ubuntu--lv` 是通过 ==df -h /== 得出的

6. 继续

   ![image-20251108143527924](ubuntu-磁盘调整.assets/image-20251108143527924.png)

   ```shell
   sudo resize2fs /dev/mapper/ubuntu--vg-ubuntu--lv
   ```

7. 查看（`df -h`）

   ![image-20251108143558148](ubuntu-磁盘调整.assets/image-20251108143558148.png)

8. 其他





