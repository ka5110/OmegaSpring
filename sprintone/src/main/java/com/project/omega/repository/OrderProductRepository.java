package com.project.omega.repository;

import com.project.omega.bean.dao.OrderProduct;
import com.project.omega.bean.dao.OrderProductPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct, OrderProductPK> {}
