package it.jpanik.mappers;

import it.jpanik.exceptions.ValidationException;

import java.util.*;
import java.util.stream.Collectors;

public abstract class Mapper<DTO,ENTITY> {

    public DTO convertEntityToDto(ENTITY entity) {
        if(entity==null)   {
            return null;
        }
        return convertEntityToDtoImpl(entity);
    }

    public ENTITY convertDtoToEntity(DTO dto) {
        if(dto==null)   {
            return null;
        }
        return convertDtoToEntityImpl(dto);
    }

    public abstract DTO convertEntityToDtoImpl(ENTITY entity);

    public abstract ENTITY convertDtoToEntityImpl(DTO dto);

    public List<DTO> convertEntityListToDtoList(Set<ENTITY> entities)   {
        if (entities == null) {
            return new ArrayList<>();
        }
        return entities.stream().map( e -> convertEntityToDto(e)).toList();
    }

    public List<ENTITY> convertDtoListToEntityList(List<DTO> dtos) {
        if(dtos == null)    {
            return new ArrayList<>();
        }
        return dtos.stream().map(dto -> convertDtoToEntity(dto)).toList();
    }
    
    public List<DTO> convertEntityListToDtoList(List<ENTITY> entities) {
        if (entities == null) {
            return new ArrayList<>();
        }
        return entities.stream().map( e -> convertEntityToDto(e)).toList();
    }

    public Set<ENTITY> convertDtoListToEntitySet(List<DTO> dto) {
        if (dto == null) {
            return new HashSet<>();
        }
        return dto.stream().map( dto1 -> convertDtoToEntity(dto1)).collect(Collectors.toSet());
    }

}
