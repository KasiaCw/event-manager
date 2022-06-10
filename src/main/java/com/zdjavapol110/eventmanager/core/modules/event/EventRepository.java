package com.zdjavapol110.eventmanager.core.modules.event;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EventRepository
    extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {

    @Query("SELECT E FROM Event E WHERE LOWER(E.title) LIKE concat('%', LOWER (?1),'%')")
    List<Event> findByTitle(String keyword);

    default Specification<Event> onlyPublished() {
        return ((root, query, criteriaBuilder) -> criteriaBuilder
                .notEqual(root.get("status"),EventState.NOT_PUBLISHED));
    }

    default Specification<Event> endsAfter(LocalDate date) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder
                .greaterThanOrEqualTo(root.get("endDate"),date));
    }

    default Specification<Event> onlyTitle(String title) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder
                .like(criteriaBuilder.lower(root.get("title")),"%" + title.toLowerCase() + "%"));
    }

    @Query("SELECT e FROM Event e WHERE e.title = ?1")
    Optional<Event> findUsersByEventName(String eventTitle);

}
