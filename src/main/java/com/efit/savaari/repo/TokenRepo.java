package com.efit.savaari.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efit.savaari.entity.TokenVO;

public interface TokenRepo extends JpaRepository<TokenVO, String>{

}
