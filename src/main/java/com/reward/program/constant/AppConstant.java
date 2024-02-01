package com.reward.program.constant;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.function.Function;

public interface AppConstant {
	String NO_PURCHASE_MESSAGE = "CUSTOMER HAS NO PURCHASE";

	Function<Instant, Month> INSSTANTMONTHFUNCTION = instant -> LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
			.getMonth();

}
