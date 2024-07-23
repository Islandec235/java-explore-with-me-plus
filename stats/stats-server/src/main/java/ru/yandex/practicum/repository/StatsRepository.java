package ru.yandex.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.yandex.practicum.model.Hit;
import ru.yandex.practicum.model.Stat;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsRepository extends JpaRepository<Hit, Long> {

//    @Query("SELECT new ru.yandex.practicum(h.app, h.uri, COUNT(h.ip) AS hits) " +
//            "FROM hits AS h" +
//            "WHERE h.timestamp BETWEEN ?1 AND ?2 " +
//            "AND h.uri IN ?3 " +
//            "GROUP BY h.uri")
//    List<Stat> findAllWithoutUniqueIp(LocalDateTime start, LocalDateTime end, List<String> uris);
//
//    @Query("SELECT new ru.yandex.practicum(h.app, h.uri, COUNT(DISTINCT h.ip) AS hits) " +
//            "FROM hits AS h" +
//            "WHERE h.timestamp BETWEEN ?1 AND ?2 " +
//            "AND h.uri IN ?3 " +
//            "GROUP BY h.uri")
//    List<Stat> findAllWithUniqueIp(LocalDateTime start, LocalDateTime end, List<String> uris);


    // Caused by: org.hibernate.query.SyntaxException: At 1:86 and token 'h', mismatched input 'h', expecting one of
    // the following tokens: <EOF>, ',', CROSS, FULL, GROUP, INNER, JOIN, LEFT, ORDER, OUTER, RIGHT, WHERE [SELECT
    // new ru.yandex.practicum(h.app, h.uri, COUNT(h.ip) AS hits) FROM hits AS hWHERE h.timestamp BETWEEN ?1 AND ?2 AND
    // h.uri IN ?3 GROUP BY h.uri]
}
