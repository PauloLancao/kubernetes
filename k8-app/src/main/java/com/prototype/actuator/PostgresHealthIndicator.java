package com.prototype.actuator;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

@Component
@Endpoint(id = "postgres")
public class PostgresHealthIndicator {

	@ReadOperation
	public String postgresHealth() {
		return "Postgres Health";
	}
}
