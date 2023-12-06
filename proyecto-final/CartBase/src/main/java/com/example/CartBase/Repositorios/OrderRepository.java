package com.example.CartBase.Repositorios;

import com.example.CartBase.Entidades.Enumerate.OrderStatus;
import com.example.CartBase.Entidades.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    public List<Order> findAllByStatus(OrderStatus status);

    public List<Order> findAllByStatus(OrderStatus status, Pageable pageable);

    public Optional<Order> findByIdAndClientUsername(Long id, String clientUsername);

    public List<Order> findAllByClientUsername(String clientUsername, Pageable pageable);

    public List<Order> findAllByClientUsernameAndStatus(String clientUsername, OrderStatus status, Pageable pageable);

    public List<Order> findAllByClientUsernameStartsWith(String clientUsername, Pageable pageable);

    public List<Order> findAllByClientUsernameStartsWithAndStatus(String clientUsername, OrderStatus status, Pageable pageable);

    public Optional<Order> findByIdAndStatus(Long id, OrderStatus status);

    public Integer countAllByCreateDateBetween(Date createDateStart, Date createDateEnd);

    public Integer countAllByAcceptedDateBetween(Date acceptedDateStart, Date acceptedDateEnd);

    public Integer countAllByUpdateDateBetweenAndStatus(Date updateDateStart, Date updateDateEnd, OrderStatus status);
}
