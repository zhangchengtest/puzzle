package com.elephant.maven;

import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;

import java.io.File;
import java.io.FileReader;
import java.util.List;

public class MavenDependencyReader {

    public static void read(String path) throws Exception {
        // 读取pom.xml文件
        File pomFile = new File(path);
        MavenXpp3Reader reader = new MavenXpp3Reader();
        Model model = reader.read(new FileReader(pomFile));

        // 获取依赖列表并打印出来
        List<Dependency> dependencies = model.getDependencies();
        for (Dependency dependency : dependencies) {
            System.out.println(dependency.getGroupId() + ":" + dependency.getArtifactId() + ":" + dependency.getVersion());
        }
    }

    public static void main(String[] args) throws Exception {
        read("pom.xml");
        System.out.println("------");
        read("app-sysmanage/pom.xml");
    }
}
