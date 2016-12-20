package com.cs.test.db.dao;

import com.cs.test.db.entity.Chapter;
import com.cs.test.db.partentity.PartChapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * Created by admin on 2016/12/6.
 */
@Repository
public interface ChapterDao extends JpaRepository<Chapter, Integer> {

	public List<Chapter> findByBookId(int bookId);

	public Set<String> findChapterNameByBookId(int bookId);

	public List<Chapter> findByRetryStatusAndRetryCountIsLessThan(int retryStatus, int maxRetryCount);

	@Modifying
	@Query("update Chapter c set c.content=?1, c.retryStatus=?2,c.retryCount=c.retryCount+1 where c.id=?3")
	void refreshContent(String content, int retryStatus, int chapterId);

	public Chapter getByName(String chapterName);

	@Modifying
	@Query("update Chapter c set c.retryCount=c.retryCount+1 where c.id=?1")
	void refreshChapterRetryInfo(int chapterId);

	@Query("select new Chapter(c.id, c.name) from Chapter c where c.bookId=?1")
	List<Chapter> getBasicChapterInfo(int bookId);

	//依然使用的是全量查询 只是会对最终的结果进行加工
	List<PartChapter> findByBookIdIn(List<Integer> bookIds);

}
