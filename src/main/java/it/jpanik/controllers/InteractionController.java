package it.jpanik.controllers;

import it.jpanik.dto.InteractionDto;
import it.jpanik.exceptions.ValidationException;
import it.jpanik.services.InteractionService;
import it.jpanik.validator.InteractionValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jCommunity/interaction")
@CrossOrigin(origins = "*", maxAge = 3600)
public class InteractionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InteractionController.class);
    private final InteractionService interactionService;
    @Autowired
    public InteractionController(InteractionService interactionService) {
        this.interactionService = interactionService;
    }

    @GetMapping
    public List<InteractionDto> getAllInteractions()   {
        LOGGER.info("Called GET /interaction");
        return this.interactionService.getAllInteractions();
    }

    @GetMapping("/{id}")
    public InteractionDto getAll(@PathVariable Long id)    {
        LOGGER.info("Called GET /interaction/{}",id);
        return this.interactionService.get(id);
    }

    @PostMapping
    public InteractionDto saveOrUpdate(@RequestBody InteractionDto interactionDto) throws ValidationException {
        LOGGER.info("Called POST /interaction");
        InteractionValidator.validate(interactionDto);
        return this.interactionService.saveOrUpdate(interactionDto);
    }

    @DeleteMapping("/{id}")
    public InteractionDto delete(@PathVariable Long id)  throws ValidationException  {
        LOGGER.info("Called DELETE /interaction");
        return this.interactionService.delete(id);
    }
}
