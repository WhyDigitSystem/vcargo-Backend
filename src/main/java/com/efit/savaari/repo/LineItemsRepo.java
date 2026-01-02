package com.efit.savaari.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.LineItemsVO;
import com.efit.savaari.entity.RequestforQuotesVO;

@Repository
public interface LineItemsRepo extends JpaRepository<LineItemsVO, Long>{

	List< LineItemsVO> findByRequestforQuotesVO(RequestforQuotesVO vo);

}
