package org.example.repository;

import org.example.dto.PersonCreateRequest;
import org.example.models.Person;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person,Long> {
    Optional<Person> findByEmail(String email);
    @Query(nativeQuery = true, value = " select * from Person order by id limit ?2 offset ?1")
    List<Person> findAllbyCustom(int id, int skip);

//    @Query()
//    List<Person> findAllByWhere(int id, int skip);
}
