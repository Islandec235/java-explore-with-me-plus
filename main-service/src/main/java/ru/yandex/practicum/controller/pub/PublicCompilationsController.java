package ru.yandex.practicum.controller.pub;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.compilation.CompilationDto;
import ru.yandex.practicum.service.CompilationService;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/compilations")
public class PublicCompilationsController {

    private final CompilationService service;

    @GetMapping
    public List<CompilationDto> getCompilations(@RequestParam(value = "pinned", defaultValue = "false") Boolean pinned,
                                                @RequestParam(value = "from", defaultValue = "0") @Min(0) int from,
                                                @RequestParam(value = "size", defaultValue = "10") @Min(3) @Max(50) int size) {
        log.info("Get compilations with pinned={}, page-from={}, page-size={}", pinned, from, size);
        return service.getCompilations(pinned, from, size);
    }

    @GetMapping("/{compId}")
    public CompilationDto getCompilationsById(@PathVariable(value = "compId") Long compId) {
        log.info("Get compilation By compId={}", compId);
        return service.getCompilationsById(compId);
    }
}
