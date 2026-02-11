package com.doogoo.doogoo.event.repository;

import com.doogoo.doogoo.event.domain.Event;
import java.time.Instant;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByStartTimeGreaterThanEqualOrderByStartTime(Instant fromTime);

    List<Event> findByExternalIdInOrderByStartTime(List<String> externalIds);
}
