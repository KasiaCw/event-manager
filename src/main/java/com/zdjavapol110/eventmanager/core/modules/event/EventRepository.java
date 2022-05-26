package com.zdjavapol110.eventmanager.core.modules.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EventRepository
    extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {}
