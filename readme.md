**database credentials example**

**ORACLE**

```
first.db.datasource.jdbc-url: jdbc:oracle:thin:@url:sid
first.db.datasource.username: username
first.db.datasource.password: password
first.db.datasource.driverClassName: oracle.jdbc.OracleDriver
```

**DB2**

```
first.db.datasource.jdbc-url: jdbc:as400://url
first.db.datasource.username: username
first.db.datasource.password: password
first.db.datasource.driverClassName: com.ibm.as400.access.AS400JDBCDriver
```

**MYSQL**

```
first.db.datasource.jdbc-url: jdbc:mysql://url:port/db_name
first.db.datasource.username: username
first.db.datasource.password: password
first.db.datasource.driverClassName: com.mysql.jdbc.Driver
```
**POSTGRESQL**

```
first.db.datasource.jdbc-url: jdbc:postgresql://url:port/scheme?currentSchema=cshema
first.db.datasource.username: username
first.db.datasource.password: password
first.db.datasource.driverClassName: org.postgresql.Driver
```