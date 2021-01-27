package com.library.management.service;

import com.library.management.model.Library;
import com.library.management.repository.LibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class LibraryService {
    @Autowired
    private LibraryRepository libraryRepository;

    public Library save(Library library) {
        return (Library) libraryRepository.save(library);
    }

    public Optional<Library> get(long id) {
        return libraryRepository.findById(id);
    }

    public Iterable<Library> list() {
        return libraryRepository.findAll();
    }

    @Transactional
    public void update(Library Book) {
        libraryRepository.save(Book);
    }

    @Transactional
    public void delete(long id) {
        libraryRepository.deleteById(id);
    }


}
