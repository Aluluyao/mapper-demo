#SpringBoot整合Mybatis
* 添加依赖

        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>1.3.1</version>
        </dependency>
* 配置SqlSessionFactory

        spring中，如果想使用mybatis，就必须将mybatis的sqlSesionFactory交给spring
        来管理，在 MyBatis-Spring 中,SqlSessionFactoryBean 是用于创建 SqlSessionFactory的。  
         
        /**
         * SqlSessionFactory是由SqlSessionFactoryBean的getObject方法获取，
         * 通过源码我们可以看到SqlSessionFactoryBean继承了FactoryBean类，
         * 同时我们需要给SqlSessionFactory注入数据源（任意类型数据源都可以）
         * <p>
         * 简单理解就是需要将mybatis的SqlSessionFactory工厂交给spring来管理,你可以直接
         * 使用SqlSessionFactory获取SqlSession,可以使用它来执行映射语句,提交或回滚连接,
         * 但是在spring中我们不这么做，我们会将这个工作交给一个叫做SqlSessionTemplate的对象
         * 来处理，后面会详细解释这个对象
         *
         * @param dataSource
         * @return
         * @throws Exception
         */
        @Bean(name = "kindSqlSessionFactory")
        public SqlSessionFactory instance(@Qualifier("kindDataSource") DataSource dataSource) throws Exception {
            SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
            // 注意如果这个路径下面没有xml文件 会报错
            factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/*.xml"));
            factoryBean.setDataSource(dataSource);
            return factoryBean.getObject();
        }
* 配置SqlSessionTemplate

        /**
         * 在 MyBatis 中,你可以使用 SqlSessionFactory 来创建 SqlSession。
         * 一旦你获得一个 session 之后,你可以使用它来执行映射语句,提交或回滚连接,最后,当不再需要它的时候,
         * 你可以关闭 session。 使用 MyBatis-Spring 之后, 你不再需要直接使用 SqlSessionFactory 了,
         * 因为你的 bean 可以通过一个线程安全的 SqlSession 来注入,基于 Spring 的事务配置 来自动提交,回滚,关闭session。
         * 注意通常不必直接使用 SqlSession。 在大多数情况下 MapperFactoryBean(映射器工厂), 将会在 bean 中注入所需要的映射器。
         * 后面会解释这个MapperFactoryBean细节。
         *
         * SqlSessionTemplate 是 MyBatis-Spring 的核心。
         * 这个类负责管理 MyBatis 的 SqlSession, 调用 MyBatis 的 SQL 方法,
         * 翻译异常。 SqlSessionTemplate 是线程安全的, 可以被多个 DAO 所共享使用。
         *
         * 当调用 SQL 方法时,SqlSessionTemplate 将会保证使用的 SqlSession 是和当前 Spring 的事务相关的。
         * 此外,它管理 session 的生命 周期,包含必要的关闭,提交或回滚操作。
         * SqlSessionTemplate 实现了 SqlSession 接口,这就是说,在代码中无需对 MyBatis 的 SqlSession 进行替换。
         *
         * @param sqlSessionFactory
         * @return
         * @throws Exception
         */
        @Bean(name = "kindSqlSessionTemplate")
        public SqlSessionTemplate instance(@Qualifier("kindSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
            SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactory);
            return template;
        }

* 什么是MapperFactoryBean

        为了代替手工使用 SqlSessionDaoSupport 或 SqlSessionTemplate 编写数据访问对象
        (DAO)的代码,MyBatis-Spring 提供了一个动态代理的实现:MapperFactoryBean。这个类 
        可以让你直接注入数据映射器接口到你的 service 层 bean 中。当使用映射器时,你仅仅如调 
        用你的 DAO 一样调用它们就可以了,但是你不需要编写任何 DAO 实现的代码,因为 MyBatis-Spring
        将会为你创建代理。
        
        使用注入的映射器代码,在 MyBatis,Spring 或 MyBatis-Spring 上面不会有直接的依赖。 
        MapperFactoryBean 创建的代理控制开放和关闭 session,翻译任意的异常到 Spring 的 
        DataAccessException 异常中。此外,如果需要或参与到一个已经存在活动事务中,代理将 
        会开启一个新的 Spring 事务
        
* 配置MapperScannerConfigurer

        /**
         * 为了代替手工使用 SqlSessionDaoSupport 或 SqlSessionTemplate 编写数据访问对象
         * (DAO)的代码,MyBatis-Spring 提供了一个动态代理的实现:MapperFactoryBean。这个类
         * 可以让你直接注入数据映射器接口到你的 service 层 bean 中。当使用映射器时,你仅仅如调
         * 用你的 DAO 一样调用它们就可以了,但是你不需要编写任何 DAO 实现的代码,因为 MyBatis-Spring
         * 将会为你创建代理。
         *
         * 使用注入的映射器代码,在 MyBatis,Spring 或 MyBatis-Spring 上面不会有直接的依赖。
         * MapperFactoryBean 创建的代理控制开放和关闭 session,翻译任意的异常到 Spring 的
         * DataAccessException 异常中。此外,如果需要或参与到一个已经存在活动事务中,代理将
         * 会开启一个新的 Spring 事务
         *
         * 需要注意的是：这里由于我使用的是tk.mybatis.spring.mapper.MapperScannerConfigurer 它继承了
         * org.mybatis.spring.mapper.MapperScannerConfigurer 看名字像是一个配置类，所以我们可以在这里
         * 设置你需要扫描的接口路径，这样我们就可以使用接口代理开发mybatis了，但是这里我使用的是通用mapper
         * 所以需要引入通用mapper的jar包
         *
         * @return
         */
        @Bean(name = "kindMapperScannerConfigurer")
        public MapperScannerConfigurer mapperScannerConfigurer() {
            MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
            mapperScannerConfigurer.setSqlSessionFactoryBeanName("kindSqlSessionFactory");
            mapperScannerConfigurer.setBasePackage("com.zhengyao.commons.dao.kind.mapper");
            return mapperScannerConfigurer;
        }
        
        <dependency>
            <groupId>tk.mybatis</groupId>
            <artifactId>mapper</artifactId>
            <version>3.4.6</version>
        </dependency>
