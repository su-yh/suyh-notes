

- java md5

        MessageDigest md = MessageDigest.getInstance("MD5");

        byte[] md5 = md.digest(pwd.getBytes());


- spring md5

        String md5Pwd = DigestUtils.md5DigestAsHex(pwd.getBytes());

