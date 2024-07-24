package ru.yandex.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.yandex.practicum.dto.StatCountHitsDto;
import ru.yandex.practicum.model.Stat;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsRepository extends JpaRepository<Stat, Integer> {

    String SELECT_STAT_WITHOUT_UNIQUE_IP_SQL = "SELECT " +
            "new ru.yandex.practicum.dto.StatCountHitsDto(s.app, s.uri, " +
            "(SELECT count(st.ip) FROM Stat AS st WHERE st.uri = s.uri) AS hits) " +
            "FROM Stat AS s WHERE s.uri IN ( ?3 ) AND s.timestamp BETWEEN ?1 AND ?2 " +
            "GROUP BY s.uri, s.app ORDER BY hits DESC ";

    String SELECT_STAT_WITH_UNIQUE_IP_SQL = "SELECT " +
            "new ru.yandex.practicum.dto.StatCountHitsDto(s.app, s.uri, " +
            "(SELECT count(DISTINCT st.ip) FROM Stat AS st WHERE st.uri = s.uri) AS hits) " +
            "FROM Stat AS s WHERE s.uri IN ( ?3 ) AND s.timestamp BETWEEN ?1 AND ?2 " +
            "GROUP BY s.uri, s.app ORDER BY hits DESC ";

    String SELECT_STAT_ALL_WITHOUT_UNIQUE_IP_SQL = "SELECT " +
            "new ru.yandex.practicum.dto.StatCountHitsDto(s.app, s.uri, " +
            "(SELECT count(st.ip) FROM Stat AS st WHERE st.uri = s.uri) AS hits) " +
            "FROM Stat AS s WHERE s.timestamp BETWEEN ?1 AND ?2 GROUP BY s.uri, s.app ORDER BY hits DESC ";

    String SELECT_STAT_ALL_WITH_UNIQUE_IP_SQL = "SELECT " +
            "new ru.yandex.practicum.dto.StatCountHitsDto(s.app, s.uri, " +
            "(SELECT count(DISTINCT st.ip) FROM Stat AS st WHERE st.uri = s.uri) AS hits) " +
            "FROM Stat AS s WHERE s.timestamp BETWEEN ?1 AND ?2 GROUP BY s.uri, s.app ORDER BY hits DESC ";

    @Query(SELECT_STAT_WITHOUT_UNIQUE_IP_SQL)
    List<StatCountHitsDto> findWithoutUniqueIp(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query(SELECT_STAT_WITH_UNIQUE_IP_SQL)
    List<StatCountHitsDto> findWithUniqueIp(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query(SELECT_STAT_ALL_WITHOUT_UNIQUE_IP_SQL)
    List<StatCountHitsDto> findAllWithoutUniqueIp(LocalDateTime start, LocalDateTime end);

    @Query(SELECT_STAT_ALL_WITH_UNIQUE_IP_SQL)
    List<StatCountHitsDto> findAllWithUniqueIp(LocalDateTime start, LocalDateTime end);

}
