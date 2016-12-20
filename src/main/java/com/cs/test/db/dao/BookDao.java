package com.cs.test.db.dao;

import com.cs.test.db.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by admin on 2016/12/6.
 */
@Repository
public interface BookDao extends JpaRepository<Book, Integer>, JpaSpecificationExecutor<Book> {

	public List<Book> findBySpiderOpenAndStatus(int spiderOpen, int status);

	@Modifying
	@Query("update Book b set b.lastestChapterId=?1, b.lastestChapterName=?2, b.lastedSpiderTime=?3 where b.id=?4")
	public int updateLastestChapterInfo(int chapterId, String chapterName, Date spiderTime, int bookId);

	public Book getByName(String bookName);

	@Query("select b.name from Book b")
	public Set<String> findName();


	public Book findById(int id);
}
