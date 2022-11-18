package com.ogi.ogi.repo;

import com.ogi.ogi.models.Data;
import org.springframework.data.repository.CrudRepository;

public interface DataRepository extends CrudRepository<Data/* table */, Long/*id type*/> {

}
