package ru.yandex.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.model.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
