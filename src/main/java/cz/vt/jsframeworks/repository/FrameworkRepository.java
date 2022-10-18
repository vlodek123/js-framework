package cz.vt.jsframeworks.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cz.vt.jsframeworks.entity.Framework;

@Repository
public interface FrameworkRepository extends JpaRepository<Framework, Long> {
    
    List<Framework> findByFrameworkNameContaining(String infix);

    List<Framework> findByHypeLevelGreaterThanEqual(Long hypeLevel);
    
    List<Framework> findByHypeLevelLessThanEqual(Long hypeLevel);
    
}
