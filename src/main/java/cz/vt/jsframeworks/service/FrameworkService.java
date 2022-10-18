package cz.vt.jsframeworks.service;

import java.time.LocalDate;
import java.util.List;

import cz.vt.jsframeworks.entity.Framework;

public interface FrameworkService {
    Framework getFramework(Long id);
    Framework saveFramework(Framework framework);
    Framework updateFramework(Framework framework, Long id);
    Framework updateHypeLevelAndDeprecationDate(Long id, Long hypeLevel, LocalDate depecationDate);
    Framework updateHypeLevel(Long id, Long hypeLevel);
    Framework updateDeprecationDate(Long id, LocalDate deprecationDate);
    void deleteFramework(Long id);
    List<Framework> getFrameworks();
    List<Framework> getFrameworkByString(String infix);

    List<Framework> getByHypeLevelGreaterThanEqual(Long hypeLevel);
    
    List<Framework> getByHypeLevelLessThanEqual(Long hypeLevel);
}
