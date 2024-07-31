package ru.yandex.practicum.service;

import ru.yandex.practicum.dto.compilation.CompilationDto;
import ru.yandex.practicum.dto.compilation.NewCompilationDto;
import ru.yandex.practicum.dto.compilation.UpdateCompilationRequest;

import java.util.List;

public interface CompilationService {
    CompilationDto changeCompilation(NewCompilationDto compilationDto);

    void deleteCompilation(Long compId);

    CompilationDto patchCompilation(Long compId, UpdateCompilationRequest compilationDto);

    List<CompilationDto> getCompilations(Boolean pinned, int from, int size);

    CompilationDto getCompilationsById(Long compId);

}
