package generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * Model & Mapper generator
 */
public class CodeGenerator {


    private static final String MODULE_NAME = "musicgallery";

    private static final String PARENT_NAME = "com.example";

    private static String[] tableNames = {
            "music",
            "artist",
            "album"
    };

    private final static AutoGenerator autoGenerator = new AutoGenerator();

    // package config
    private static String CONTROLLER = "controller";
    private static String SERVICE = "service";
    private static String SERVICEIMPL = "service.Impl";
    private static String MAPPER = "mapper";
    private static String ENTITY = "model";
    private static String projectPath =  System.getProperty("user.dir");

    // data source config
    private static String DB_USERNAME = "gallery_admin";
    private static String DB_PASSWORD = "Bp5zwoayGvCfQGhmP87j";
    private static String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static String DB_URL = "jdbc:mysql://localhost:3306/music_gallery?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&useSSL=false";

    // xml path
    private static String TEMPLATEPATH = "/templates/mapper.xml.vm";

    // other config
    private static GlobalConfig gc = new GlobalConfig();
    private static DataSourceConfig dsc = new DataSourceConfig();
    private static PackageConfig pc = new PackageConfig();
    private static InjectionConfig cfg = new InjectionConfig() {
        @Override
        public void initMap() {
            // to do nothing
        }
    };
    private static TemplateConfig templateConfig = new TemplateConfig();
    private static StrategyConfig strategy = new StrategyConfig();


    private static void initGlobalConfig(){

        // 全局配置
        gc.setAuthor("Lin Pang");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setOpen(false);
        gc.setFileOverride(false);
        gc.setSwagger2(true);
        gc.setBaseResultMap(true);
        gc.setBaseColumnList(true);
        gc.setDateType(DateType.ONLY_DATE);
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setControllerName("%sController");
        autoGenerator.setGlobalConfig(gc);
    }


    private static void initDataSourceConfig(){

        dsc.setUrl(DB_URL);
        dsc.setDbType(DbType.MYSQL);
        dsc.setDriverName(DB_DRIVER);
        dsc.setUsername(DB_USERNAME);
        dsc.setPassword(DB_PASSWORD);
        autoGenerator.setDataSource(dsc);
    }


    private static void initPackageConfig(){

        // 包配置
        pc.setModuleName(MODULE_NAME);
        pc.setParent(PARENT_NAME);
        pc.setService(SERVICE);
        pc.setEntity(ENTITY);
        pc.setServiceImpl(SERVICEIMPL);
        pc.setMapper(MAPPER);
        pc.setController(CONTROLLER);
        autoGenerator.setPackageInfo(pc);
    }


    private static void initInjectionConfig(){


        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig(TEMPLATEPATH) {
            @Override
            public String outputFile(TableInfo t) {
                return projectPath + "/src/main/resources/mapper/" + t.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        autoGenerator.setCfg(cfg);
    }


    private static void initTemplateConfig(){


        templateConfig.setEntity("templates/entity.java");
        templateConfig.setController(null);
        templateConfig.setXml(null);
        autoGenerator.setTemplate(templateConfig);
    }


    private static void initStrategyConfig(){

        List<TableFill> tableFillList = new ArrayList<>();
        strategy.setTableFillList(tableFillList);
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setSuperEntityClass("com.baomidou.mybatisplus.extension.activerecord.Model");
        strategy.setInclude(tableNames);
        autoGenerator.setStrategy(strategy);
        autoGenerator.setTemplateEngine(new VelocityTemplateEngine());
    }

    public static void init(){
        initGlobalConfig();
        initDataSourceConfig();
        initPackageConfig();
        initInjectionConfig();
        initTemplateConfig();
        initStrategyConfig();
    }

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        init();
        autoGenerator.execute();
    }
}

