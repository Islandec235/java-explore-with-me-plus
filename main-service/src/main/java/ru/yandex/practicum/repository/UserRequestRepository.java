package ru.yandex.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.yandex.practicum.model.RequestStatus;
import ru.yandex.practicum.model.UserRequest;

import java.util.List;
import java.util.Set;

public interface UserRequestRepository extends JpaRepository<UserRequest, Long> {

    Long countByStatusAndEventId(RequestStatus status, Long id);

    List<UserRequest> findAllByRequesterId(Long id);

    List<UserRequest> findAllByRequesterIdAndEventId(Long userId, Long eventId);

    List<UserRequest> findByIdInAndEventId(Set<Long> ids, Long eventId);



    @Modifying
//    @Query("UPDATE UserRequest u SET u.status = :status WHERE u.id IN :ids")
//    void updateStatusRequest(@Param(value = "status") RequestStatus status, @Param(value = "ids") List<Long> ids);
    @Query(value = "UPDATE user_request SET status = ?1 WHERE id = ?2 ", nativeQuery = true)
    void updateUserRequestStatus(String status, Long id);

    @Modifying
    @Query(value = "UPDATE user_request SET status = 'CANCELED' WHERE event_id = ?1 ", nativeQuery = true)
    void cancelStatusAllRequestPending(Long eventId);

}
