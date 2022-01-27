package com.prototype.restdocopenapi;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface FooRepository extends PagingAndSortingRepository<Foo, Long> {

}
