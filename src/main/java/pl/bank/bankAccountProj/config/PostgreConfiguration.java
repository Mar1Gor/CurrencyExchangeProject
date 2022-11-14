//package pl.bank.bankAccountProj.config;
//
//import com.sun.istack.NotNull;
//import lombok.Setter;
//import org.postgresql.ds.PGSimpleDataSource;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//
//import javax.sql.DataSource;
//
//@Configuration
//@Profile("postgre")
//@ConfigurationProperties("postgre")
//@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
//@Setter
//public class PostgreConfiguration {
//    @NotNull
//    private String username;
//
//    @NotNull
//    private String password;
//
//    @NotNull
//    private String serverName;
//
//    @NotNull
//    private String databaseName;
//
//    @NotNull
//    private Integer portNumber;
//
//    @Bean
//    DataSource dataSource() {
//
//        PGSimpleDataSource dataSource = new PGSimpleDataSource();
//        dataSource.setUser(username);
//        dataSource.setPassword(password);
//        dataSource.setServerNames(new String[] {serverName});
//        dataSource.setDatabaseName(databaseName);
//        dataSource.setPortNumbers(new int[] {portNumber});
//
//        return dataSource;
//    }
//}
