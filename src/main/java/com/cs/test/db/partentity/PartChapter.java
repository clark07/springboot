package com.cs.test.db.partentity;

import org.springframework.beans.factory.annotation.Value;

public interface PartChapter {
	String getName();

	@Value("#{target.bookName}")
	String getOtherBookName();

	@Value("#{target.bookName} #{target.name}")
	String getFullName();
}
