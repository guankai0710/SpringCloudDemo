package com.guankai.springcloud.mbg;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.*;

/**
 * Mapper注解插件
 *
 * @author: kai.guan
 * @date: 2020/3/27 21:00
 **/
public class MapperAnnotationPlugin extends PluginAdapter {
    private final static Map<String, String> ANNOTATION_IMPORTS;

    static {
        ANNOTATION_IMPORTS = new HashMap<>();
        ANNOTATION_IMPORTS.put("@Mapper", "org.apache.ibatis.annotations.Mapper");
        ANNOTATION_IMPORTS.put("@Repository", "org.springframework.stereotype.Repository");
    }

    private List<String> annotations;

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    /**
     * 具体执行顺序 http://www.mybatis.org/generator/reference/pluggingIn.html
     * @param introspectedTable
     * @return
     */
    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        super.initialized(introspectedTable);

        this.annotations = new ArrayList<>();
        Properties properties = this.getProperties();
        boolean findMapper = false;
        for (Object key : properties.keySet()) {
            String annotation = key.toString().trim();
            if (annotation.startsWith("@Mapper")) {
                findMapper = true;
            }

            if (StringUtility.isTrue(properties.getProperty(key.toString()))) {
                this.annotations.add(annotation);
            }
        }

        if (!findMapper) {
            this.annotations.add(0, "@Mapper");
        }
    }



    /**
     * 具体执行顺序 http://www.mybatis.org/generator/reference/pluggingIn.html
     * @param interfaze
     * @param topLevelClass
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        for (String annotation : this.annotations) {
            if (annotation.equals("@Mapper")) {
                if (introspectedTable.getTargetRuntime() == IntrospectedTable.TargetRuntime.MYBATIS3) {
                    // don't need to do this for MYBATIS3_DSQL as that runtime already adds this annotation
                    interfaze.addImportedType(new FullyQualifiedJavaType(ANNOTATION_IMPORTS.get(annotation)));
                    interfaze.addAnnotation(annotation);
                }
            } else if (ANNOTATION_IMPORTS.get(annotation) != null) {
                interfaze.addImportedType(new FullyQualifiedJavaType(ANNOTATION_IMPORTS.get(annotation)));
                interfaze.addAnnotation(annotation);
            }
        }

        return true;
    }
}
