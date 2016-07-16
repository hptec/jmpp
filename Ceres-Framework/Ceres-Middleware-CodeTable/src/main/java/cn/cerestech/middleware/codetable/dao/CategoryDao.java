package cn.cerestech.middleware.codetable.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.cerestech.framework.support.persistence.Owner;
import cn.cerestech.middleware.codetable.entity.Category;

public interface CategoryDao extends JpaRepository<Category, Long> {

	Category findUniqueByOwnerAndCategory(Owner owner, String category);

	List<Category> findByOwner(Owner owner);

}
