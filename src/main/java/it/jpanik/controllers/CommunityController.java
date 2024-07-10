package it.jpanik.controllers;

import it.jpanik.dto.CommunityDto;
import it.jpanik.exceptions.ValidationException;
import it.jpanik.services.CommunityService;
import it.jpanik.services.UserCommunityService;
import it.jpanik.validator.CommunityValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jCommunity/community")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CommunityController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommunityController.class);
    private final CommunityService communityService;

    @Autowired
    public CommunityController(CommunityService communityService) {
        this.communityService = communityService;
    }

    @GetMapping
    public List<CommunityDto> getAllCommunities() {
        LOGGER.info("Called GET /community");
        return this.communityService.getAllCommunities();
    }

    @GetMapping("/{id}")
    public CommunityDto get(@PathVariable Long id) throws ValidationException {
        LOGGER.info("Called GET /community/{}",id);
        return this.communityService.get(id);
    }

    @PostMapping
    public CommunityDto saveOrUpdate(@RequestBody CommunityDto communityDto) throws ValidationException {
        LOGGER.info("Called POST /community");
        CommunityValidator.validate(communityDto);
        return this.communityService.saveOrUpdate(communityDto);
    }

    @DeleteMapping("/{id}")
    public CommunityDto delete(@PathVariable Long id) throws ValidationException {
        LOGGER.info("Called DELETE /community/{}",id);
        return this.communityService.delete(id);
    }
}
