package com.efit.savaari.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.IndentsParticipantsVO;
import com.efit.savaari.entity.IndentsVO;

@Repository
public interface IndentsParticipantsRepo extends JpaRepository<IndentsParticipantsVO, Long>{

	List< IndentsParticipantsVO> findByIndentsVO(IndentsVO indentsVO);

}
