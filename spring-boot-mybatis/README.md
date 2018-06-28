# mybatis
spring-boot-mybatis简单demo,基于springmvc拦截器实现登陆权限过滤
官方标准：https://github.com/mybatis/spring-boot-starter/tree/master/mybatis-spring-boot-samples

spring-boot 整合mybaits 分为两种方法：基于xml配置文件和基于注解。这个demo是基于注解，但sql统一放在xml文件中，方便后期维护。
这里整合mybatis 需要注意两个地方：
<ul>
<li>1. 需要指定 mapper java 接口
    <ul>
        <li>1.1 指定mapper java 接口 可用@Mapper注解到每个Mapper 接口 或使用在主函数入口类加上@MapperScan("com.example.mapper")</li>
    </ul>
    
</li>
<li>2.需要指定 mapper xml 文件
     <ul>
        <li>2.1 指定mapper xml 文件 可在properties文件中使用mybatis.mapper-locations: classpath:mapper/*.xml
        </li>
     </ul>
</li>
</ul>



