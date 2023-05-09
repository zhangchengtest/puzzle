package com.elephant.maven;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DependencyTreeParser {

    public static void main(String[] args) {
//        String treeOutput = runMvnCommand("dependency:tree");
//        List<Dependency> dependencies = parseTreeOutput(treeOutput);
//        for (Dependency dep : dependencies) {
//            System.out.println(dep);
//        }

        long tt = new Date().getTime();
        Date expireDate = addOneYearToDate(new Date());
        System.out.println(expireDate);
        Date expireDate2 = addOneYearToDate(new Date());
        System.out.println(expireDate2);
    }

    public static Date addOneYearToDate(Date date) {
        // 将Date对象转换为LocalDate对象
        LocalDate currentDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        // 创建一个表示一年的Period对象
        Period oneYear = Period.ofYears(1);

        // 将当前日期加上一年的Period对象
        LocalDate expireDate = currentDate.plus(oneYear);

        // 将结果转换为Date类型
        return Date.from(expireDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    private static String runMvnCommand(String command) {
        StringBuilder output = new StringBuilder();
        try {
            Process process = Runtime.getRuntime().exec("mvn " + command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return output.toString();
    }

    private static List<Dependency> parseTreeOutput(String treeOutput) {
        List<Dependency> dependencies = new ArrayList<>();
        String[] lines = treeOutput.split("\n");
        for (String line : lines) {
            if (line.startsWith("[INFO]")) {
                String[] parts = line.split(":");
                String groupId = parts[1].trim();
                String artifactId = parts[2].trim();
                String version = parts[3].trim();
                dependencies.add(new Dependency(groupId, artifactId, version));
            }
        }
        return dependencies;
    }

    private static class Dependency {
        private String groupId;
        private String artifactId;
        private String version;

        public Dependency(String groupId, String artifactId, String version) {
            this.groupId = groupId;
            this.artifactId = artifactId;
            this.version = version;
        }

        public String getGroupId() {
            return groupId;
        }

        public String getArtifactId() {
            return artifactId;
        }

        public String getVersion() {
            return version;
        }

        @Override
        public String toString() {
            return groupId + ":" + artifactId + ":" + version;
        }
    }
}
