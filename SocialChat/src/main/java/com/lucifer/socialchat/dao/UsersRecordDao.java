package com.lucifer.socialchat.dao;

import java.time.LocalTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lucifer.socialchat.model.UsersRecord;

@Repository
public interface UsersRecordDao extends JpaRepository<UsersRecord, Long>{

	UsersRecord findByName(String name);

	UsersRecord findByEmail(String email);

	UsersRecord findTopByEmailOrderByIdDesc(String email);

	UsersRecord findTopByNameOrderByIdDesc(String name);


}
