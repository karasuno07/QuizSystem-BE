package com.fsoft.quizsystem.service;

import com.fsoft.quizsystem.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class TagService {

    private final TagRepository tagRepository;
}
