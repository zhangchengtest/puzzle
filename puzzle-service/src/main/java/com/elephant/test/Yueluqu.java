package com.elephant.test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;

public class Yueluqu {


    public static void main(String[] args) throws IOException {
        File dir = new File("D:\\xinyun\\code\\cunw-config"); // 替换为要读取的目录路径
        File[] files = dir.listFiles(file -> !file.isHidden()); // 排除掉隐藏目录
        for (File file : files) {
            if (file.isDirectory()) {
                File[] subFiles = file.listFiles();
                for (File subFile : subFiles) {
                    if (subFile.isFile() && subFile.getName().contains("test")) {
                        Path source = subFile.toPath();
                        String newName = subFile.getAbsolutePath().replace("test", "ylq");
                        Path destination = new File(newName).toPath();
//                        System.out.println(subFile.getAbsolutePath().replace(subFile.getName(), ));
//                        System.out.println(subFile.getName());
                        String content = new String(Files.readAllBytes(source));
                        content = content.replace("cunw.db.host=172.31.134.200", "cunw.db.host=rm-bp1714ny7vhgcr36r.mysql.rds.aliyuncs.com");
                        content = content.replace("cunw.db.username=test", "cunw.db.username=yuelu");
                        content = content.replace("cunw.db.password=Test/*-753951", "cunw.db.password=R2yzRk9Aprp$a");

                        content = content.replace("cunw.db.slave.host=172.31.134.200", "cunw.db.slave.host=rm-bp1714ny7vhgcr36r.mysql.rds.aliyuncs.com");
                        content = content.replace("cunw.db.slave.username=test", "cunw.db.slave.username=yuelu");
                        content = content.replace("cunw.db.slave.password=Test/*-753951", "cunw.db.slave.password=R2yzRk9Aprp$a");

                        content = content.replace("cunw.redis.cluster.node=172.31.134.200:7000,172.31.134.200:7001,172.31.134.200:7002,172.31.134.200:7003,172.31.134.200:7004,172.31.134.200:7005",
                                "cunw.redis.cluster.node=172.16.44.196:7000,172.16.44.196:7001,172.16.44.196:7002,172.16.44.196:7003,172.16.44.196:7004,172.16.44.196:7005");

                        content = content.replaceAll("cunw.mq.host=172.31.134.200", "cunw.mq.host=172.16.44.196");
                        content = content.replaceAll("cunw.mq.password=123456", "cunw.mq.password=S1d%*q2M");

                        content = content.replaceAll("cunw.es.serverUris=http://116.205.166.181:9200", "cunw.es.serverUris=http://172.16.44.196:9200");

                        Files.write(destination, content.getBytes(), StandardOpenOption.CREATE);
                    }
                }
            }
        }
    }
}
