package com.example.springbootvue3.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;

import java.util.Collections;

public class MybatisGenerator {
    private static final String url = "jdbc:mysql://localhost:3306/blog?serverTimezone=UTC&useSSL=false";
    private static final String username = "root";
    private static final String password = "root";
    //private static final DataSourceConfig.Builder DATA_SOURCE_CONFIG=new DataSourceConfig.Builder(url,username,password);
    public static void main(String[] args) {
        FastAutoGenerator.create(url,username,password)
                .globalConfig(builder -> {
                    builder.author("丁祥 QQ 2421341497") // 设置作者
                            .disableOpenDir()
                            // .enableSwagger() // 开启 swagger 模式
                            // .fileOverride() // 覆盖已生成文件
                            .outputDir("E://javawebproject/springboot-vue3/src/main/java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.example.springbootvue3") // 设置父包名
                            //.moduleName("system") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "E://javawebproject/springboot-vue3/src/main/resources/mybatis/mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("role_menu") // 设置需要生成的表名
                            .addTablePrefix("", "") // 设置过滤表前缀
                     .entityBuilder().enableLombok().disableSerialVersionUID()
                            .controllerBuilder().enableRestStyle()
                    .mapperBuilder().enableMapperAnnotation();
                })

                .execute();
    }
}
