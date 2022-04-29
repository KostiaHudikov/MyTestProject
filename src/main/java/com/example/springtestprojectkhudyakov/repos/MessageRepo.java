package com.example.springtestprojectkhudyakov.repos;

import com.example.springtestprojectkhudyakov.domain.Message;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.HashSet;
import java.util.List;

public interface MessageRepo extends CrudRepository<Message, Long> {

    List<Message> findAllBySubtype(String subtype);

    @Query(value = "SELECT m FROM Message m WHERE m.subtype <> 'send' AND m.subtype <> 'start' AND m.ssoid <> 'Unauthorized' AND m.formid = ?1 AND m.ssoid = ?2")
    List<Message>  findByFormidAndSsoidAndSubtypeNotSendNotStartNotUnauthorized(String formid, String ssoid);

    @Query(value = "SELECT DISTINCT m.formid FROM Message m WHERE m.formid <> ''")
    HashSet<String> findUniqueFormid();

    @Query(value = "SELECT COUNT(m) FROM Message m WHERE m.formid = ?1")
    int countByFormId(String formId);
}