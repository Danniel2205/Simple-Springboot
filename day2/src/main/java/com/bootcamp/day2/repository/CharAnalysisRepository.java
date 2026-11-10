package com.bootcamp.day2.repository;

import com.bootcamp.day2.entity.CharAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CharAnalysisRepository extends JpaRepository<CharAnalysis, Long>{
    List<CharAnalysis> findByKalimatContainingIgnoreCase(String kata);

    @Query("SELECT c FROM CharAnalysis c WHERE c.totalHuruf >= :min")  //menggunakan field yang di state di entity
    List<CharAnalysis> findByTotalHurufMinimal(@Param("min") int min); //karna pakek jpql maka lgsung nembah entity

    @Query(value = "SELECT * FROM mst_char_analysis ORDER BY created_at DESC LIMIT 5",   //karna native querry jadi direct ke table
            nativeQuery = true)
    List<CharAnalysis> findTerbaru();

    boolean existsByKalimatIgnoreCase(String kalimat);
}
