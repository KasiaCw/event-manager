package com.zdjavapol110.eventmanager.core.modules.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventRepository
    extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {

    @Query("SELECT E FROM Event E WHERE LOWER(E.title) LIKE concat('%', LOWER (?1),'%')")
    List<Event> findByTitle(String keyword);

}
