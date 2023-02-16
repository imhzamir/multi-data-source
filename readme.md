**database credentials example**

**csds: Custom Database**

**ORACLE**

```
csds.datasource.jdbc-url: jdbc:oracle:thin:@url:sid
csds.datasource.username: username
csds.datasource.password: password
csds.datasource.driverClassName: oracle.jdbc.OracleDriver
```

**DB2**

```
csds.datasource.jdbc-url: jdbc:as400://url
csds.datasource.username: username
csds.datasource.password: password
csds.datasource.driverClassName: com.ibm.as400.access.AS400JDBCDriver
```

**MYSQL**

```
csds.datasource.jdbc-url: jdbc:mysql://url:port/db_name
csds.datasource.username: username
csds.datasource.password: password
csds.datasource.driverClassName: com.mysql.jdbc.Driver
```
**POSTGRESQL**

```
csds.datasource.jdbc-url: jdbc:postgresql://url:port/scheme?currentSchema=cshema
csds.datasource.username: username
csds.datasource.password: password
csds.datasource.driverClassName: org.postgresql.Driver
```