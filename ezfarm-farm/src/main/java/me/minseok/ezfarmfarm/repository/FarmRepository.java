package me.minseok.ezfarmfarm.repository;

import me.minseok.ezfarmfarm.domain.Farm;
import org.springframework.data.repository.CrudRepository;

public interface FarmRepository extends CrudRepository<Farm, Long> {
}
