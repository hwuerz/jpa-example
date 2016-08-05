# Add MySQL Datasource for JPA

Short description how to connect to MySQL databases in JavaEE applications running on Wildfly. This is tested with Wildfly [10.0.0.Final](http://download.jboss.org/wildfly/10.0.0.Final/wildfly-10.0.0.Final.zip). First you have to add the MySQL Module to wildfly, than you can configure the data source via the web-interface and last xou are able to use the connection.


## Add MySQL Module to Wildfly

1. Download mysql connector from [MySQL-Website](http://dev.mysql.com/downloads/connector/j/)

2. Copy `mysql-connector-java-5.1.39-bin.jar` to `$JBOSS_HOME/modules/com/mysql/main`

3. Create a file called `module.xml` in `$JBOSS_HOME/modules/system/layers/base/com/mysql/driver/main` with the following content

    ``` xml
    <module xmlns="urn:jboss:module:1.3" name="com.mysql.driver">
     <resources>
      <resource-root path="mysql-connector-java-5.1.39-bin.jar" />
     </resources>
     <dependencies>
      <module name="javax.api"/>
      <module name="javax.transaction.api"/>
     </dependencies>
    </module>
    ```
    *Check the path to point to the correct jar file*
    
4. Own the new files by wildfly user

    ``` bash
    sudo chown -R wildfly:wildfly ${WILDFLY_HOME}/modules/system/layers/base/com/mysql/`
    ```
    
5. Start wildfly

6. Add datasource

    ``` bash
    ${WILDFLY_HOME}/jboss-cli.sh
    ```
    
    The JBoss CLI starts but is disconnected. Connect to server by running
    
    ``` bash
    connect localhost:port
    ```
    
    The CLI is connected to the running instance `[standalone@localhost:9990 /]` is displayed. Add the MySQL module to wildfly by running
    
    ``` bash
    /subsystem=datasources/jdbc-driver=mysql:add(driver-name=mysql,driver-module-name=com.mysql.driver,driver-class-name=com.mysql.jdbc.Driver)
    ```
    
    The CLI should print `{"outcome" => "success"}`
   
    
    
## Add *data source*

1. Open `http://localhost:9990` and navigate to `Configuration - Subsystems - Datasources - Non-XA`

2. Click `Add`

3. Insert Name and JNDI Name. JNDI Name has to follow the schema `java:jboss/something`

4. Test connection by clicking the button



## Let Java use the data source
1. Create `src/main/resources/META-INF/persistence.xml` with the following content

    ``` xml
    <?xml version="1.0" encoding="UTF-8"?>
    <persistence version="2.0" xmlns="http://java.sun.com/xml/ns/persistence" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://java.sun.com/xml/ns/persistence http://java.sun.com/xml/ns/persistence/persistence_2_0.xsd">
     <persistence-unit name="MyPU" transaction-type="JTA">
      <jta-data-source>java:jboss/something</jta-data-source>
      <exclude-unlisted-classes>false</exclude-unlisted-classes>
      <properties>
      </properties>
     </persistence-unit>
    </persistence>
    ```
2. Use connection
    ``` java
    import javax.faces.bean.ManagedBean;
    import javax.faces.bean.SessionScoped;
    import javax.persistence.EntityManager;
    import javax.persistence.PersistenceContext;
    import java.util.List;
    
    @ManagedBean
    @SessionScoped
    public class Controller {
    
        @PersistenceContext(unitName = "MyPU")
        private EntityManager entityManager;
    
        @SuppressWarnings("unchecked")
        public List<MyTable> get() {
            return entityManager.createNativeQuery("select * from MyTable", MyTable.class).getResultList();
        }
    
    }
    ```

Hallo