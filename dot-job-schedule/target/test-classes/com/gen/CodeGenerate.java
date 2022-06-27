package com.gen;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import com.dot.admin.Application;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
@Slf4j
public class CodeGenerate {

    @Autowired
    private DataSourceProperties dataSource;

    /**
     * 项目路径
     */
    private static final String projectPath = System.getProperty("user.dir");

    /**
     * 要生成的表名称
     */
    private static final String tableNames = "product_similar_products,user_click_product_similar_products,user_expose";

    //获取项目目录
    String parent_dir = "";
    //获取pojo目录.entity
    String pojodir = parent_dir + "src/main/java/com/dot/job/entity/";
    //获取mapper目录
    String mapperdir = parent_dir + "src/main/java/com/dot/job/mapper/";
    //获取模块名
    //获取service目录
    String servicedir = parent_dir + "src/main/java/com/dot/job/service/";
    String serviceImpldir = parent_dir + "src/main/java/com/dot/job/service/impl/";

    @Test
    public void generate() {
        //设置自定义输出目录（分布式项目使用）
        Map<OutputFile, String> pathInfo = new HashMap<>();
        pathInfo.put(OutputFile.entity, pojodir);
        pathInfo.put(OutputFile.mapper, mapperdir);
        pathInfo.put(OutputFile.mapperXml, mapperdir);
        pathInfo.put(OutputFile.service, servicedir);
        pathInfo.put(OutputFile.serviceImpl, serviceImpldir);


        //最终的代码生成
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/dot-job", "root", "root")
                .globalConfig(builder -> {
                    builder.author("shuxun") // 设置作者
//                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
//                           // 指定自己本地输出目录
                            .outputDir("/Users/shuxun/defi_workspace/dot-job/dot-job-manage/src/main/java")
                    ;
                })
                .packageConfig(builder -> {
                    builder
                            .parent("com.dot.job") // 设置父包名，默认值:com.baomidou
//                            .moduleName("") // 设置父包模块名
                            .entity("entity")
                            .mapper("mapper")
                            .xml("mapper")
//                            .pathInfo(pathInfo)
                            .build();
                })

                /**
                 * 设置需要生成的表名 addInclude
                 * 过滤表前缀 addTablePrefix
                 * 过滤表后缀 addTableSuffix
                 */
                .strategyConfig(builder -> {
                    builder
                            /*.addInclude("ecrcmd_app_new_hot_warehouse")
                           .addTablePrefix("ecrcmd_app_new_")*/
                            /*.addInclude("item_info","item_sell_info","item_release_info","item_release_record"
                            ,"chain_info","user_address_info","user_claim_record",
                                    "user_invest_info","user_itegral_record",
                            "user_pledge_record","user_transfer_record","user_unlock_record");*/
                            .addInclude("dot_executor","dot_execute_group")
                    ;
                })

                .strategyConfig(builder -> {
                    builder.entityBuilder()
                            .enableLombok()
                            .enableTableFieldAnnotation()
                    ;
                })

                .strategyConfig(builder -> {
                    builder.mapperBuilder()
                            .enableBaseResultMap()
                            .enableBaseColumnList()
                    ;
                })

                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();


    }


}


