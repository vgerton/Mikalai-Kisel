package com.epam.cdp.multithreading.service;

import com.epam.cdp.multithreading.model.Person;

import java.util.List;

/**
 * @author kisel.nikolay@gmail.com
 * @since 26.10.2014
 */
public interface PersonService {

    public void save(final Person person);

    public List<Person> find();
}
