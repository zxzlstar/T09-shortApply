package com.star.shop.admin.repository;

import com.star.shop.admin.entity.Member;
import com.star.shop.basic.repository.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Repository
public interface MemberRepository extends BaseRepository<Member,String> {
	public Member findByOpenid(String openid);
	
	public Member findByInvitation(String invitation) ;
	
	public Optional<Member> findById(String id);
	
	@Modifying
	@Query("update Member set amount = :amount where id = :id")
	public void updateAmount(@Param("id") String id, @Param("amount") BigDecimal amount);
	
	public List<Member> findByIdIn(List<Long> list);

	@Query("select id from Member where nickName like :nickName")
	public List<String> findByNickName(@Param("nickName") String nickName);

	public List<Member> findByReferrer(String mid);
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE t_member SET advertising = '' WHERE advertising <> ''", nativeQuery = true)
	public void emptyAdvertising();

	@Query(value = "select case when sex = 1 THEN '男' when sex = 2 THEN '女' ELSE '不详' end as sexName,count(sex) as countNum from t_member GROUP BY sex order by sex DESC ",nativeQuery = true)
	public List<Map<String, Object>> findCountMember();
}
