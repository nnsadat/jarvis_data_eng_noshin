package ca.jrvs.apps.trading;

import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"ca.jrvs.apps.trading.dao", "ca.jrvs.apps.trading.service"})
public class TestConfig {

  @Bean
  public MarketDataConfig marketDataConfig() {
    MarketDataConfig marketDataConfig = new MarketDataConfig();
    marketDataConfig.setHost("https://cloud.iexapis.com/v1/");
    marketDataConfig.setToken(System.getenv("IEX_PUB_TOKEN"));
    return marketDataConfig;
  }

  @Bean
  public PoolingHttpClientConnectionManager poolingHttpClientConnectionManager() {
    PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
    cm.setMaxTotal(50);
    cm.setDefaultMaxPerRoute(50);
    return cm;
  }

  @Bean
  public DataSource dataSource() {
    String url = System.getenv("PSQL_URL");
    String user = System.getenv("PSQL_USER");
    String password = System.getenv("PSQL_PASSWORD");
    BasicDataSource basicDataSource = new BasicDataSource();
    basicDataSource.setUrl(url);
    basicDataSource.setUsername(user);
    basicDataSource.setPassword(password);
    return basicDataSource;
  }

}