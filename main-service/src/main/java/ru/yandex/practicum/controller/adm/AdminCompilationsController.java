package ru.yandex.practicum.controller.adm;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.compilation.CompilationDto;
import ru.yandex.practicum.dto.compilation.NewCompilationDto;
import ru.yandex.practicum.dto.compilation.UpdateCompilationRequest;
import ru.yandex.practicum.service.CompilationService;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/compilations")
@Tag(name = "Admin: Подборки событий", description = "API для работы с подборками")
public class AdminCompilationsController {

    private final CompilationService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создание подборки")
    public CompilationDto adminChangeCompilation(@RequestBody @Valid NewCompilationDto compilationDto) {
        log.info("Admin change compilation {}", compilationDto);
        return service.changeCompilation(compilationDto);
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удаление подборки")
    public void adminDeleteCompilation(@PathVariable Long compId) {
        log.info("Admin delete compilation id {}", compId);
        service.deleteCompilation(compId);
    }

    @PatchMapping("/{compId}")
    @Operation(summary = "Изменение подборки")
    public CompilationDto adminPatchCompilation(@PathVariable Long compId,
                                                @RequestBody @Valid UpdateCompilationRequest compilationDto) {
        log.info("Admin delete compilation id {}", compId);
        return service.patchCompilation(compId, compilationDto);
    }

}
