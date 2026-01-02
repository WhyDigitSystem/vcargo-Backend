package com.efit.savaari.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efit.savaari.entity.ResponsibilityVO;
import com.efit.savaari.entity.ScreensVO;

public interface ScreensRepo extends JpaRepository<ScreensVO, Long> {

	List<ScreensVO> findByResponsibilityVO(ResponsibilityVO responsibilityVO);

}
