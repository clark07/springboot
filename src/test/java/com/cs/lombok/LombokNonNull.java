package com.cs.lombok;

import lombok.NonNull;

/**
 * Created by admin on 2016/12/26.
 */

/**
 * lombok.nonNull.exceptionType = [NullPointerException | IllegalArgumentException] (default: NullPointerException).
 When lombok generates a null-check if statement, by default, a java.lang.NullPointerException will be thrown with the field name as the exception message. However, you can use IllegalArgumentException in this configuration key to have lombok throw that exception, with 'fieldName is null' as exception message.
 lombok.nonNull.flagUsage = [warning | error] (default: not set)
 Lombok will flag any usage of @NonNull as a warning or error if configured.
 */
public class LombokNonNull {

	private String name;

	public LombokNonNull(@NonNull String name) {
		this.name = name;
	}

	public static void main(String[] args) {
		new LombokNonNull(null);
	}

}
