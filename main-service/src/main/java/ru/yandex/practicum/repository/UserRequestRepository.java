package ru.yandex.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.model.RequestStatus;
import ru.yandex.practicum.model.UserRequest;

import java.util.List;

public interface UserRequestRepository extends JpaRepository<UserRequest, Long> {

    long countByStatusAndEventId(RequestStatus status, Long id);

    List<UserRequest> findAllByRequesterId(Long id);

}
