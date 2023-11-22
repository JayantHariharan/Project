package com.lucifer.socialchat.service;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.lucifer.socialchat.dao.UsersRecordDao;
import com.lucifer.socialchat.model.UsersRecord;

@Service
public class UsersRecordService {

	@Autowired
	UsersRecordDao usersRecordDao;
	
	
	public void updateLeaveTime(String name) {
	    UsersRecord userRecord = usersRecordDao.findTopByNameOrderByIdDesc(name);
	    if (userRecord != null) {
	        userRecord.setLeaveTime(LocalTime.now()); // Update leave time to the current time
	        usersRecordDao.save(userRecord);
	    }
	}


	
	public boolean isUserAllowedToJoin(String username, String email) {
	    UsersRecord latestRecord = usersRecordDao.findTopByEmailOrderByIdDesc(email);

	    if (latestRecord == null || latestRecord.getLeaveTime() != null) {
	      LocalTime joinTime = LocalTime.now();
	      saveUserRecord(username, email, joinTime);
	        return true;
	    }
	    
	    return false;
	}


    public void saveUserRecord(String username, String email, LocalTime joinTime) {
        UsersRecord newUserRecord = new UsersRecord();
        newUserRecord.setName(username);
        newUserRecord.setEmail(email);
        newUserRecord.setJoinTime(joinTime);
        usersRecordDao.save(newUserRecord);
    }



    public boolean doesEmailExist(String email) {
        UsersRecord userRecord = usersRecordDao.findByEmail(email);
        return userRecord != null;
    }

    public boolean isLeaveTimeNull(String email) {
        UsersRecord latestRecord = usersRecordDao.findTopByEmailOrderByIdDesc(email);
        return latestRecord == null || latestRecord.getLeaveTime() == null;
    }







}