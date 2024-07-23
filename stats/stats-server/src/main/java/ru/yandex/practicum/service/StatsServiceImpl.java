package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.model.Hit;
import ru.yandex.practicum.model.Stat;
import ru.yandex.practicum.repository.StatsRepository;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {
    private final StatsRepository repository;

    @Transactional
    @Override
    public Hit saveInfo(Hit hit) {
        hit.setTimestamp(LocalDateTime.now().withNano(0));
        return repository.save(hit);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Stat> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
//        if (unique) {
//            return repository.findAllWithUniqueIp(start, end, uris);
//        } else {
//            return repository.findAllWithoutUniqueIp(start, end, uris);
//        }
        return null;
    }
}
