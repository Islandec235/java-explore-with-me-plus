package ru.yandex.practicum.controller.adm;

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
public class AdminCompilationsController {

    private final CompilationService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto adminChangeCompilation(@RequestBody @Valid NewCompilationDto compilationDto) {
        log.info("Admin change compilation {}", compilationDto);
        return service.changeCompilation(compilationDto);
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void adminDeleteCompilation(@PathVariable Long compId) {
        log.info("Admin delete compilation id {}", compId);
        service.deleteCompilation(compId);
    }

    @PatchMapping("/{compId}")
    public CompilationDto adminPatchCompilation(@PathVariable Long compId,
                                                @RequestBody @Valid UpdateCompilationRequest compilationDto) {
        log.info("Admin delete compilation id {}", compId);
        return service.patchCompilation(compId, compilationDto);
    }

}
