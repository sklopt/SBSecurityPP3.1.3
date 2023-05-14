package ru.kata.spring.boot_security.pp.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.pp.entities.Role;

import java.util.List;

@Service
public class RoleService {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Role> getAllRoles() {
        return entityManager.createQuery("select r from Role r", Role.class).getResultList();
    }


    public Role getRole(String userRole) {
        return entityManager.createQuery("select r from Role r where r.name =: name", Role.class)
                .setParameter("userRole", userRole).getSingleResult();
    }


    public Role getRoleById(Long id) {
        return entityManager.find(Role.class, id);
    }


    public void addRole(Role role) {
        entityManager.persist(role);
    }
}
