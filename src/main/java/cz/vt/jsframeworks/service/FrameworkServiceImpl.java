package cz.vt.jsframeworks.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.vt.jsframeworks.entity.Framework;
import cz.vt.jsframeworks.exception.FrameworkNotFoundException;
import cz.vt.jsframeworks.repository.FrameworkRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class FrameworkServiceImpl implements FrameworkService {

    FrameworkRepository frameworkRepository;

    @Override
    public Framework getFramework(Long id) {
        Optional<Framework> framework = frameworkRepository.findById(id);
        return unwrapFramework(framework, id);
    }

    @Override
    @Transactional 
    public Framework saveFramework(Framework framework) {
        return frameworkRepository.save(framework);
    }

    @Override
    public void deleteFramework(Long id) {        
        frameworkRepository.deleteById(id);
    }

    @Override
    public List<Framework> getFrameworks() {
        return frameworkRepository.findAll();
    }

    @Override
    public List<Framework> getFrameworkByString(String infix) {
        List<Framework> frameworks = frameworkRepository.findByFrameworkNameContaining(infix);
        if (frameworks.isEmpty()) {
            throw new FrameworkNotFoundException(infix);
        } else {
            return frameworks;
        }
    }

    public Framework unwrapFramework(Optional<Framework> entity, Long id) {
        if (entity.isPresent())
            return entity.get();
        else
            throw new FrameworkNotFoundException(id);
    }

    @Override
    public Framework updateHypeLevelAndDeprecationDate(Long id, Long hypeLevel, LocalDate depecationDate) {
        Framework frToUpdate = unwrapFramework(frameworkRepository.findById(id), id);
        frToUpdate.setHypeLevel(hypeLevel);
        frToUpdate.setDeprecationDate(depecationDate);
        return frameworkRepository.save(frToUpdate);
    }

    @Override
    public Framework updateHypeLevel(Long id, Long hypeLevel) {
        Framework frToUpdate = unwrapFramework(frameworkRepository.findById(id), id);
        frToUpdate.setHypeLevel(hypeLevel);
        return frameworkRepository.save(frToUpdate);
    }

    @Override
    public Framework updateDeprecationDate(Long id, LocalDate deprecationDate) {
        Framework frToUpdate = unwrapFramework(frameworkRepository.findById(id), id);
        frToUpdate.setDeprecationDate(deprecationDate);
        return frameworkRepository.save(frToUpdate);
    }

    @Override
    public Framework updateFramework(Framework framework, Long id) {
        unwrapFramework(frameworkRepository.findById(id), id);
        return frameworkRepository.save(framework);
    }

    @Override
    public List<Framework> getByHypeLevelGreaterThanEqual(Long hypeLevel) {        
        return frameworkRepository.findByHypeLevelGreaterThanEqual(hypeLevel);
    }

    @Override
    public List<Framework> getByHypeLevelLessThanEqual(Long hypeLevel) {        
        return frameworkRepository.findByHypeLevelLessThanEqual(hypeLevel);
    }

}
