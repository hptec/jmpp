package cn.cerestech.middleware.codetable.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.cerestech.middleware.codetable.entity.Category;

public interface CategoryDao extends JpaRepository<Category, Long> {

	public Category findUniqueByOwnerTypeAndOwnerIdAndCategory(String type, Long id, String category);

}
