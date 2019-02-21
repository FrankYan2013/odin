package com.huifu.odin.dal;

import com.github.pagehelper.PageInterceptor;
import com.huifu.orion.config.ConfigChangeListener;
import com.huifu.pyxis.client.PyxisConfig;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * c3p0数据库连接池配置
 * <p>
 *
 * @author frank.yan
 * @date 2016/12/13
 */
@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = "com.huifu.odin.dal", sqlSessionTemplateRef = "sqlSessionTemplate")
public class C3p0DatesourceConfig {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${acct.master.c3p0.jdbcUrl}")
    private String jdbcUrl;

    @Value("${acct.master.c3p0.driverClass:oracle.jdbc.driver.OracleDriver}")
    private String driverClass;

    @Value("${acct.master.c3p0.user}")
    private String user;

    @Value("${acct.master.c3p0.password}")
    private String password;

    @Value("${acct.master.c3p0.initialPoolSize:5}")
    private int initialPoolSize;

    @Value("${acct.master.c3p0.minPoolSize:5}")
    private int minPoolSize;

    @Value("${acct.master.c3p0.maxPoolSize:15}")
    private int maxPoolSize;

    @Value("${acct.master.c3p0.maxIdleTime:86400}")
    private int maxIdleTime;

    @Value("${acct.master.c3p0.acquireIncrement:3}")
    private int acquireIncrement;

    @Value("${acct.master.c3p0.idleConnectionTestPeriod:60}")
    private int idleConnectionTestPeriod;

    @Value("${acct.master.c3p0.checkoutTimeout:300000}")
    private int checkoutTimeout;

    @Value("${acct.master.c3p0.preferredTestQuery:select 1 from dual}")
    private String preferredTestQuery;

    @Value("${acct.master.c3p0.maxStatements:450}")
    private int maxStatements;

    @Value("${acct.master.c3p0.maxStatementsPerConnection:30}")
    private int maxStatementsPerConnection;

    @Value("${acct.master.c3p0.statementCacheNumDeferredCloseThreads:1}")
    private int statementCacheNumDeferredCloseThreads;

    @Value("${acct.master.jdbc.monitor:false}")
    private boolean jdbcMonitor;

    @Value("${acct.master.jndi.open:false}")
    private boolean isOpenJndi;


    @Bean(destroyMethod = "close", initMethod = "getConnection")
    public DataSource dataSource() throws PropertyVetoException {

        if (isOpenJndi) {
            String jndiName = PyxisConfig.getInstance().getProperty("jndi.name");

            if (StringUtils.isNotEmpty(jndiName)) {
                JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
                DataSource dataSource = dataSourceLookup.getDataSource(jndiName);
                return dataSource;
            } else {
                logger.error("jndi.name is empty. init jndi datasource failed.");
                throw new RuntimeException("init jndi datasource failed.");
            }
        }

        ComboPooledDataSource dataSource = new ComboPooledDataSource(true);
        dataSource.setDriverClass(driverClass);
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUser(user);
        dataSource.setPassword(password);
        dataSource.setInitialPoolSize(initialPoolSize);
        dataSource.setMinPoolSize(minPoolSize);
        dataSource.setMaxPoolSize(maxPoolSize);
        dataSource.setMaxIdleTime(maxIdleTime);
        dataSource.setAcquireIncrement(acquireIncrement);
        dataSource.setIdleConnectionTestPeriod(idleConnectionTestPeriod);
        dataSource.setCheckoutTimeout(checkoutTimeout);
        dataSource.setPreferredTestQuery(preferredTestQuery);
        dataSource.setMaxStatements(maxStatements);
        dataSource.setMaxStatementsPerConnection(maxStatementsPerConnection);
        dataSource.setStatementCacheNumDeferredCloseThreads(statementCacheNumDeferredCloseThreads);

        String[] keyArrStr = new String[]{
                "c3p0.user", "c3p0.password", "c3p0.preferredTestQuery"
        };

        String[] keyArrInt = new String[]{
                "c3p0.initialPoolSize", "c3p0.minPoolSize", "c3p0.maxPoolSize", "c3p0.maxIdleTime",
                "c3p0.acquireIncrement", "c3p0.checkoutTimeout",
                "c3p0.idleConnectionTestPeriod",
                "c3p0.maxStatements", "c3p0.maxStatementsPerConnection", "c3p0.statementCacheNumDeferredCloseThreads"
        };

        for (String changeKey : keyArrStr) {
            addStrListener(dataSource, changeKey);
        }
        for (String changeKey : keyArrInt) {
            addIntStrListener(dataSource, changeKey);
        }

        return dataSource;
    }

    private void addIntStrListener(ComboPooledDataSource c3p0DataSource, final String changeKey) {
        PyxisConfig.getInstance().addChangeListener(new ConfigChangeListener() {
            @Override
            public void onChange(String key, String path, String value) {
                if (key.equals(changeKey)) {
                    if (c3p0DataSource != null) {
                        int val = Integer.parseInt(value);
                        if ("c3p0.initialPoolSize".equals(changeKey)) {
                            c3p0DataSource.setInitialPoolSize(val);
                        } else if ("c3p0.minPoolSize".equals(changeKey)) {
                            c3p0DataSource.setMinPoolSize(val);
                        } else if ("c3p0.maxPoolSize".equals(changeKey)) {
                            c3p0DataSource.setMaxPoolSize(val);
                        } else if ("c3p0.maxIdleTime".equals(changeKey)) {
                            c3p0DataSource.setMaxIdleTime(val);
                        } else if ("c3p0.acquireIncrement".equals(changeKey)) {
                            c3p0DataSource.setAcquireIncrement(val);
                        } else if ("c3p0.checkoutTimeout".equals(changeKey)) {
                            c3p0DataSource.setCheckoutTimeout(val);
                        } else if ("c3p0.idleConnectionTestPeriod".equals(changeKey)) {
                            c3p0DataSource.setIdleConnectionTestPeriod(val);
                        } else if ("c3p0.maxStatements".equals(changeKey)) {
                            c3p0DataSource.setMaxStatements(val);
                        } else if ("c3p0.maxStatementsPerConnection".equals(changeKey)) {
                            c3p0DataSource.setMaxStatementsPerConnection(val);
                        } else if ("c3p0.statementCacheNumDeferredCloseThreads".equals(changeKey)) {
                            c3p0DataSource.setStatementCacheNumDeferredCloseThreads(val);
                        }
                        try {
                            c3p0DataSource.getConnection();
                        } catch (SQLException e) {
                            logger.error("getConnection error:", e);
                        }
                    }
                }
            }
        });
    }

    private void addStrListener(ComboPooledDataSource c3p0DataSource, final String changeKey) {
        PyxisConfig.getInstance().addChangeListener(new ConfigChangeListener() {
            @Override
            public void onChange(String key, String path, String value) {
                if (key.equals(changeKey)) {
                    if (c3p0DataSource != null) {
                        if ("c3p0.user".equals(changeKey)) {
                            c3p0DataSource.setUser(value);
                        } else if ("c3p0.password".equals(changeKey)) {
                            c3p0DataSource.setPassword(value);
                        } else if ("c3p0.preferredTestQuery".equals(changeKey)) {
                            c3p0DataSource.setPreferredTestQuery(value);
                        }
                        try {
                            c3p0DataSource.getConnection();
                        } catch (SQLException e) {
                            logger.error("getConnection error:", e);
                        }
                    }
                }
            }
        });
    }


    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
        return sqlSessionTemplate;
    }

    @Autowired
    PageInterceptor pageInterceptor;

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setTypeAliasesPackage("com.huifu.odin.dal.mapper,com.huifu.odin.dal.repository");
        String[] mapperLocations = new String[2];
        mapperLocations[0] = "classpath*:com/huifu/odin/dal/mapper/**/*Mapper.xml";
        mapperLocations[1] = "classpath*:com/huifu/odin/dal/repository/**/*.xml";
        sqlSessionFactoryBean.setMapperLocations(resolveMapperLocations(mapperLocations));
        sqlSessionFactoryBean.setPlugins(new Interceptor[]{pageInterceptor});
        return sqlSessionFactoryBean.getObject();
    }

    public Resource[] resolveMapperLocations(String[] mapperLocations) {
        ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
        List<Resource> resources = new ArrayList<Resource>();
        if (mapperLocations != null) {
            for (String mapperLocation : mapperLocations) {
                try {
                    Resource[] mappers = resourceResolver.getResources(mapperLocation);
                    resources.addAll(Arrays.asList(mappers));
                } catch (IOException e) {
                    // ignore
                }
            }
        }
        return resources.toArray(new Resource[resources.size()]);
    }


    @Bean
    public TransactionTemplate transactionTemplate(PlatformTransactionManager transactionManager) {
        TransactionTemplate transactionTemplate = new TransactionTemplate();
        transactionTemplate.setTransactionManager(transactionManager);
        return transactionTemplate;
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);
        return dataSourceTransactionManager;
    }


}
