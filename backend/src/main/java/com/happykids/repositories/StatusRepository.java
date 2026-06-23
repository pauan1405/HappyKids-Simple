package com.happykids.repositories;
import com.happykids.entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;
public interface StatusRepository extends JpaRepository<Status, Integer> {}
