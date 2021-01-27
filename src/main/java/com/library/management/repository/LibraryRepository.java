package com.library.management.repository;

import com.library.management.model.Library;
import org.springframework.data.repository.CrudRepository;


public interface LibraryRepository extends CrudRepository<Library, Long> {
}
