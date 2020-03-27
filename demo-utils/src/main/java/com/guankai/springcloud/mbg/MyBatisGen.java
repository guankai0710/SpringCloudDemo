package com.guankai.springcloud.mbg;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.TableConfiguration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * mybatis生成代码插件启动类
 *
 * @author kai.guan
 * @date 2019/bootstrap/10 16:15
 **/
public class MyBatisGen {

    private static final Logger logger = LoggerFactory.getLogger(MyBatisGen.class);

    public static void main(String[] args) throws Exception{
        //mybatis-generatorconfig.xml地址
        String fileName = "F:\\IdeaWorkspace01\\SpringCloudDemo\\demo-model\\src\\main\\resources\\mybatis-generatorconfig.xml";
        if (args.length > 0){
            fileName = args[0];
        }
        File configFile = new File(fileName);
        List<String> warnings = new ArrayList<>();
        boolean overwrite = true;
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
        List<Context> contexts = config.getContexts();
        for (Context context : contexts) {
            for (TableConfiguration tableConfiguration : context.getTableConfigurations()) {
                logger.info("表名：{},  类名：{}",
                        tableConfiguration.getTableName(),
                        tableConfiguration.getDomainObjectName());
            }
        }
    }
}
