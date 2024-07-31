package ru.yandex.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.model.RequestStatus;
import ru.yandex.practicum.model.UserRequest;

import java.util.List;

public interface UserRequestRepository extends JpaRepository<UserRequest, Long> {

    Long countByStatusAndEventId(RequestStatus status, Long id);

    List<UserRequest> findAllByRequesterId(Long id);

    List<UserRequest> findAllByRequesterIdAndEventId(Long userId, Long eventId);

    List<UserRequest> findByIdIn(List<Long> ids);
}
