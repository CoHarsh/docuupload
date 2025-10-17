package org.harsh.repository;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.harsh.model.User;

@ApplicationScoped
public class UserMongoRepository implements PanacheMongoRepository<User> {

}
