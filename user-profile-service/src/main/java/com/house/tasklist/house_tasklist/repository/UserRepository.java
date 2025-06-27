package com.house.tasklist.house_tasklist.repository;

import com.house.tasklist.house_tasklist.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Find user by username
    Optional<User> findByUsername(String username);

    // Find user by email
    Optional<User> findByEmail(String email);

    // Check if username exists
    boolean existsByUsername(String username);

    // Check if email exists
    boolean existsByEmail(String email);

    // Find active users
    List<User> findByIsActiveTrue();

    // Find inactive users
    List<User> findByIsActiveFalse();

    // Find users by first name containing (case insensitive)
    List<User> findByFirstNameContainingIgnoreCase(String firstName);

    // Find users by last name containing (case insensitive)
    List<User> findByLastNameContainingIgnoreCase(String lastName);

    // Find users by first name and last name
    List<User> findByFirstNameAndLastName(String firstName, String lastName);

    // Custom query to search users by name or email
    @Query("SELECT u FROM User u WHERE " +
           "LOWER(u.firstName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(u.lastName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(u.username) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(u.email) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<User> searchUsers(@Param("searchTerm") String searchTerm);

    // Custom query to get active users count
    @Query("SELECT COUNT(u) FROM User u WHERE u.isActive = true")
    long countActiveUsers();
} 