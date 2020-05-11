package com.ntt.task;

import javax.annotation.sql.DataSourceDefinition;

@DataSourceDefinition(name = "java:global/jdbc/taskDatasource",
        className = "org.postgresql.xa.PGXADataSource",
        serverName = "${ENV=DB_SERVER}",
        portNumber = 5432,
        databaseName = "tasks",
        user = "${ENV=DB_USER}",
        password = "${ENV=DB_PASSWORD}")
public class DataSource {

}
