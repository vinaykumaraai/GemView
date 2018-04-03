package com.luretechnologies.tms.backend.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import com.luretechnologies.tms.backend.data.entity.AbstractEntity;
import com.vaadin.data.provider.Query;

public abstract class CrudService<T extends AbstractEntity> {

	protected abstract Map<Long, T> getRepository();
	protected abstract List<T> getSavedList();

	public T save(T entity) {
		if(entity.getId()== null) {
			entity.setId(System.currentTimeMillis());
			getSavedList().add(entity);
		}
		getRepository().put(entity.getId(),entity);
		return entity;
	}

	public void delete(Long id) {
		getRepository().remove(id);
		
	}

	public T load(Long id) {
		return getRepository().get(id);
	}

	public abstract Stream<T> fetchFromBackEnd(Query<T, String> query);
	
	protected abstract int sizeInBackEnd(Query<T, String> query);
	
}


