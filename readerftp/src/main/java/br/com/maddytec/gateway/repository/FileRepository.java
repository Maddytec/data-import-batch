package br.com.maddytec.gateway.repository;

import br.com.maddytec.domain.File;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface FileRepository extends CrudRepository<File, UUID> {
}
