package com.efit.savaari.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efit.savaari.entity.NotificationVO;

public interface NotificationRepo extends JpaRepository<NotificationVO, Long> {
	
	List<NotificationVO> findByUseridAndIsReadFalseAndIsDeletedFalse(Long userid);

    List<NotificationVO> findByUserid(Long userid);

}
